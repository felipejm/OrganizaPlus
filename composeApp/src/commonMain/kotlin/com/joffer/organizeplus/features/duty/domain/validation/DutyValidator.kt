package com.joffer.organizeplus.features.duty.domain.validation

import com.joffer.organizeplus.features.duty.domain.entities.DutyForm
import com.joffer.organizeplus.features.duty.domain.entities.DutyFormField
import com.joffer.organizeplus.features.duty.domain.entities.ValidationError

class DutyValidator {
    fun validate(form: DutyForm): Map<DutyFormField, ValidationError> {
        val errors = mutableMapOf<DutyFormField, ValidationError>()
        
        if (form.title.isBlank()) {
            errors[DutyFormField.Title] = ValidationError.EmptyTitle
        }
        
        if (form.startDate.isBlank()) {
            errors[DutyFormField.StartDate] = ValidationError.EmptyStartDate
        }
        
        if (form.dueDate.isBlank()) {
            errors[DutyFormField.DueDate] = ValidationError.EmptyDueDate
        }
        
        if (form.categoryName.isBlank()) {
            errors[DutyFormField.CategoryName] = ValidationError.EmptyCategory
        }
        
        if (form.hasStartDateReminder && 
            (form.startDateReminderDaysBefore < 1 || form.startDateReminderDaysBefore > 30)) {
            errors[DutyFormField.StartDateReminderDays] = ValidationError.InvalidReminderDays
        }
        
        if (form.hasDueDateReminder && 
            (form.dueDateReminderDaysBefore < 1 || form.dueDateReminderDaysBefore > 30)) {
            errors[DutyFormField.DueDateReminderDays] = ValidationError.InvalidReminderDays
        }
        
        return errors
    }
}

