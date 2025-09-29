package com.joffer.organizeplus.features.duty.create.presentation

import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyForm
import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyFormField

sealed class CreateDutyIntent {
    object SaveCreateDuty : CreateDutyIntent()
    object CancelForm : CreateDutyIntent()
    object ClearError : CreateDutyIntent()
    object ClearSuccess : CreateDutyIntent()
    object ClearErrorSnackbar : CreateDutyIntent()
    object ClearSuccessSnackbar : CreateDutyIntent()
    
    data class UpdateFormField(
        val field: CreateDutyFormField,
        val value: Any
    ) : CreateDutyIntent()
}
