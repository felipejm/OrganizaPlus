package com.joffer.organizeplus.features.dutyList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.dashboard.domain.usecases.DeleteDutyUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.days
import io.github.aakira.napier.Napier

class DutyListViewModel(
    private val repository: DutyRepository,
    private val deleteDutyUseCase: DeleteDutyUseCase
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
            is DutyListIntent.SearchDuties -> searchDuties(intent.query)
            is DutyListIntent.MarkDutyPaid -> markDutyPaid(intent.dutyId)
            is DutyListIntent.EditDuty -> editDuty(intent.dutyId)
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
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                duties = duties,
                                error = null
                            )
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
    
    private fun searchDuties(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        applySearch()
    }
    
    private fun applySearch() {
        val currentState = _uiState.value
        val allDuties = currentState.duties
        
        val filteredDuties = allDuties.filter { duty ->
            currentState.searchQuery.isEmpty() || 
                duty.title.contains(currentState.searchQuery, ignoreCase = true)
        }
        
        _uiState.value = currentState.copy(duties = filteredDuties)
    }
    
    private fun markDutyPaid(dutyId: String) {
        viewModelScope.launch {
            repository.markDutyPaid(dutyId, kotlinx.datetime.Clock.System.now())
                .catch { exception ->
                    Napier.e("Error marking duty as paid", exception)
                    _uiState.value = _uiState.value.copy(
                        error = exception.message ?: "error_marking_paid"
                    )
                }
                .collect { result ->
                    result.fold(
                        onSuccess = {
                            loadDuties()
                        },
                        onFailure = { exception ->
                            Napier.e("Failed to mark duty as paid", exception)
                            _uiState.value = _uiState.value.copy(
                                error = exception.message ?: "error_marking_paid"
                            )
                        }
                    )
                }
        }
    }
    
    
    private fun editDuty(dutyId: String) {
        // Navigation will be handled by the UI layer through onNavigateToEditDuty callback
    }
    
    private fun deleteDuty(dutyId: String) {
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
