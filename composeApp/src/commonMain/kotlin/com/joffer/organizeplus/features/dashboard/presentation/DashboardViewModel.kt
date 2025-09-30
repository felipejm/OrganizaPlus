package com.joffer.organizeplus.features.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.features.dashboard.domain.usecases.GetDashboardDataUseCase
import com.joffer.organizeplus.features.dashboard.domain.usecases.MarkObligationPaidUseCase
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.duty.occurrence.domain.repositories.DutyOccurrenceRepository
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyWithLastOccurrence
import com.joffer.organizeplus.features.dashboard.DashboardUiState
import com.joffer.organizeplus.features.dashboard.DashboardIntent
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import io.github.aakira.napier.Napier
class DashboardViewModel(
    private val getDashboardDataUseCase: GetDashboardDataUseCase,
    private val markObligationPaidUseCase: MarkObligationPaidUseCase,
    private val dutyRepository: DutyRepository,
    private val dutyOccurrenceRepository: DutyOccurrenceRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    
    private val _userName = MutableStateFlow("Usuário")
    val userName: StateFlow<String> = _userName.asStateFlow()
    
    init {
        loadDashboardData()
    }
    
    fun onIntent(intent: DashboardIntent) {
        when (intent) {
            is DashboardIntent.LoadDashboard -> loadDashboardData()
            is DashboardIntent.RefreshDashboard -> refreshDashboard()
            is DashboardIntent.MarkObligationPaid -> markObligationPaid(intent.obligationId)
            is DashboardIntent.ClearError -> clearError()
            is DashboardIntent.Retry -> retry()
        }
    }
    
    private fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            // Load both upcoming and latest duties
            launch {
                getDashboardDataUseCase()
                    .catch { exception ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = exception.message ?: "Erro desconhecido"
                        )
                    }
                    .collect { result ->
                        result.fold(
                            onSuccess = { data ->
                                _uiState.value = _uiState.value.copy(
                                    isLoading = false,
                                    upcomingDuties = data.upcomingDuties,
                                    error = null
                                )
                            },
                            onFailure = { exception ->
                                _uiState.value = _uiState.value.copy(
                                    isLoading = false,
                                    error = exception.message ?: "Erro ao carregar dados"
                                )
                            }
                        )
                    }
            }
            
            // Load latest duties separately with last occurrence data
            launch {
                dutyRepository.getLatestDuties(3)
                    .catch { /* ignore errors for latest duties */ }
                    .collect { result ->
                        result.fold(
                            onSuccess = { latestDuties ->
                                // Load last occurrence for each duty
                                val dutiesWithLastOccurrence = latestDuties.map { duty ->
                                    DutyWithLastOccurrence(
                                        duty = duty,
                                        lastOccurrence = null // Will be loaded separately
                                    )
                                }
                                _uiState.value = _uiState.value.copy(
                                    latestDuties = dutiesWithLastOccurrence
                                )
                                
                                // Load last occurrence for each duty
                                latestDuties.forEach { duty ->
                                    launch {
                                        dutyOccurrenceRepository.getLastOccurrenceByDutyId(duty.id)
                                            .fold(
                                                onSuccess = { lastOccurrence ->
                                                    val updatedDuties = _uiState.value.latestDuties.map { dutyWithOccurrence ->
                                                        if (dutyWithOccurrence.duty.id == duty.id) {
                                                            dutyWithOccurrence.copy(lastOccurrence = lastOccurrence)
                                                        } else {
                                                            dutyWithOccurrence
                                                        }
                                                    }
                                                    _uiState.value = _uiState.value.copy(
                                                        latestDuties = updatedDuties
                                                    )
                                                },
                                                onFailure = { /* ignore */ }
                                            )
                                    }
                                }
                            },
                            onFailure = { /* ignore */ }
                        )
                    }
            }
        }
    }
    
    private fun refreshDashboard() {
        loadDashboardData()
    }
    
    private fun markObligationPaid(obligationId: String) {
        viewModelScope.launch {
            markObligationPaidUseCase(obligationId)
                .collect { result ->
                    result.fold(
                        onSuccess = {
                            // Refresh data after successful payment
                            loadDashboardData()
                        },
                        onFailure = { exception ->
                            _uiState.value = _uiState.value.copy(
                                error = exception.message ?: "Erro ao marcar como pago"
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
        loadDashboardData()
    }
    
    fun setUserName(name: String) {
        _userName.value = name
    }
}
