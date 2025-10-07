package com.joffer.organizeplus.features.dashboard.data.local

import com.joffer.organizeplus.database.entities.DutyEntity
import com.joffer.organizeplus.database.entities.DutyOccurrenceEntity
import com.joffer.organizeplus.database.mappers.DutyMapper
import com.joffer.organizeplus.database.mappers.toDomainEntity
import com.joffer.organizeplus.features.dashboard.domain.entities.DashboardData
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyWithLastOccurrence
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DashboardLocalMapper {

    fun toDomain(dutiesWithOccurrences: List<Pair<DutyEntity, DutyOccurrenceEntity?>>): DashboardData {
        val upcomingDuties = dutiesWithOccurrences.map { (dutyEntity, _) ->
            DutyMapper.toDomainEntity(dutyEntity)
        }

        return DashboardData(upcomingDuties = upcomingDuties)
    }

    fun toDutyWithLastOccurrence(
        dutyEntity: DutyEntity,
        occurrenceEntity: DutyOccurrenceEntity?
    ): DutyWithLastOccurrence {
        val duty = DutyMapper.toDomainEntity(dutyEntity)
        val lastOccurrence = occurrenceEntity?.toDomainEntity()

        // Check if there's an occurrence in the current month
        val hasCurrentMonthOccurrence = if (occurrenceEntity != null) {
            val occurrenceDate = Instant.fromEpochMilliseconds(occurrenceEntity.completedDateMillis)
                .toLocalDateTime(TimeZone.currentSystemDefault())
            val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            occurrenceDate.year == currentDate.year && occurrenceDate.month == currentDate.month
        } else {
            false
        }

        return DutyWithLastOccurrence(
            duty = duty,
            lastOccurrence = lastOccurrence,
            hasCurrentMonthOccurrence = hasCurrentMonthOccurrence
        )
    }

    fun toDutiesWithLastOccurrence(
        dutiesWithOccurrences: List<Pair<DutyEntity, DutyOccurrenceEntity?>>
    ): List<DutyWithLastOccurrence> {
        return dutiesWithOccurrences.map { (dutyEntity, occurrenceEntity) ->
            toDutyWithLastOccurrence(dutyEntity, occurrenceEntity)
        }
    }
}
