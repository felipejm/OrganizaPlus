package com.joffer.organizeplus.database.mappers

import com.joffer.organizeplus.database.entities.DutyOccurrenceEntity
import com.joffer.organizeplus.features.dutyOccurrence.domain.entities.DutyOccurrence
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

object DutyOccurrenceMapper {
    
    fun toDomainEntity(entity: DutyOccurrenceEntity): DutyOccurrence {
        return DutyOccurrence(
            id = entity.id.toString(),
            dutyId = entity.dutyId.toString(),
            paidAmount = entity.paidAmount,
            completedDate = entity.completedDate,
            notes = entity.notes,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
    
    fun toRoomEntity(domain: DutyOccurrence): DutyOccurrenceEntity {
        return DutyOccurrenceEntity(
            id = domain.id.toLongOrNull() ?: 0L,
            dutyId = domain.dutyId.toLongOrNull() ?: 0L,
            paidAmount = domain.paidAmount,
            completedDate = domain.completedDate,
            notes = domain.notes,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
    }
}
