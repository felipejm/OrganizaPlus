package com.joffer.organizeplus.features.duty.occurrence.domain.validation

import com.joffer.organizeplus.common.utils.safeToDouble
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrenceForm
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrenceFormField

class DutyOccurrenceValidator {

    fun validate(form: DutyOccurrenceForm): Map<DutyOccurrenceFormField, ValidationError> {
        val errors = mutableMapOf<DutyOccurrenceFormField, ValidationError>()

        val amount = form.paidAmount?.safeToDouble()
        if (form.dutyType == DutyType.PAYABLE && amount != null && amount <= 0) {
            errors[DutyOccurrenceFormField.PaidAmount] = ValidationError.InvalidAmount
        }

        return errors
    }
}
