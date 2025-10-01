package com.joffer.organizeplus.features.duty.occurrence.domain.entities

import kotlinx.datetime.LocalDate

data class DutyOccurrence(
    val id: String,
    val dutyId: String,
    val paidAmount: Double? = null,
    val completedDate: LocalDate,
    val createdAt: LocalDate
)
