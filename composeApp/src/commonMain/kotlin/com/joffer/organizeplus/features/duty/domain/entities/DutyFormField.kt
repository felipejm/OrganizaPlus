package com.joffer.organizeplus.features.duty.domain.entities

sealed class DutyFormField {
    object Title : DutyFormField()
    object StartDate : DutyFormField()
    object DueDate : DutyFormField()
    object DutyType : DutyFormField()
    object CategoryName : DutyFormField()
    object HasStartDateReminder : DutyFormField()
    object StartDateReminderDays : DutyFormField()
    object StartDateReminderTime : DutyFormField()
    object HasDueDateReminder : DutyFormField()
    object DueDateReminderDays : DutyFormField()
    object DueDateReminderTime : DutyFormField()
}
