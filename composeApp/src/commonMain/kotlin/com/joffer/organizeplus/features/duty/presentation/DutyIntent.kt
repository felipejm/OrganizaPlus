package com.joffer.organizeplus.features.duty.presentation

import com.joffer.organizeplus.features.duty.domain.entities.DutyForm
import com.joffer.organizeplus.features.duty.domain.entities.DutyFormField

sealed class DutyIntent {
    object SaveDuty : DutyIntent()
    object CancelForm : DutyIntent()
    object ClearError : DutyIntent()
    object ClearSuccess : DutyIntent()
    object ClearErrorSnackbar : DutyIntent()
    object ClearSuccessSnackbar : DutyIntent()
    object ShowCustomRule : DutyIntent()
    object HideCustomRule : DutyIntent()
    object ShowStartDateReminderOptions : DutyIntent()
    object HideStartDateReminderOptions : DutyIntent()
    object ShowDueDateReminderOptions : DutyIntent()
    object HideDueDateReminderOptions : DutyIntent()
    object ShowTimePicker : DutyIntent()
    object HideTimePicker : DutyIntent()
    
    data class UpdateFormField(
        val field: DutyFormField,
        val value: Any
    ) : DutyIntent()
    
    data class SelectTime(val time: String) : DutyIntent()
}