package com.joffer.organizeplus.database.mappers

import com.joffer.organizeplus.database.entities.DutyEntity
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType

object DutyMapper {
    
    fun toDomainEntity(entity: DutyEntity): Duty {
        return Duty(
            id = entity.id.toString(),
            title = entity.title,
            startDate = entity.createdAt, // Using createdAt as startDate
            dueDate = entity.dueDate ?: entity.createdAt,
            type = when (entity.type) {
                "ACTIONABLE" -> DutyType.ACTIONABLE
                "PAYABLE" -> DutyType.PAYABLE
                else -> DutyType.ACTIONABLE
            },
            categoryName = entity.categoryName ?: "",
            status = if (entity.isCompleted) Duty.Status.PAID else Duty.Status.PENDING,
            snoozeUntil = null,
            createdAt = entity.createdAt
        )
    }
    
    fun toRoomEntity(domain: Duty): DutyEntity {
        return DutyEntity(
            id = domain.id.toLongOrNull() ?: 0L,
            title = domain.title,
            description = null,
            type = domain.type.name,
            dueDate = domain.dueDate,
            isCompleted = domain.status == Duty.Status.PAID,
            categoryName = domain.categoryName,
            createdAt = domain.createdAt
        )
    }
}
