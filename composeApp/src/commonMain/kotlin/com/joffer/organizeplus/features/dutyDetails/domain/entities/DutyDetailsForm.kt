package com.joffer.organizeplus.features.dutyDetails.domain.entities

import kotlinx.datetime.Instant

data class DutyDetailsForm(
    val id: String? = null,
    val dutyId: String = "",
    val paidAmount: Double = 0.0,
    val paidDate: Instant = kotlinx.datetime.Clock.System.now(),
    val notes: String = ""
) {
    fun isValid(): Boolean {
        return dutyId.isNotBlank() && paidAmount > 0
    }
}
