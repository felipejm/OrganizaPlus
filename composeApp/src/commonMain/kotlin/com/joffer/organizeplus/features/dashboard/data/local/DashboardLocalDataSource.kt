package com.joffer.organizeplus.features.dashboard.data.local

import com.joffer.organizeplus.database.dao.DutyDao
import com.joffer.organizeplus.database.dao.DutyOccurrenceDao
import com.joffer.organizeplus.database.entities.DutyEntity
import com.joffer.organizeplus.database.entities.DutyOccurrenceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface DashboardLocalDataSource {
    suspend fun getAllDutiesWithLastOccurrence(): Flow<Result<List<Pair<DutyEntity, DutyOccurrenceEntity?>>>>
}

class DashboardLocalDataSourceImpl(
    private val dutyDao: DutyDao,
    private val dutyOccurrenceDao: DutyOccurrenceDao
) : DashboardLocalDataSource {

    override suspend fun getAllDutiesWithLastOccurrence(): Flow<Result<List<Pair<DutyEntity, DutyOccurrenceEntity?>>>> {
        return try {
            dutyDao.getAllDuties().map { duties ->
                val dutiesWithOccurrences = duties.map { duty ->
                    val lastOccurrence = dutyOccurrenceDao.getLastOccurrenceByDutyId(duty.id)
                    Pair(duty, lastOccurrence)
                }
                Result.success(dutiesWithOccurrences)
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
