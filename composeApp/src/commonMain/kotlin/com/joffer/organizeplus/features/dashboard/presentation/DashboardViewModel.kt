package com.joffer.organizeplus.features.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.features.dashboard.DashboardIntent
import com.joffer.organizeplus.features.dashboard.DashboardUiState
import com.joffer.organizeplus.features.dashboard.domain.usecases.GetDashboardDataUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Dashboard ViewModel following clean architecture principles
 *
 * Responsibilities:
 * - Manage UI state
 * - Handle user intents
 * - Call use cases
 * - Map domain data to UI state
 *
 * All business logic and repository coordination is delegated to the use case layer
 */
class DashboardViewModel(
    private val getDashboardDataUseCase: GetDashboardDataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    fun onIntent(intent: DashboardIntent) {
        when (intent) {
            is DashboardIntent.LoadDashboard -> loadDashboardData()
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
                        error = exception.message ?: "Unknown error occurred"
                    )
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { dashboardData ->
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                upcomingDuties = dashboardData.upcomingDuties,
                                personalDuties = dashboardData.personalDuties,
                                companyDuties = dashboardData.companyDuties,
                                personalSummary = dashboardData.personalSummary,
                                companySummary = dashboardData.companySummary,
                                error = null
                            )
                        },
                        onFailure = { exception ->
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = exception.message ?: "Failed to load dashboard data"
                            )
                        }
                    )
                }
        }
    }

    private fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    private fun retry() = loadDashboardData()
}
