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
            type = DutyType.ACTIONABLE, // Default type, can be enhanced later
            categoryId = entity.category ?: "",
            status = if (entity.isCompleted) Duty.Status.PAID else Duty.Status.PENDING,
            priority = when (entity.priority) {
                "LOW" -> Duty.Priority.LOW
                "MEDIUM" -> Duty.Priority.MEDIUM
                "HIGH" -> Duty.Priority.HIGH
                "URGENT" -> Duty.Priority.URGENT
                else -> Duty.Priority.MEDIUM
            },
            snoozeUntil = null,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
    
    fun toRoomEntity(domain: Duty): DutyEntity {
        return DutyEntity(
            id = domain.id.toLongOrNull() ?: 0L,
            title = domain.title,
            description = null,
            dueDate = domain.dueDate,
            isCompleted = domain.status == Duty.Status.PAID,
            priority = domain.priority.name,
            category = domain.categoryId,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
    }
}
