package com.joffer.organizeplus.features.dashboard.domain.entities

data class DashboardData(
    val upcomingDuties: List<Duty>,
    val latestDuties: List<Duty>
)
