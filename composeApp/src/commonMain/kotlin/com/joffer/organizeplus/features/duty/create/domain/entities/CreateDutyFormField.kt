package com.joffer.organizeplus.features.duty.create.domain.entities

sealed class CreateDutyFormField {
    object Title : CreateDutyFormField()
    object DutyType : CreateDutyFormField()
}
