package com.joffer.organizeplus.features.duty.occurrence.domain.entities

import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class DutyOccurrenceForm(
    val id: Long = 0L,
    val dutyId: Long = 0L,
    val dutyType: DutyType = DutyType.ACTIONABLE,
    val paidAmount: String? = null,
    val completedDate: LocalDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date
) {
    fun toDutyOccurrence(): DutyOccurrence {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val amount = paidAmount?.toDoubleOrNull()

        return DutyOccurrence(
            id = id,
            dutyId = dutyId,
            paidAmount = if (dutyType == DutyType.PAYABLE && amount != null && amount > 0) {
                amount
            } else {
                null
            },
            completedDate = completedDate,
            createdAt = now
        )
    }
}
