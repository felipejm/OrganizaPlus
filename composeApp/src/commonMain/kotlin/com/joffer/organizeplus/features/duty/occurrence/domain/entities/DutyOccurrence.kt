package com.joffer.organizeplus.features.duty.occurrence.domain.entities

import kotlinx.datetime.LocalDate

data class DutyOccurrence(
    val id: Long,
    val dutyId: Long,
    val paidAmount: Double? = null,
    val completedDate: LocalDate,
    val createdAt: LocalDate
)
