package com.joffer.organizeplus.database.mappers

import com.joffer.organizeplus.database.entities.DutyEntity
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType

object DutyMapper {

    fun toDomainEntity(entity: DutyEntity): Duty {
        return Duty(
            id = entity.id,
            title = entity.title,
            startDay = entity.startDay,
            dueDay = entity.dueDay,
            type = when (entity.type) {
                "ACTIONABLE" -> DutyType.ACTIONABLE
                "PAYABLE" -> DutyType.PAYABLE
                else -> DutyType.ACTIONABLE
            },
            categoryName = entity.categoryName ?: "",
            createdAt = entity.createdAt
        )
    }

    fun toRoomEntity(domain: Duty): DutyEntity {
        return DutyEntity(
            id = domain.id,
            title = domain.title,
            description = null,
            type = domain.type.name,
            startDay = domain.startDay,
            dueDay = domain.dueDay,
            isCompleted = false,
            categoryName = domain.categoryName,
            createdAt = domain.createdAt
        )
    }
}
