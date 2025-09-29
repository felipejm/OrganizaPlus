package com.joffer.organizeplus.features.duty.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.features.duty.detail.domain.entities.DutyDetails
import com.joffer.organizeplus.features.duty.detail.domain.repositories.DutyDetailsRepository
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import io.github.aakira.napier.Napier

class DutyDetailsListViewModel(
    private val repository: DutyDetailsRepository,
    private val dutyRepository: DutyRepository,
    private val dutyId: String
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DutyDetailsListUiState())
    val uiState: StateFlow<DutyDetailsListUiState> = _uiState.asStateFlow()
    
    fun getDutyId(): String = dutyId
    
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
            
            // Load duty title
            dutyRepository.getDutyById(dutyId)
                .catch { exception ->
                    Napier.e("Error loading duty", exception)
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { duty ->
                            _uiState.value = _uiState.value.copy(dutyTitle = duty?.title ?: "")
                        },
                        onFailure = { exception ->
                            Napier.e("Failed to load duty", exception)
                        }
                    )
                }
            
            // Load records
            repository.getRecordsByDutyId(dutyId)
                .catch { exception ->
                    Napier.e("Error loading records", exception)
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "error_loading_records"
                    )
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { records ->
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                records = records,
                                error = null
                            )
                        },
                        onFailure = { exception ->
                            Napier.e("Failed to load records", exception)
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = exception.message ?: "error_loading_records"
                            )
                        }
                    )
                }
        }
    }
    
    private fun refreshRecords() {
        loadRecords()
    }
    
    private fun deleteRecord(recordId: String) {
        viewModelScope.launch {
            repository.deleteRecord(recordId)
                .catch { exception ->
                    Napier.e("Error deleting record", exception)
                    _uiState.value = _uiState.value.copy(
                        error = exception.message ?: "error_deleting_record"
                    )
                }
                .collect { result ->
                    result.fold(
                        onSuccess = {
                            loadRecords() // Refresh the list
                        },
                        onFailure = { exception ->
                            Napier.e("Failed to delete record", exception)
                            _uiState.value = _uiState.value.copy(
                                error = exception.message ?: "error_deleting_record"
                            )
                        }
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
    val records: List<DutyDetails> = emptyList(),
    val dutyTitle: String = "",
    val error: String? = null
)

sealed class DutyDetailsListIntent {
    object LoadRecords : DutyDetailsListIntent()
    object RefreshRecords : DutyDetailsListIntent()
    data class DeleteRecord(val recordId: String) : DutyDetailsListIntent()
    object ClearError : DutyDetailsListIntent()
    object Retry : DutyDetailsListIntent()
}
