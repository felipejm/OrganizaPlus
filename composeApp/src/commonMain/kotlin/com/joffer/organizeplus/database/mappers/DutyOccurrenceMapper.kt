package com.joffer.organizeplus.database.mappers

import com.joffer.organizeplus.database.entities.DutyOccurrenceEntity
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrence
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

fun DutyOccurrenceEntity.toDomainEntity(): DutyOccurrence {
    return DutyOccurrence(
        id = this.id,
        dutyId = this.dutyId,
        paidAmount = if (this.paidAmount < 0) null else this.paidAmount,
        completedDate = Instant.fromEpochMilliseconds(
            this.completedDateMillis
        ).toLocalDateTime(TimeZone.currentSystemDefault()).date,
        createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    )
}

fun DutyOccurrence.toRoomEntity(): DutyOccurrenceEntity {
    return DutyOccurrenceEntity(
        dutyId = this.dutyId,
        paidAmount = this.paidAmount ?: -1.0, // Use -1.0 to represent null
        completedDateMillis = this.completedDate.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    )
}
