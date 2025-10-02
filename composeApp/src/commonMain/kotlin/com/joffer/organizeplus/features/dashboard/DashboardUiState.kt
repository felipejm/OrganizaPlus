package com.joffer.organizeplus.features.dashboard

import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyWithLastOccurrence

data class DashboardUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val upcomingDuties: List<Duty> = emptyList(),
    val personalDuties: List<DutyWithLastOccurrence> = emptyList(),
    val companyDuties: List<DutyWithLastOccurrence> = emptyList(),
    val personalSummary: MonthlySummary? = null,
    val companySummary: MonthlySummary? = null,
    val showSnoozeSnackbar: Boolean = false,
    val snoozedDutyId: String? = null
)

data class MonthlySummary(
    val totalAmountPaid: Double = 0.0,
    val totalCompleted: Int = 0,
    val totalTasks: Int = 0,
    val currentMonth: Int = 0,
    val year: Int = 0
)
