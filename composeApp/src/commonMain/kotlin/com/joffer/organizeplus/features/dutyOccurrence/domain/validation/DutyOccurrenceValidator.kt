package com.joffer.organizeplus.features.dutyOccurrence.domain.validation

import com.joffer.organizeplus.features.dutyOccurrence.domain.entities.DutyOccurrenceForm
import com.joffer.organizeplus.features.dutyOccurrence.domain.entities.DutyOccurrenceFormField

class DutyOccurrenceValidator {
    
    fun validate(form: DutyOccurrenceForm): Map<DutyOccurrenceFormField, ValidationError> {
        val errors = mutableMapOf<DutyOccurrenceFormField, ValidationError>()
        
        if (form.paidAmount <= 0) {
            errors[DutyOccurrenceFormField.PaidAmount] = ValidationError.InvalidAmount
        }
        
        if (form.dutyId.isBlank()) {
            errors[DutyOccurrenceFormField.PaidAmount] = ValidationError.BlankField
        }
        
        return errors
    }
}
