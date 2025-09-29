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
    val categoryName: String = ""
) {
    fun isValid(): Boolean {
        return title.isNotBlank() && 
               startDate.isNotBlank() &&
               dueDate.isNotBlank() && 
               categoryName.isNotBlank()
    }
}
