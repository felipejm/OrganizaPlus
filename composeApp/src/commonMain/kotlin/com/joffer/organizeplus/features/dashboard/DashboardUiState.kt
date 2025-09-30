package com.joffer.organizeplus.features.dashboard

import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyWithLastOccurrence

data class DashboardUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val upcomingDuties: List<Duty> = emptyList(),
    val personalDuties: List<DutyWithLastOccurrence> = emptyList(),
    val companyDuties: List<DutyWithLastOccurrence> = emptyList(),
    val showSnoozeSnackbar: Boolean = false,
    val snoozedDutyId: String? = null
)
