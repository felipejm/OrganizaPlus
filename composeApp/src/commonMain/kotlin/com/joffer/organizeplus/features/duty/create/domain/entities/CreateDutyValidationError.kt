package com.joffer.organizeplus.features.createDuty.domain.entities

sealed class CreateDutyValidationError {
    object EmptyTitle : CreateDutyValidationError()
    object EmptyStartDate : CreateDutyValidationError()
    object EmptyDueDate : CreateDutyValidationError()
    object EmptyCategory : CreateDutyValidationError()
    object InvalidReminderDays : CreateDutyValidationError()
}
