package com.joffer.organizeplus.features.duty.create.domain.entities

sealed class CreateDutyFormField {
    object Title : CreateDutyFormField()
    object StartDate : CreateDutyFormField()
    object DueDate : CreateDutyFormField()
    object DutyType : CreateDutyFormField()
    object CategoryName : CreateDutyFormField()
}
