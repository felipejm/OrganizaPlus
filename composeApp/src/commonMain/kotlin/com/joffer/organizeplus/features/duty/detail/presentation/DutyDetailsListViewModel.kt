package com.joffer.organizeplus.features.duty.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.duty.occurrence.domain.repositories.DutyOccurrenceRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DutyDetailsListViewModel(
    private val repository: DutyOccurrenceRepository,
    private val dutyRepository: DutyRepository,
    private val dutyId: Long
) : ViewModel() {

    private val _uiState = MutableStateFlow(DutyDetailsListUiState())
    val uiState: StateFlow<DutyDetailsListUiState> = _uiState.asStateFlow()

    init {
        loadRecords()
    }

    fun onIntent(intent: DutyDetailsListIntent) {
        when (intent) {
            DutyDetailsListIntent.LoadRecords -> loadRecords()
            DutyDetailsListIntent.RefreshRecords -> refreshRecords()
            is DutyDetailsListIntent.ShowDeleteConfirmation -> showDeleteConfirmation(intent.recordId)
            DutyDetailsListIntent.HideDeleteConfirmation -> hideDeleteConfirmation()
            is DutyDetailsListIntent.ConfirmDeleteRecord -> confirmDeleteRecord(intent.recordId)
            DutyDetailsListIntent.ClearError -> clearError()
            DutyDetailsListIntent.Retry -> retry()
        }
    }

    private fun loadRecords() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            launch { loadDutyInformation() }
            launch { loadOccurrences() }
        }
    }

    private suspend fun loadDutyInformation() {
        dutyRepository.getDutyById(dutyId)
            .catch { exception ->
                handleError("Error loading duty", exception)
            }
            .collect { result ->
                result.fold(
                    onSuccess = { duty ->
                        _uiState.value = _uiState.value.copy(duty = duty)
                        duty?.let { loadChartData(it) }
                    },
                    onFailure = { exception ->
                        handleError("Failed to load duty", exception)
                    }
                )
            }
    }

    private suspend fun loadOccurrences() {
        try {
            repository.getDutyOccurrencesByDutyId(dutyId).fold(
                onSuccess = { occurrences ->
                    val sortedOccurrences = sortOccurrencesByDate(occurrences)
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        records = sortedOccurrences,
                        error = null
                    )
                },
                onFailure = { exception ->
                    handleError("Failed to load occurrences", exception)
                }
            )
        } catch (exception: Exception) {
            handleError("Error loading occurrences", exception)
        }
    }

    private fun sortOccurrencesByDate(
        occurrences: List<com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrence>
    ): List<com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrence> {
        return occurrences.sortedByDescending { it.completedDate }
    }

    private fun loadChartData(duty: com.joffer.organizeplus.features.dashboard.domain.entities.Duty) {
        viewModelScope.launch {
            repository.getMonthlyChartData(dutyId, duty.type)
                .fold(
                    onSuccess = { chartData ->
                        _uiState.value = _uiState.value.copy(chartData = chartData)
                    },
                    onFailure = { exception ->
                        Napier.e("Failed to load chart data", exception)
                    }
                )
        }
    }

    private fun refreshRecords() = loadRecords()

    private fun deleteRecord(recordId: Long) {
        viewModelScope.launch {
            try {
                repository.deleteDutyOccurrence(recordId).fold(
                    onSuccess = { loadRecords() },
                    onFailure = { exception -> handleError("Failed to delete occurrence", exception) }
                )
            } catch (exception: Exception) {
                handleError("Error deleting occurrence", exception)
            }
        }
    }

    private fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    private fun retry() = loadRecords()

    private fun handleError(message: String, exception: Throwable) {
        Napier.e(message, exception)
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            error = exception.message ?: "Unknown error"
        )
    }
    
    private fun showDeleteConfirmation(recordId: Long) {
        _uiState.value = _uiState.value.copy(
            showDeleteConfirmation = true,
            occurrenceToDelete = recordId
        )
    }
    
    private fun hideDeleteConfirmation() {
        _uiState.value = _uiState.value.copy(
            showDeleteConfirmation = false,
            occurrenceToDelete = null
        )
    }
    
    private fun confirmDeleteRecord(recordId: Long) {
        hideDeleteConfirmation()
        deleteRecord(recordId)
    }
}
