package com.joffer.organizeplus.features.duty.create.domain.entities

sealed class CreateDutyValidationError {
    object EmptyTitle : CreateDutyValidationError()
    object EmptyStartDate : CreateDutyValidationError()
    object EmptyDueDate : CreateDutyValidationError()
    object EmptyCategory : CreateDutyValidationError()
    object InvalidReminderDays : CreateDutyValidationError()
}
