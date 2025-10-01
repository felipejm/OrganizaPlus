package com.joffer.organizeplus.features.duty.occurrence.domain.validation

import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrenceForm
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrenceFormField

class DutyOccurrenceValidator {

    fun validate(form: DutyOccurrenceForm): Map<DutyOccurrenceFormField, ValidationError> {
        val errors = mutableMapOf<DutyOccurrenceFormField, ValidationError>()

        // Only validate paid amount for payable duties
        if (form.dutyType == DutyType.PAYABLE && form.paidAmount <= 0) {
            errors[DutyOccurrenceFormField.PaidAmount] = ValidationError.InvalidAmount
        }

        if (form.dutyId.isBlank()) {
            errors[DutyOccurrenceFormField.PaidAmount] = ValidationError.BlankField
        }

        return errors
    }
}
