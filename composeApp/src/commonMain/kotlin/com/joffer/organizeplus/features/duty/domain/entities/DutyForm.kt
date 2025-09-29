package com.joffer.organizeplus.features.duty.domain.entities

import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime

data class DutyForm(
    val id: String? = null,
    val title: String = "",
    val startDate: String = "",
    val dueDate: String = "",
    val dutyType: DutyType = DutyType.ACTIONABLE,
    val categoryId: String = "",
    val hasStartDateReminder: Boolean = false,
    val startDateReminderDaysBefore: Int = 3,
    val startDateReminderTime: String = "09:00",
    val hasDueDateReminder: Boolean = false,
    val dueDateReminderDaysBefore: Int = 3,
    val dueDateReminderTime: String = "09:00",
    val priority: Priority = Priority.MEDIUM
) {
    enum class Priority {
        LOW, MEDIUM, HIGH, URGENT
    }

    fun isValid(): Boolean {
        return title.isNotBlank() && 
               startDate.isNotBlank() &&
               dueDate.isNotBlank() && 
               categoryId.isNotBlank()
    }
}
