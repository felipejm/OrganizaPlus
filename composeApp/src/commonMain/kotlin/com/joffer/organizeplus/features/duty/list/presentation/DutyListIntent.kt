package com.joffer.organizeplus.features.duty.list.presentation

sealed class DutyListIntent {
    object LoadDuties : DutyListIntent()
    object RefreshDuties : DutyListIntent()
    data class ShowDeleteConfirmation(val dutyId: Long) : DutyListIntent()
    object HideDeleteConfirmation : DutyListIntent()
    data class ConfirmDeleteDuty(val dutyId: Long) : DutyListIntent()
    object ClearError : DutyListIntent()
    object Retry : DutyListIntent()
}
