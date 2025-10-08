package com.joffer.organizeplus.features.duty.data.remote

import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrence
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DutyRemoteMapper {

    fun toDomain(remote: DutyRemoteDto): Duty {
        return Duty(
            id = remote.id,
            title = remote.title,
            type = when (remote.type) {
                "PAYABLE" -> DutyType.PAYABLE
                "ACTIONABLE" -> DutyType.ACTIONABLE
                else -> DutyType.ACTIONABLE
            },
            categoryName = remote.categoryName,
            createdAt = try {
                Instant.parse(remote.createdAt)
            } catch (e: Exception) {
                Clock.System.now()
            }
        )
    }

    fun toDomain(remote: DutyOccurrenceRemoteDto): DutyOccurrence {
        val instant = try {
            Instant.parse(remote.completedAt)
        } catch (e: Exception) {
            Clock.System.now()
        }

        return DutyOccurrence(
            id = remote.id,
            dutyId = remote.dutyId,
            paidAmount = remote.amountPaid,
            completedDate = instant.toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).date,
            createdAt = instant.toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).date
        )
    }

    fun toRemote(duty: Duty): CreateDutyRequest {
        return CreateDutyRequest(
            title = duty.title,
            type = when (duty.type) {
                DutyType.PAYABLE -> "PAYABLE"
                DutyType.ACTIONABLE -> "ACTIONABLE"
            },
            categoryName = duty.categoryName
        )
    }
}
