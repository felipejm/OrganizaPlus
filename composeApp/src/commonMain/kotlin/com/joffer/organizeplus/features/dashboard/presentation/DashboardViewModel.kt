package com.joffer.organizeplus.features.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.common.constants.CategoryConstants
import com.joffer.organizeplus.features.dashboard.DashboardIntent
import com.joffer.organizeplus.features.dashboard.DashboardUiState
import com.joffer.organizeplus.features.dashboard.MonthlySummary
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyWithLastOccurrence
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.dashboard.domain.usecases.GetDashboardDataUseCase
import com.joffer.organizeplus.features.duty.occurrence.domain.repositories.DutyOccurrenceRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DashboardViewModel(
    private val getDashboardDataUseCase: GetDashboardDataUseCase,
    private val dutyRepository: DutyRepository,
    private val dutyOccurrenceRepository: DutyOccurrenceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    fun onIntent(intent: DashboardIntent) {
        when (intent) {
            is DashboardIntent.LoadDashboard -> loadDashboardData()
            is DashboardIntent.RefreshDashboard -> refreshDashboard()
            is DashboardIntent.ClearError -> clearError()
            is DashboardIntent.Retry -> retry()
        }
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            launch { loadUpcomingDuties() }
            launch { loadDutiesByCategory() }
        }
    }

    private suspend fun loadUpcomingDuties() {
        getDashboardDataUseCase()
            .catch { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = exception.message ?: "Unknown error"
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
                            error = exception.message ?: "Failed to load data"
                        )
                    }
                )
            }
    }

    private suspend fun loadDutiesByCategory() {
        dutyRepository.getAllDuties()
            .catch { /* ignore errors for duties */ }
            .collect { result ->
                result.fold(
                    onSuccess = { allDuties ->
                        val personalDuties = allDuties
                            .filter { it.categoryName == CategoryConstants.PERSONAL }
                            .take(3)
                            .map { duty -> createDutyWithOccurrence(duty) }

                        val companyDuties = allDuties
                            .filter { it.categoryName == CategoryConstants.COMPANY }
                            .take(3)
                            .map { duty -> createDutyWithOccurrence(duty) }

                        _uiState.value = _uiState.value.copy(
                            personalDuties = personalDuties,
                            companyDuties = companyDuties
                        )

                        loadMonthlySummaries()
                        loadLastOccurrences(personalDuties, companyDuties)
                    },
                    onFailure = { /* ignore */ }
                )
            }
    }

    private fun createDutyWithOccurrence(duty: Duty) = DutyWithLastOccurrence(
        duty = duty,
        lastOccurrence = null,
        hasCurrentMonthOccurrence = false
    )

    private fun loadLastOccurrences(
        personalDuties: List<DutyWithLastOccurrence>,
        companyDuties: List<DutyWithLastOccurrence>
    ) {
        personalDuties.forEach { dutyWithOccurrence ->
            loadLastOccurrenceForDuty(dutyWithOccurrence.duty.id) { updatedDuty ->
                val updatedDuties = personalDuties.map { duty ->
                    if (duty.duty.id == updatedDuty.duty.id) updatedDuty else duty
                }
                _uiState.value = _uiState.value.copy(personalDuties = updatedDuties)
            }
        }

        companyDuties.forEach { dutyWithOccurrence ->
            loadLastOccurrenceForDuty(dutyWithOccurrence.duty.id) { updatedDuty ->
                val updatedDuties = companyDuties.map { duty ->
                    if (duty.duty.id == updatedDuty.duty.id) updatedDuty else duty
                }
                _uiState.value = _uiState.value.copy(companyDuties = updatedDuties)
            }
        }
    }

    private fun loadLastOccurrenceForDuty(
        dutyId: Long,
        onSuccess: (DutyWithLastOccurrence) -> Unit
    ) {
        viewModelScope.launch {
            dutyOccurrenceRepository.getLastOccurrenceByDutyId(dutyId)
                .fold(
                    onSuccess = { lastOccurrence ->
                        val updatedDuty = DutyWithLastOccurrence(
                            duty = _uiState.value.personalDuties.find { it.duty.id == dutyId }?.duty
                                ?: _uiState.value.companyDuties.find { it.duty.id == dutyId }?.duty
                                ?: return@fold,
                            lastOccurrence = lastOccurrence,
                            hasCurrentMonthOccurrence = false
                        )
                        onSuccess(updatedDuty)
                    },
                    onFailure = { /* ignore */ }
                )
        }
    }

    private fun refreshDashboard() = loadDashboardData()
    
    private fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    private fun retry() = loadDashboardData()

    private fun loadMonthlySummaries() {
        viewModelScope.launch {
            try {
                val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                val currentMonth = currentDate.monthNumber
                val currentYear = currentDate.year

                val allDuties = loadAllDutiesForSummary()
                val dutyCounts = calculateDutyCounts(allDuties)

                val personalSummary = async {
                    loadCategorySummary(
                        categoryName = CategoryConstants.PERSONAL,
                        totalTasks = dutyCounts.personal,
                        currentMonth = currentMonth,
                        currentYear = currentYear
                    )
                }

                val companySummary = async {
                    loadCategorySummary(
                        categoryName = CategoryConstants.COMPANY,
                        totalTasks = dutyCounts.company,
                        currentMonth = currentMonth,
                        currentYear = currentYear
                    )
                }

                _uiState.value = _uiState.value.copy(
                    personalSummary = personalSummary.await(),
                    companySummary = companySummary.await()
                )
            } catch (e: Exception) {
                Napier.e("Failed to load monthly summaries: ${e.message}")
                _uiState.value = _uiState.value.copy(
                    personalSummary = null,
                    companySummary = null
                )
            }
        }
    }

    private suspend fun loadAllDutiesForSummary(): List<Duty> {
        return dutyRepository.getAllDuties()
            .catch { error ->
                Napier.e("Failed to load duties for summary: ${error.message}")
                emit(Result.success(emptyList()))
            }
            .first()
            .getOrElse {
                Napier.e("Failed to get duties result for summary")
                emptyList()
            }
    }

    private data class DutyCounts(
        val personal: Int,
        val company: Int
    )

    private fun calculateDutyCounts(duties: List<Duty>): DutyCounts {
        return DutyCounts(
            personal = duties.count { it.categoryName == CategoryConstants.PERSONAL },
            company = duties.count { it.categoryName == CategoryConstants.COMPANY }
        )
    }

    private suspend fun loadCategorySummary(
        categoryName: String,
        totalTasks: Int,
        currentMonth: Int,
        currentYear: Int
    ): MonthlySummary? {
        return try {
            dutyOccurrenceRepository.getMonthlyOccurrences(
                categoryName,
                currentMonth,
                currentYear
            ).fold(
                onSuccess = { occurrences ->
                    val totalAmountPaid = occurrences
                        .sumOf { it.paidAmount ?: 0.0 }

                    val totalCompleted = occurrences
                        .map { it.dutyId }
                        .distinct()
                        .size

                    MonthlySummary(
                        totalAmountPaid = totalAmountPaid,
                        totalCompleted = totalCompleted,
                        totalTasks = totalTasks,
                        currentMonth = currentMonth,
                        year = currentYear
                    )
                },
                onFailure = { error ->
                    Napier.e("Failed to load $categoryName occurrences: ${error.message}")
                    null
                }
            )
        } catch (e: Exception) {
            Napier.e("Exception loading $categoryName summary: ${e.message}")
            null
        }
    }
}
