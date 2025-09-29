package com.joffer.organizeplus.features.duty.create.domain.entities

sealed class CreateDutyValidationError {
    object EmptyTitle : CreateDutyValidationError()
    object InvalidStartDay : CreateDutyValidationError()
    object InvalidDueDay : CreateDutyValidationError()
    object EmptyCategory : CreateDutyValidationError()
}
