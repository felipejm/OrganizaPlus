package com.joffer.organizeplus.features.dutyOccurrence.data.repositories

import com.joffer.organizeplus.database.dao.DutyOccurrenceDao
import com.joffer.organizeplus.database.mappers.toDomainEntity
import com.joffer.organizeplus.database.mappers.toRoomEntity
import com.joffer.organizeplus.features.dutyOccurrence.domain.entities.DutyOccurrence
import com.joffer.organizeplus.features.dutyOccurrence.domain.repositories.DutyOccurrenceRepository
import kotlinx.coroutines.flow.first

class RoomDutyOccurrenceRepository(
    private val dutyOccurrenceDao: DutyOccurrenceDao
) : DutyOccurrenceRepository {
    
    override suspend fun saveDutyOccurrence(dutyOccurrence: DutyOccurrence): Result<DutyOccurrence> {
        return try {
            val entity = dutyOccurrence.toRoomEntity()
            val id = dutyOccurrenceDao.insertDutyOccurrence(entity)
            val savedEntity = dutyOccurrence.copy(id = id.toString())
            Result.success(savedEntity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getDutyOccurrencesByDutyId(dutyId: String): Result<List<DutyOccurrence>> {
        return try {
            val entities = dutyOccurrenceDao.getDutyOccurrencesByDutyId(dutyId.toLongOrNull() ?: 0L).first()
            val dutyOccurrences = entities.map { it.toDomainEntity() }
            Result.success(dutyOccurrences)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun deleteDutyOccurrence(id: String): Result<Unit> {
        return try {
            dutyOccurrenceDao.deleteDutyOccurrenceById(id.toLongOrNull() ?: return Result.failure(IllegalArgumentException("Invalid ID")))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
