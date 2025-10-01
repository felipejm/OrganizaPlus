package com.joffer.organizeplus.features.duty.create.domain.entities

sealed class CreateDutyValidationError {
    object EmptyTitle : CreateDutyValidationError()
    object EmptyCategory : CreateDutyValidationError()
}
