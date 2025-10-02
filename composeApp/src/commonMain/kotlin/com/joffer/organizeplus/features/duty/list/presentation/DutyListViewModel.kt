package com.joffer.organizeplus.features.duty.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.common.constants.CategoryConstants
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
            is DutyListIntent.DeleteDuty -> deleteDuty(intent.dutyId)
            DutyListIntent.ClearError -> clearError()
            DutyListIntent.Retry -> retry()
        }
    }

    private fun loadDuties() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            repository.getAllDuties()
                .catch { exception ->
                    Napier.e("Error loading duties", exception)
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "error_loading_duties"
                    )
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { duties ->
                            // Filter duties by category
                            val filteredDuties = when (categoryFilter) {
                                DutyCategoryFilter.All -> duties
                                DutyCategoryFilter.Personal -> duties.filter {
                                    it.categoryName == CategoryConstants.PERSONAL
                                }
                                DutyCategoryFilter.Company -> duties.filter {
                                    it.categoryName == CategoryConstants.COMPANY
                                }
                                is DutyCategoryFilter.Custom -> duties.filter { it.categoryName == categoryFilter.name }
                            }

                            // Get current month and year
                            val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                            val currentMonth = currentDateTime.monthNumber
                            val currentYear = currentDateTime.year

                            // Load last occurrence and check current month occurrence for each duty
                            val dutiesWithOccurrences = mutableListOf<DutyWithLastOccurrence>()
                            
                            filteredDuties.forEach { duty ->
                                launch {
                                    // Load last occurrence
                                    val lastOccurrenceResult = dutyOccurrenceRepository.getLastOccurrenceByDutyId(duty.id)
                                    
                                    // Check if duty has occurrences in current month
                                    val currentMonthOccurrencesResult = dutyOccurrenceRepository.getMonthlyOccurrences(
                                        duty.categoryName, currentMonth, currentYear
                                    )
                                    
                                    val hasCurrentMonthOccurrence = currentMonthOccurrencesResult
                                        .getOrNull()
                                        ?.any { occurrence -> occurrence.dutyId == duty.id } ?: false
                                    
                                    val lastOccurrence = lastOccurrenceResult.getOrNull()
                                    
                                    val dutyWithOccurrence = DutyWithLastOccurrence(
                                        duty = duty,
                                        lastOccurrence = lastOccurrence,
                                        hasCurrentMonthOccurrence = hasCurrentMonthOccurrence
                                    )
                                    
                                    // Update the list with the new duty
                                    val updatedDuties = _uiState.value.duties.toMutableList()
                                    val existingIndex = updatedDuties.indexOfFirst { it.duty.id == duty.id }
                                    
                                    if (existingIndex >= 0) {
                                        updatedDuties[existingIndex] = dutyWithOccurrence
                                    } else {
                                        updatedDuties.add(dutyWithOccurrence)
                                    }
                                    
                                    // Sort duties: paid (current month occurrences) first, then by title
                                    val sortedDuties = updatedDuties.sortedWith(
                                        compareByDescending<DutyWithLastOccurrence> { it.hasCurrentMonthOccurrence }
                                            .thenBy { it.duty.title }
                                    )
                                    
                                    _uiState.value = _uiState.value.copy(
                                        isLoading = false,
                                        duties = sortedDuties,
                                        error = null
                                    )
                                }
                            }
                            
                            // Initialize with empty list if no duties
                            if (filteredDuties.isEmpty()) {
                                _uiState.value = _uiState.value.copy(
                                    isLoading = false,
                                    duties = emptyList(),
                                    error = null
                                )
                            }
                        },
                        onFailure = { exception ->
                            Napier.e("Failed to load duties", exception)
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = exception.message ?: "error_loading_duties"
                            )
                        }
                    )
                }
        }
    }

    private fun refreshDuties() {
        loadDuties()
    }

    private fun deleteDuty(dutyId: Long) {
        viewModelScope.launch {
            try {
                val result = deleteDutyUseCase(dutyId)
                result.fold(
                    onSuccess = {
                        loadDuties() // Refresh the list
                    },
                    onFailure = { exception ->
                        Napier.e("Failed to delete duty", exception)
                        _uiState.value = _uiState.value.copy(
                            error = exception.message ?: "error_deleting_duty"
                        )
                    }
                )
            } catch (exception: Exception) {
                Napier.e("Error deleting duty", exception)
                _uiState.value = _uiState.value.copy(
                    error = exception.message ?: "error_deleting_duty"
                )
            }
        }
    }

    private fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    private fun retry() {
        loadDuties()
    }
}
