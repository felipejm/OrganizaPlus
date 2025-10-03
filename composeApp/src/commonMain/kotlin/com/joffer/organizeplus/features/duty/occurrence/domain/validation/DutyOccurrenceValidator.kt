package com.joffer.organizeplus.features.duty.occurrence.domain.validation

import com.joffer.organizeplus.common.utils.safeToDouble
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrenceForm
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrenceFormField

class DutyOccurrenceValidator {

    fun validate(form: DutyOccurrenceForm): Map<DutyOccurrenceFormField, ValidationError> {
        val errors = mutableMapOf<DutyOccurrenceFormField, ValidationError>()

        validatePaidAmount(form, errors)
        validateCompletedDate(form, errors)

        return errors
    }

    private fun validatePaidAmount(
        form: DutyOccurrenceForm,
        errors: MutableMap<DutyOccurrenceFormField, ValidationError>
    ) {
        if (form.dutyType == DutyType.PAYABLE) {
            val amount = form.paidAmount?.safeToDouble()
            when {
                form.paidAmount.isNullOrBlank() -> {
                    errors[DutyOccurrenceFormField.PaidAmount] = ValidationError.BlankField
                }
                amount == null || amount <= 0 -> {
                    errors[DutyOccurrenceFormField.PaidAmount] = ValidationError.InvalidAmount
                }
            }
        }
    }

    private fun validateCompletedDate(
        form: DutyOccurrenceForm,
        errors: MutableMap<DutyOccurrenceFormField, ValidationError>
    ) {
        // Suppress unused parameter warnings since these are required by interface
        @Suppress("UNUSED_PARAMETER")
        val unusedForm = form

        @Suppress("UNUSED_PARAMETER")
        val unusedErrors = errors
        // Date validation can be added here if needed
        // For now, we assume the date is always valid as it comes from a date picker
    }
}
