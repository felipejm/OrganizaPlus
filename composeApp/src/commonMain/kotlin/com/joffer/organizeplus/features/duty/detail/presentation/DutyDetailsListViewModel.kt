package com.joffer.organizeplus.features.duty.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.duty.detail.domain.entities.ChartData
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrence
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
            is DutyDetailsListIntent.DeleteRecord -> deleteRecord(intent.recordId)
            DutyDetailsListIntent.ClearError -> clearError()
            DutyDetailsListIntent.Retry -> retry()
        }
    }

    private fun loadRecords() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            // Load duty information
            dutyRepository.getDutyById(dutyId)
                .catch { exception ->
                    Napier.e("Error loading duty", exception)
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { duty ->
                            _uiState.value = _uiState.value.copy(duty = duty)
                        },
                        onFailure = { exception ->
                            Napier.e("Failed to load duty", exception)
                        }
                    )
                }

            // Load occurrences
            try {
                val result = repository.getDutyOccurrencesByDutyId(dutyId)
                result.fold(
                    onSuccess = { occurrences ->
                        // Sort occurrences by date (most recent first)
                        val sortedOccurrences = occurrences.sortedByDescending { it.completedDate }

                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            records = sortedOccurrences,
                            error = null
                        )

                        // Load chart data if duty is available
                        _uiState.value.duty?.let { dutyInfo ->
                            launch {
                                repository.getMonthlyChartData(dutyId, dutyInfo.type)
                                    .fold(
                                        onSuccess = { chartData ->
                                            _uiState.value =
                                                _uiState.value.copy(chartData = chartData)
                                        },
                                        onFailure = { exception ->
                                            Napier.e("Failed to load chart data", exception)
                                        }
                                    )
                            }
                        }
                    },
                    onFailure = { exception ->
                        Napier.e("Failed to load occurrences", exception)
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = exception.message ?: "error_loading_occurrences"
                        )
                    }
                )
            } catch (exception: Exception) {
                Napier.e("Error loading occurrences", exception)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = exception.message ?: "error_loading_occurrences"
                )
            }
        }
    }

    private fun refreshRecords() {
        loadRecords()
    }

    private fun deleteRecord(recordId: Long) {
        viewModelScope.launch {
            try {
                val result = repository.deleteDutyOccurrence(recordId)
                result.fold(
                    onSuccess = {
                        loadRecords() // Refresh the list
                    },
                    onFailure = { exception ->
                        Napier.e("Failed to delete occurrence", exception)
                        _uiState.value = _uiState.value.copy(
                            error = exception.message ?: "error_deleting_occurrence"
                        )
                    }
                )
            } catch (exception: Exception) {
                Napier.e("Error deleting occurrence", exception)
                _uiState.value = _uiState.value.copy(
                    error = exception.message ?: "error_deleting_occurrence"
                )
            }
        }
    }

    private fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    private fun retry() {
        loadRecords()
    }
}

data class DutyDetailsListUiState(
    val isLoading: Boolean = false,
    val records: List<DutyOccurrence> = emptyList(),
    val duty: Duty? = null,
    val chartData: ChartData? = null,
    val error: String? = null
)

sealed class DutyDetailsListIntent {
    object LoadRecords : DutyDetailsListIntent()
    object RefreshRecords : DutyDetailsListIntent()
    data class DeleteRecord(val recordId: Long) : DutyDetailsListIntent()
    object ClearError : DutyDetailsListIntent()
    object Retry : DutyDetailsListIntent()
}
