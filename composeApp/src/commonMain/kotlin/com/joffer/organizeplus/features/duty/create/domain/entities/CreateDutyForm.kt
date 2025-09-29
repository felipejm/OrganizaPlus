package com.joffer.organizeplus.features.duty.create.domain.entities

import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime

// Default category constant - this should match the string resource value
const val DEFAULT_CATEGORY = "Pessoal"

data class CreateDutyForm(
    val id: String? = null,
    val title: String = "",
    val startDay: Int = 0,
    val dueDay: Int = 0,
    val dutyType: DutyType = DutyType.ACTIONABLE,
    val categoryName: String = DEFAULT_CATEGORY
) {
    fun isValid(): Boolean {
        return title.isNotBlank() && 
               startDay in 1..31 &&
               dueDay in 1..31 && 
               categoryName.isNotBlank()
    }
}
