package com.joffer.organizeplus.features.dutyOccurrence.domain.entities

import kotlinx.datetime.LocalDate
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class DutyOccurrenceForm(
    val id: String? = null,
    val dutyId: String = "",
    val paidAmount: Double = 0.0,
    val completedDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
) {
    fun isValid(): Boolean {
        return dutyId.isNotBlank() && paidAmount > 0
    }
    
    fun toDutyOccurrence(): DutyOccurrence {
        val now = Clock.System.now()
        return DutyOccurrence(
            id = id ?: "",
            dutyId = dutyId,
            paidAmount = paidAmount,
            completedDate = completedDate,
            notes = null,
            createdAt = now,
            updatedAt = now
        )
    }
}
