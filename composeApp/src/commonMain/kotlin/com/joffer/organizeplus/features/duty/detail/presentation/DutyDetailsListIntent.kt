package com.joffer.organizeplus.features.duty.detail.presentation

sealed class DutyDetailsListIntent {
    object LoadRecords : DutyDetailsListIntent()
    object RefreshRecords : DutyDetailsListIntent()
    data class ShowDeleteConfirmation(val recordId: Long) : DutyDetailsListIntent()
    object HideDeleteConfirmation : DutyDetailsListIntent()
    data class ConfirmDeleteRecord(val recordId: Long) : DutyDetailsListIntent()
    object ClearError : DutyDetailsListIntent()
    object Retry : DutyDetailsListIntent()
}
