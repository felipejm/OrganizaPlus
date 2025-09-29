package com.joffer.organizeplus.features.duty.create.domain.entities

import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime

data class CreateDutyForm(
    val id: String? = null,
    val title: String = "",
    val startDate: String = "",
    val dueDate: String = "",
    val dutyType: DutyType = DutyType.ACTIONABLE,
    val categoryName: String = "",
    val hasStartDateReminder: Boolean = false,
    val startDateReminderDaysBefore: Int = 3,
    val startDateReminderTime: String = "09:00",
    val hasDueDateReminder: Boolean = false,
    val dueDateReminderDaysBefore: Int = 3,
    val dueDateReminderTime: String = "09:00"
) {
    fun isValid(): Boolean {
        return title.isNotBlank() && 
               startDate.isNotBlank() &&
               dueDate.isNotBlank() && 
               categoryName.isNotBlank()
    }
}
