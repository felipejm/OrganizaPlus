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

                            // Load last occurrence for each duty
                            val dutiesWithLastOccurrence = filteredDuties.map { duty ->
                                DutyWithLastOccurrence(
                                    duty = duty,
                                    lastOccurrence = null // Will be loaded separately
                                )
                            }
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                duties = dutiesWithLastOccurrence,
                                error = null
                            )

                            // Load last occurrence for each duty
                            filteredDuties.forEach { duty ->
                                launch {
                                    dutyOccurrenceRepository.getLastOccurrenceByDutyId(duty.id)
                                        .fold(
                                            onSuccess = { lastOccurrence ->
                                                val updatedDuties = _uiState.value.duties.map { dutyWithOccurrence ->
                                                    if (dutyWithOccurrence.duty.id == duty.id) {
                                                        dutyWithOccurrence.copy(lastOccurrence = lastOccurrence)
                                                    } else {
                                                        dutyWithOccurrence
                                                    }
                                                }
                                                _uiState.value = _uiState.value.copy(
                                                    duties = updatedDuties
                                                )
                                            },
                                            onFailure = { /* ignore */ }
                                        )
                                }
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
