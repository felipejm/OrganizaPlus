package com.joffer.organizeplus.features.dashboard

/**
 * Intent actions for Dashboard
 */
sealed class DashboardIntent {
    object LoadDashboard : DashboardIntent()
    object ClearError : DashboardIntent()
    object Retry : DashboardIntent()
}
