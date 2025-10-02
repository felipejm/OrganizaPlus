package com.joffer.organizeplus.features.duty.detail.presentation

sealed class DutyDetailsListIntent {
    object LoadRecords : DutyDetailsListIntent()
    object RefreshRecords : DutyDetailsListIntent()
    data class DeleteRecord(val recordId: Long) : DutyDetailsListIntent()
    object ClearError : DutyDetailsListIntent()
    object Retry : DutyDetailsListIntent()
}
