package com.joffer.organizeplus.features.duty.create.domain.validation

import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyForm
import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyFormField
import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyValidationError

class CreateDutyValidator {
    fun validate(form: CreateDutyForm): Map<CreateDutyFormField, CreateDutyValidationError> {
        val errors = mutableMapOf<CreateDutyFormField, CreateDutyValidationError>()

        if (form.title.isBlank()) {
            errors[CreateDutyFormField.Title] = CreateDutyValidationError.EmptyTitle
        }

        return errors
    }
}
