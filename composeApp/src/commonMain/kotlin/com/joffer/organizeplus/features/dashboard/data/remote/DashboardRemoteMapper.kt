package com.joffer.organizeplus.features.dashboard.data.remote

import com.joffer.organizeplus.features.dashboard.MonthlySummary
import com.joffer.organizeplus.features.dashboard.domain.entities.DashboardData
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyWithLastOccurrence
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrence
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DashboardRemoteMapper {

    fun toDomain(remote: DashboardRemoteResponse): DashboardData {
        return DashboardData(
            upcomingDuties = remote.dashboard.personalDuties.map { toDomain(it.duty) } +
                remote.dashboard.companyDuties.map { toDomain(it.duty) }
        )
    }

    fun toDomain(remote: EnhancedDutyWithOccurrenceRemote): DutyWithLastOccurrence {
        return DutyWithLastOccurrence(
            duty = toDomain(remote.duty),
            lastOccurrence = remote.lastOccurrence?.let { toDomain(it) },
            hasCurrentMonthOccurrence = remote.hasCurrentMonthOccurrence
        )
    }

    fun toDomain(remote: EnhancedDutyRemote): Duty {
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

    fun toDomain(remote: EnhancedDutyOccurrenceRemote): DutyOccurrence {
        return DutyOccurrence(
            id = remote.id,
            dutyId = remote.dutyId,
            paidAmount = remote.amountPaid,
            completedDate = try {
                Instant.parse(remote.completedAt).toLocalDateTime(TimeZone.currentSystemDefault()).date
            } catch (e: Exception) {
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            },
            createdAt = try {
                Instant.parse(remote.completedAt).toLocalDateTime(TimeZone.currentSystemDefault()).date
            } catch (e: Exception) {
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            }
        )
    }

    fun toDomain(remote: EnhancedMonthlySummaryRemote): MonthlySummary {
        return MonthlySummary(
            totalAmountPaid = remote.totalAmountPaid,
            totalCompleted = remote.totalCompleted,
            totalTasks = remote.totalTasks,
            currentMonth = remote.currentMonth,
            year = remote.year
        )
    }
}
