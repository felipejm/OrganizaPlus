package com.joffer.organizeplus.features.dashboard

/**
 * Intent actions for Dashboard
 */
sealed class DashboardIntent {
    object LoadDashboard : DashboardIntent()
    object RefreshDashboard : DashboardIntent()
    data class MarkObligationPaid(val obligationId: String) : DashboardIntent()
    object ClearError : DashboardIntent()
    object Retry : DashboardIntent()
}
