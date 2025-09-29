package com.joffer.organizeplus.features.createDuty.presentation

import com.joffer.organizeplus.features.createDuty.domain.entities.CreateDutyForm
import com.joffer.organizeplus.features.createDuty.domain.entities.CreateDutyFormField

sealed class CreateDutyIntent {
    object SaveCreateDuty : CreateDutyIntent()
    object CancelForm : CreateDutyIntent()
    object ClearError : CreateDutyIntent()
    object ClearSuccess : CreateDutyIntent()
    object ClearErrorSnackbar : CreateDutyIntent()
    object ClearSuccessSnackbar : CreateDutyIntent()
    object ShowCustomRule : CreateDutyIntent()
    object HideCustomRule : CreateDutyIntent()
    object ShowStartDateReminderOptions : CreateDutyIntent()
    object HideStartDateReminderOptions : CreateDutyIntent()
    object ShowDueDateReminderOptions : CreateDutyIntent()
    object HideDueDateReminderOptions : CreateDutyIntent()
    object ShowTimePicker : CreateDutyIntent()
    object HideTimePicker : CreateDutyIntent()
    
    data class UpdateFormField(
        val field: CreateDutyFormField,
        val value: Any
    ) : CreateDutyIntent()
    
    data class SelectTime(val time: String) : CreateDutyIntent()
}
