package com.joffer.organizeplus.features.obligationList.presentation

sealed class ObligationListIntent {
    object LoadObligations : ObligationListIntent()
    object RefreshObligations : ObligationListIntent()
    data class SearchObligations(val query: String) : ObligationListIntent()
    data class MarkObligationPaid(val obligationId: String) : ObligationListIntent()
    data class EditObligation(val obligationId: String) : ObligationListIntent()
    data class DeleteObligation(val obligationId: String) : ObligationListIntent()
    object ClearError : ObligationListIntent()
    object Retry : ObligationListIntent()
}
