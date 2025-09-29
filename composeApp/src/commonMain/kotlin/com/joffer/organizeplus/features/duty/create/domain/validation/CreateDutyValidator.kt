package com.joffer.organizeplus.features.createDuty.domain.validation

import com.joffer.organizeplus.features.createDuty.domain.entities.CreateDutyForm
import com.joffer.organizeplus.features.createDuty.domain.entities.CreateDutyFormField
import com.joffer.organizeplus.features.createDuty.domain.entities.CreateDutyValidationError

class CreateDutyValidator {
    fun validate(form: CreateDutyForm): Map<CreateDutyFormField, CreateDutyValidationError> {
        val errors = mutableMapOf<CreateDutyFormField, CreateDutyValidationError>()
        
        if (form.title.isBlank()) {
            errors[CreateDutyFormField.Title] = CreateDutyValidationError.EmptyTitle
        }
        
        if (form.startDate.isBlank()) {
            errors[CreateDutyFormField.StartDate] = CreateDutyValidationError.EmptyStartDate
        }
        
        if (form.dueDate.isBlank()) {
            errors[CreateDutyFormField.DueDate] = CreateDutyValidationError.EmptyDueDate
        }
        
        if (form.categoryName.isBlank()) {
            errors[CreateDutyFormField.CategoryName] = CreateDutyValidationError.EmptyCategory
        }
        
        if (form.hasStartDateReminder && 
            (form.startDateReminderDaysBefore < 1 || form.startDateReminderDaysBefore > 30)) {
            errors[CreateDutyFormField.StartDateReminderDays] = CreateDutyValidationError.InvalidReminderDays
        }
        
        if (form.hasDueDateReminder && 
            (form.dueDateReminderDaysBefore < 1 || form.dueDateReminderDaysBefore > 30)) {
            errors[CreateDutyFormField.DueDateReminderDays] = CreateDutyValidationError.InvalidReminderDays
        }
        
        return errors
    }
}

