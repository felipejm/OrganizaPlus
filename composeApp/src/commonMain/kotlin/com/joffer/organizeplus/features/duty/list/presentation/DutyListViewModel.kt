package com.joffer.organizeplus.features.duty.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.common.constants.CategoryConstants
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyWithLastOccurrence
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.dashboard.domain.usecases.DeleteDutyUseCase
import com.joffer.organizeplus.features.duty.list.domain.DutyCategoryFilter
import com.joffer.organizeplus.features.duty.occurrence.domain.repositories.DutyOccurrenceRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DutyListViewModel(
    private val repository: DutyRepository,
    private val deleteDutyUseCase: DeleteDutyUseCase,
    private val dutyOccurrenceRepository: DutyOccurrenceRepository,
    private val categoryFilter: DutyCategoryFilter
) : ViewModel() {

    private val _uiState = MutableStateFlow(DutyListUiState())
    val uiState: StateFlow<DutyListUiState> = _uiState.asStateFlow()

    init {
        loadDuties()
    }

    fun onIntent(intent: DutyListIntent) {
        when (intent) {
            DutyListIntent.LoadDuties -> loadDuties()
            DutyListIntent.RefreshDuties -> refreshDuties()
            is DutyListIntent.ShowDeleteConfirmation -> showDeleteConfirmation(intent.dutyId)
            DutyListIntent.HideDeleteConfirmation -> hideDeleteConfirmation()
            is DutyListIntent.ConfirmDeleteDuty -> confirmDeleteDuty(intent.dutyId)
            DutyListIntent.ClearError -> clearError()
            DutyListIntent.Retry -> retry()
        }
    }

    private fun loadDuties() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            repository.getAllDuties()
                .catch { exception ->
                    handleError("Error loading duties", exception)
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { duties -> processDuties(duties) },
                        onFailure = { exception -> handleError("Failed to load duties", exception) }
                    )
                }
        }
    }

    private suspend fun processDuties(duties: List<Duty>) {
        val filteredDuties = filterDutiesByCategory(duties)

        if (filteredDuties.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                duties = emptyList(),
                error = null
            )
            return
        }

        val currentDate = getCurrentDate()
        val dutiesWithOccurrences = mutableListOf<DutyWithLastOccurrence>()

        filteredDuties.forEach { duty ->
            viewModelScope.launch {
                val dutyWithOccurrence = loadDutyWithOccurrence(duty, currentDate)
                updateDutiesList(dutyWithOccurrence)
            }
        }
    }

    private fun filterDutiesByCategory(duties: List<Duty>): List<Duty> {
        return when (categoryFilter) {
            DutyCategoryFilter.Personal -> duties.filter { it.categoryName == CategoryConstants.PERSONAL }
            DutyCategoryFilter.Company -> duties.filter { it.categoryName == CategoryConstants.COMPANY }
        }
    }

    private fun getCurrentDate(): CurrentDate {
        val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        return CurrentDate(
            month = currentDateTime.monthNumber,
            year = currentDateTime.year
        )
    }

    private data class CurrentDate(
        val month: Int,
        val year: Int
    )

    private suspend fun loadDutyWithOccurrence(
        duty: Duty,
        currentDate: CurrentDate
    ): DutyWithLastOccurrence {
        val lastOccurrence = dutyOccurrenceRepository.getLastOccurrenceByDutyId(duty.id).getOrNull()

        val hasCurrentMonthOccurrence = dutyOccurrenceRepository.getMonthlyOccurrences(
            duty.categoryName,
            currentDate.month,
            currentDate.year
        ).getOrNull()?.any { it.dutyId == duty.id } ?: false

        return DutyWithLastOccurrence(
            duty = duty,
            lastOccurrence = lastOccurrence,
            hasCurrentMonthOccurrence = hasCurrentMonthOccurrence
        )
    }

    private fun updateDutiesList(dutyWithOccurrence: DutyWithLastOccurrence) {
        val updatedDuties = _uiState.value.duties.toMutableList()
        val existingIndex = updatedDuties.indexOfFirst { it.duty.id == dutyWithOccurrence.duty.id }

        if (existingIndex >= 0) {
            updatedDuties[existingIndex] = dutyWithOccurrence
        } else {
            updatedDuties.add(dutyWithOccurrence)
        }

        val sortedDuties = sortDuties(updatedDuties)
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            duties = sortedDuties,
            error = null
        )
    }

    private fun sortDuties(duties: List<DutyWithLastOccurrence>): List<DutyWithLastOccurrence> {
        return duties.sortedWith(
            compareByDescending<DutyWithLastOccurrence> { it.hasCurrentMonthOccurrence }
                .thenBy { it.duty.title }
        )
    }

    private fun handleError(message: String, exception: Throwable) {
        Napier.e(message, exception)
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            error = exception.message ?: "Unknown error"
        )
    }

    private fun refreshDuties() = loadDuties()

    private fun deleteDuty(dutyId: Long) {
        viewModelScope.launch {
            try {
                deleteDutyUseCase(dutyId).fold(
                    onSuccess = { loadDuties() },
                    onFailure = { exception -> handleError("Failed to delete duty", exception) }
                )
            } catch (exception: Exception) {
                handleError("Error deleting duty", exception)
            }
        }
    }

    private fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    private fun retry() = loadDuties()
    
    private fun showDeleteConfirmation(dutyId: Long) {
        _uiState.value = _uiState.value.copy(
            showDeleteConfirmation = true,
            dutyToDelete = dutyId
        )
    }
    
    private fun hideDeleteConfirmation() {
        _uiState.value = _uiState.value.copy(
            showDeleteConfirmation = false,
            dutyToDelete = null
        )
    }
    
    private fun confirmDeleteDuty(dutyId: Long) {
        hideDeleteConfirmation()
        deleteDuty(dutyId)
    }
}
