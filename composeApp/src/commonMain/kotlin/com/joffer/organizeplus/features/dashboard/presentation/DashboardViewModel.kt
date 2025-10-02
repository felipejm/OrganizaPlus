package com.joffer.organizeplus.features.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.common.constants.CategoryConstants
import com.joffer.organizeplus.features.dashboard.DashboardIntent
import com.joffer.organizeplus.features.dashboard.DashboardUiState
import com.joffer.organizeplus.features.dashboard.MonthlySummary
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

                                // Filter Personal duties
                                val personalDuties = allDuties
                                    .filter { it.categoryName == CategoryConstants.PERSONAL }
                                    .take(3)

                                // Filter Company duties
                                val companyDuties = allDuties
                                    .filter { it.categoryName == CategoryConstants.COMPANY }
                                    .take(3)

                                // Convert to DutyWithLastOccurrence
                                val personalDutiesWithOccurrence = personalDuties.map { duty ->
                                    DutyWithLastOccurrence(
                                        duty = duty,
                                        lastOccurrence = null,
                                        hasCurrentMonthOccurrence = false
                                    )
                                }
                                val companyDutiesWithOccurrence = companyDuties.map { duty ->
                                    DutyWithLastOccurrence(
                                        duty = duty,
                                        lastOccurrence = null,
                                        hasCurrentMonthOccurrence = false
                                    )
                                }

                                _uiState.value = _uiState.value.copy(
                                    personalDuties = personalDutiesWithOccurrence,
                                    companyDuties = companyDutiesWithOccurrence
                                )

                                // Load monthly summaries
                                loadMonthlySummaries()

                                // Load last occurrence for Personal duties
                                personalDuties.forEach { duty ->
                                    launch {
                                        dutyOccurrenceRepository.getLastOccurrenceByDutyId(duty.id)
                                            .fold(
                                                onSuccess = { lastOccurrence ->
                                                    val updatedDuties =
                                                        _uiState.value.personalDuties.map { dutyWithOccurrence ->
                                                            if (dutyWithOccurrence.duty.id == duty.id) {
                                                                dutyWithOccurrence.copy(
                                                                    lastOccurrence = lastOccurrence
                                                                )
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
                                                    val updatedDuties =
                                                        _uiState.value.companyDuties.map { dutyWithOccurrence ->
                                                            if (dutyWithOccurrence.duty.id == duty.id) {
                                                                dutyWithOccurrence.copy(
                                                                    lastOccurrence = lastOccurrence
                                                                )
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
    private fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    private fun retry() {
        loadDashboardData()
    }

    private fun loadMonthlySummaries() {
        viewModelScope.launch {
            try {
                val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                val currentMonth = currentDate.monthNumber
                val currentYear = currentDate.year

                // Load all duties first to get accurate totals
                val allDuties = dutyRepository.getAllDuties()
                    .catch { error ->
                        Napier.e("Failed to load duties for summary: ${error.message}")
                        emit(Result.success(emptyList()))
                    }
                    .first() // Get the first emission
                    .getOrElse {
                        Napier.e("Failed to get duties result for summary")
                        emptyList()
                    }

                // Calculate totals for each category
                val personalDutiesCount = allDuties.count { it.categoryName == CategoryConstants.PERSONAL }
                val companyDutiesCount = allDuties.count { it.categoryName == CategoryConstants.COMPANY }

                // Load summaries in parallel
                val personalSummaryDeferred = async {
                    loadCategorySummary(
                        categoryName = CategoryConstants.PERSONAL,
                        totalTasks = personalDutiesCount,
                        currentMonth = currentMonth,
                        currentYear = currentYear
                    )
                }

                val companySummaryDeferred = async {
                    loadCategorySummary(
                        categoryName = CategoryConstants.COMPANY,
                        totalTasks = companyDutiesCount,
                        currentMonth = currentMonth,
                        currentYear = currentYear
                    )
                }

                // Wait for both summaries to complete and update UI
                val personalSummary = personalSummaryDeferred.await()
                val companySummary = companySummaryDeferred.await()

                _uiState.value = _uiState.value.copy(
                    personalSummary = personalSummary,
                    companySummary = companySummary
                )
            } catch (e: Exception) {
                Napier.e("Failed to load monthly summaries: ${e.message}")
                // Set empty summaries on error
                _uiState.value = _uiState.value.copy(
                    personalSummary = null,
                    companySummary = null
                )
            }
        }
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
