package com.joffer.organizeplus.features.duty.create.domain.entities

import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType

data class CreateDutyForm(
    val id: String? = null,
    val title: String = "",
    val dutyType: DutyType = DutyType.PAYABLE,
    val categoryName: String = ""
) {
    fun isValid(): Boolean {
        return title.isNotBlank() && categoryName.isNotBlank()
    }
}
