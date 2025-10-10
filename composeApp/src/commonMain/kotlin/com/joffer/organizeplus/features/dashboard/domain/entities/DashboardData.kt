package com.joffer.organizeplus.features.dashboard.domain.entities

import com.joffer.organizeplus.features.dashboard.MonthlySummary

/**
 * Complete dashboard data aggregated from multiple sources
 */
data class DashboardData(
    val upcomingDuties: List<Duty> = emptyList(),
    val personalDuties: List<DutyWithLastOccurrence> = emptyList(),
    val companyDuties: List<DutyWithLastOccurrence> = emptyList(),
    val personalSummary: MonthlySummary? = null,
    val companySummary: MonthlySummary? = null
)
