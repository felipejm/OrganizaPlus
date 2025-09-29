package com.joffer.organizeplus.features.duty.domain.entities

sealed class ValidationError {
    object EmptyTitle : ValidationError()
    object EmptyStartDate : ValidationError()
    object EmptyDueDate : ValidationError()
    object EmptyCategory : ValidationError()
    object InvalidReminderDays : ValidationError()
}
