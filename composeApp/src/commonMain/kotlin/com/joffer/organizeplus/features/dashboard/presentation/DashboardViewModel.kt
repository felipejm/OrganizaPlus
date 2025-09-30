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
            
            // Load duties with closest due dates separately for Personal and Company
            launch {
                dutyRepository.getAllDuties()
                    .catch { /* ignore errors for duties */ }
                    .collect { result ->
                        result.fold(
                            onSuccess = { allDuties ->
                                val currentDay = kotlinx.datetime.Clock.System.now()
                                    .toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
                                    .date.dayOfMonth
                                
                                // Filter and sort Personal duties by closest due date
                                val personalDuties = allDuties
                                    .filter { it.categoryName == "Personal" }
                                    .sortedBy { duty ->
                                        val daysUntil = if (duty.dueDay >= currentDay) {
                                            duty.dueDay - currentDay
                                        } else {
                                            (31 - currentDay) + duty.dueDay
                                        }
                                        daysUntil
                                    }
                                    .take(3)
                                
                                // Filter and sort Company duties by closest due date
                                val companyDuties = allDuties
                                    .filter { it.categoryName == "Company" }
                                    .sortedBy { duty ->
                                        val daysUntil = if (duty.dueDay >= currentDay) {
                                            duty.dueDay - currentDay
                                        } else {
                                            (31 - currentDay) + duty.dueDay
                                        }
                                        daysUntil
                                    }
                                    .take(3)
                                
                                // Convert to DutyWithLastOccurrence
                                val personalDutiesWithOccurrence = personalDuties.map { duty ->
                                    DutyWithLastOccurrence(duty = duty, lastOccurrence = null)
                                }
                                val companyDutiesWithOccurrence = companyDuties.map { duty ->
                                    DutyWithLastOccurrence(duty = duty, lastOccurrence = null)
                                }
                                
                                _uiState.value = _uiState.value.copy(
                                    personalDuties = personalDutiesWithOccurrence,
                                    companyDuties = companyDutiesWithOccurrence
                                )
                                
                                // Load last occurrence for Personal duties
                                personalDuties.forEach { duty ->
                                    launch {
                                        dutyOccurrenceRepository.getLastOccurrenceByDutyId(duty.id)
                                            .fold(
                                                onSuccess = { lastOccurrence ->
                                                    val updatedDuties = _uiState.value.personalDuties.map { dutyWithOccurrence ->
                                                        if (dutyWithOccurrence.duty.id == duty.id) {
                                                            dutyWithOccurrence.copy(lastOccurrence = lastOccurrence)
                                                        } else {
                                                            dutyWithOccurrence
                                                        }
                                                    }
                                                    _uiState.value = _uiState.value.copy(
                                                        personalDuties = updatedDuties
                                                    )
                                                },
                                                onFailure = { /* ignore */ }
                                            )
                                    }
                                }
                                
                                // Load last occurrence for Company duties
                                companyDuties.forEach { duty ->
                                    launch {
                                        dutyOccurrenceRepository.getLastOccurrenceByDutyId(duty.id)
                                            .fold(
                                                onSuccess = { lastOccurrence ->
                                                    val updatedDuties = _uiState.value.companyDuties.map { dutyWithOccurrence ->
                                                        if (dutyWithOccurrence.duty.id == duty.id) {
                                                            dutyWithOccurrence.copy(lastOccurrence = lastOccurrence)
                                                        } else {
                                                            dutyWithOccurrence
                                                        }
                                                    }
                                                    _uiState.value = _uiState.value.copy(
                                                        companyDuties = updatedDuties
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
