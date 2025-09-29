package com.joffer.organizeplus.database.mappers

import com.joffer.organizeplus.database.entities.DutyOccurrenceEntity
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrence
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun DutyOccurrenceEntity.toDomainEntity(): DutyOccurrence {
    return DutyOccurrence(
        id = this.id.toString(),
        dutyId = this.dutyId.toString(),
        paidAmount = this.paidAmount,
        completedDate = Instant.fromEpochMilliseconds(this.completedDateMillis).toLocalDateTime(TimeZone.currentSystemDefault()).date,
        createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    )
}

fun DutyOccurrence.toRoomEntity(): DutyOccurrenceEntity {
    // Simplified conversion - use current time for now
    val now = Clock.System.now()
    return DutyOccurrenceEntity(
        id = this.id.toLongOrNull() ?: 0L,
        dutyId = this.dutyId.toLongOrNull() ?: 0L,
        paidAmount = this.paidAmount ?: 0.0,
        completedDateMillis = now.toEpochMilliseconds()
    )
}