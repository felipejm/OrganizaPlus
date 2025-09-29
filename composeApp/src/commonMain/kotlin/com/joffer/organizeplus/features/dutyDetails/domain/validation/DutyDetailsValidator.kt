package com.joffer.organizeplus.features.dutyDetails.domain.validation

import com.joffer.organizeplus.features.dutyDetails.domain.entities.DutyDetailsForm
import com.joffer.organizeplus.features.dutyDetails.domain.entities.DutyDetailsFormField
import com.joffer.organizeplus.features.dutyDetails.domain.validation.ValidationError

object DutyDetailsValidator {
    
    fun validate(form: DutyDetailsForm): Map<DutyDetailsFormField, ValidationError> {
        val errors = mutableMapOf<DutyDetailsFormField, ValidationError>()
        
        // Validate paid amount
        if (form.paidAmount <= 0) {
            errors[DutyDetailsFormField.PaidAmount] = ValidationError.InvalidAmount
        }
        
        return errors
    }
}
