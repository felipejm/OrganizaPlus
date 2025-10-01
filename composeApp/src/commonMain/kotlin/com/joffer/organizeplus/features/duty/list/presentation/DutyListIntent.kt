package com.joffer.organizeplus.features.duty.list.presentation

sealed class DutyListIntent {
    object LoadDuties : DutyListIntent()
    object RefreshDuties : DutyListIntent()
    data class SearchDuties(val query: String) : DutyListIntent()
    data class DeleteDuty(val dutyId: Long) : DutyListIntent()
    object ClearError : DutyListIntent()
    object Retry : DutyListIntent()
}
