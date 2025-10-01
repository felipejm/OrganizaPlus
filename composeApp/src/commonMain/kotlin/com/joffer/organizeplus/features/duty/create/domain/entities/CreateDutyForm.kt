package com.joffer.organizeplus.features.duty.create.domain.entities

import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType

data class CreateDutyForm(
    val id: Long = 0L,
    val title: String = "",
    val dutyType: DutyType = DutyType.PAYABLE,
    val categoryName: String = ""
)
