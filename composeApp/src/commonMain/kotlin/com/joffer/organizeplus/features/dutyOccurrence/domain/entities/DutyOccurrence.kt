package com.joffer.organizeplus.features.dutyOccurrence.domain.entities

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

data class DutyOccurrence(
    val id: String,
    val dutyId: String,
    val paidAmount: Double? = null,
    val completedDate: LocalDate,
    val notes: String? = null,
    val createdAt: Instant,
    val updatedAt: Instant
)

