package com.joffer.organizeplus.features.duty.create.domain.entities

sealed class CreateDutyFormField {
    object Title : CreateDutyFormField()
    object StartDay : CreateDutyFormField()
    object DueDay : CreateDutyFormField()
    object DutyType : CreateDutyFormField()
    object CategoryName : CreateDutyFormField()
}
