package com.joffer.organizeplus.features.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.features.dashboard.domain.usecases.GetDashboardDataUseCase
import com.joffer.organizeplus.features.dashboard.domain.usecases.MarkObligationPaidUseCase
import com.joffer.organizeplus.features.dashboard.domain.usecases.MarkObligationsPaidUseCase
import com.joffer.organizeplus.features.dashboard.domain.usecases.SnoozeObligationUseCase
import com.joffer.organizeplus.features.dashboard.DashboardUiState
import com.joffer.organizeplus.features.dashboard.DashboardIntent
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import io.github.aakira.napier.Napier
class DashboardViewModel(
    private val getDashboardDataUseCase: GetDashboardDataUseCase,
    private val markObligationPaidUseCase: MarkObligationPaidUseCase,
    private val markObligationsPaidUseCase: MarkObligationsPaidUseCase,
    private val snoozeObligationUseCase: SnoozeObligationUseCase
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
            is DashboardIntent.SnoozeObligation -> snoozeObligation(intent.obligationId)
            is DashboardIntent.ClearError -> clearError()
            is DashboardIntent.Retry -> retry()
        }
    }
    
    private fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
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
    
    
    private fun snoozeObligation(obligationId: String) {
        viewModelScope.launch {
            snoozeObligationUseCase(obligationId)
                .collect { result ->
                    result.fold(
                        onSuccess = {
                            loadDashboardData()
                        },
                        onFailure = { exception ->
                            _uiState.value = _uiState.value.copy(
                                error = exception.message ?: "Erro ao adiar obrigação"
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
