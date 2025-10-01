package com.joffer.organizeplus.features.dashboard.data.repositories

import com.joffer.organizeplus.database.dao.DutyDao
import com.joffer.organizeplus.database.mappers.DutyMapper
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant

class RoomDutyRepository(
    private val dutyDao: DutyDao
) : DutyRepository {

    override suspend fun getAllDuties(): Flow<Result<List<Duty>>> {
        return dutyDao.getAllDuties().map { entities ->
            Result.success(entities.map { DutyMapper.toDomainEntity(it) })
        }
    }

    override suspend fun getDutyById(id: String): Flow<Result<Duty?>> {
        val entity = dutyDao.getDutyById(id.toLongOrNull() ?: return flowOf(Result.success(null)))
        return flowOf(Result.success(entity?.let { DutyMapper.toDomainEntity(it) }))
    }

    override suspend fun insertDuty(duty: Duty): Flow<Result<Unit>> {
        return try {
            val entity = DutyMapper.toRoomEntity(duty)
            dutyDao.insertDuty(entity)
            flowOf(Result.success(Unit))
        } catch (e: Exception) {
            flowOf(Result.failure(e))
        }
    }

    override suspend fun updateDuty(duty: Duty): Flow<Result<Unit>> {
        return try {
            val entity = DutyMapper.toRoomEntity(duty)
            dutyDao.updateDuty(entity)
            flowOf(Result.success(Unit))
        } catch (e: Exception) {
            flowOf(Result.failure(e))
        }
    }

    override suspend fun deleteDuty(id: String): Flow<Result<Unit>> {
        return try {
            dutyDao.deleteDutyById(
                id.toLongOrNull() ?: return flowOf(Result.failure(IllegalArgumentException("Invalid ID")))
            )
            flowOf(Result.success(Unit))
        } catch (e: Exception) {
            flowOf(Result.failure(e))
        }
    }

    override suspend fun markDutyPaid(id: String, paidAt: Instant): Flow<Result<Unit>> {
        // Not yet implemented - mark paid functionality
        return flowOf(Result.success(Unit))
    }

    override suspend fun getUpcomingDuties(days: Int): Flow<Result<List<Duty>>> {
        return dutyDao.getAllDuties().map { entities ->
            Result.success(entities.map { DutyMapper.toDomainEntity(it) })
        }
    }

    override suspend fun getLatestDuties(limit: Int): Flow<Result<List<Duty>>> {
        return dutyDao.getAllDuties().map { entities ->
            Result.success(entities.take(limit).map { DutyMapper.toDomainEntity(it) })
        }
    }
}
