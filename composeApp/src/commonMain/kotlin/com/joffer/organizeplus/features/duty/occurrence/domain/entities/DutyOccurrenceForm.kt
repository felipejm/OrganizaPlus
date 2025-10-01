package com.joffer.organizeplus.features.duty.occurrence.domain.entities

import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class DutyOccurrenceForm(
    val id: String? = null,
    val dutyId: String = "",
    val dutyType: DutyType = DutyType.ACTIONABLE,
    val paidAmount: Double = 0.0,
    val paidAmountText: String = "",
    val completedDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
) {
    fun isValid(): Boolean {
        return dutyId.isNotBlank() && (dutyType != DutyType.PAYABLE || paidAmountText.isNotBlank())
    }

    fun toDutyOccurrence(): DutyOccurrence {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val parsedAmount = if (dutyType == DutyType.PAYABLE && paidAmountText.isNotBlank()) {
            paidAmountText.toDoubleOrNull() ?: 0.0
        } else {
            0.0
        }
        return DutyOccurrence(
            id = id ?: "",
            dutyId = dutyId,
            paidAmount = if (parsedAmount > 0) parsedAmount else null,
            completedDate = completedDate,
            createdAt = now
        )
    }
}
