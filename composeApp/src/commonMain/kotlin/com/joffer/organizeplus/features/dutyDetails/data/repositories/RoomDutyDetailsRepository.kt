package com.joffer.organizeplus.features.dutyDetails.data.repositories

import com.joffer.organizeplus.database.dao.DutyOccurrenceDao
import com.joffer.organizeplus.database.mappers.toDomainEntity
import com.joffer.organizeplus.database.mappers.toRoomEntity
import com.joffer.organizeplus.features.dutyOccurrence.domain.entities.DutyOccurrence
import com.joffer.organizeplus.features.dutyDetails.domain.entities.DutyDetails
import com.joffer.organizeplus.features.dutyDetails.domain.repositories.DutyDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.TimeZone

class RoomDutyDetailsRepository(
    private val dutyOccurrenceDao: DutyOccurrenceDao
) : DutyDetailsRepository {
    
    override suspend fun getAllRecords(): Flow<Result<List<DutyDetails>>> {
        return dutyOccurrenceDao.getAllDutyOccurrences().map { entities ->
            Result.success(entities.map { it.toDomainEntity().toDutyDetails() })
        }
    }
    
    override suspend fun getRecordsByDutyId(dutyId: String): Flow<Result<List<DutyDetails>>> {
        return dutyOccurrenceDao.getDutyOccurrencesByDutyId(dutyId.toLongOrNull() ?: 0L).map { entities ->
            Result.success(entities.map { it.toDomainEntity().toDutyDetails() })
        }
    }
    
    override suspend fun getRecordById(id: String): Flow<Result<DutyDetails?>> {
        val entity = dutyOccurrenceDao.getDutyOccurrenceById(id.toLongOrNull() ?: return flowOf(Result.success(null)))
        return flowOf(Result.success(entity?.let { it.toDomainEntity().toDutyDetails() }))
    }
    
    override suspend fun insertRecord(record: DutyDetails): Flow<Result<Unit>> {
        return try {
            val dutyOccurrence = record.toDutyOccurrence()
            val entity = dutyOccurrence.toRoomEntity()
            dutyOccurrenceDao.insertDutyOccurrence(entity)
            flowOf(Result.success(Unit))
        } catch (e: Exception) {
            flowOf(Result.failure(e))
        }
    }
    
    override suspend fun updateRecord(record: DutyDetails): Flow<Result<Unit>> {
        return try {
            val dutyOccurrence = record.toDutyOccurrence()
            val entity = dutyOccurrence.toRoomEntity()
            dutyOccurrenceDao.updateDutyOccurrence(entity)
            flowOf(Result.success(Unit))
        } catch (e: Exception) {
            flowOf(Result.failure(e))
        }
    }
    
    override suspend fun deleteRecord(id: String): Flow<Result<Unit>> {
        return try {
            dutyOccurrenceDao.deleteDutyOccurrenceById(id.toLongOrNull() ?: return flowOf(Result.failure(IllegalArgumentException("Invalid ID"))))
            flowOf(Result.success(Unit))
        } catch (e: Exception) {
            flowOf(Result.failure(e))
        }
    }
    
    override suspend fun getRecordsByDateRange(startDate: Instant, endDate: Instant): Flow<Result<List<DutyDetails>>> {
        // TODO: Implement date range query in DAO
        return flowOf(Result.success(emptyList()))
    }
}

// Extension functions to convert between DutyOccurrence and DutyDetails
private fun DutyOccurrence.toDutyDetails(): DutyDetails {
    return DutyDetails(
        id = id,
        dutyId = dutyId,
        paidAmount = paidAmount ?: 0.0,
        paidDate = kotlinx.datetime.Clock.System.now(), // Simplified for now
        notes = "", // No notes field anymore
        createdAt = kotlinx.datetime.Clock.System.now(), // Simplified for now
        updatedAt = kotlinx.datetime.Clock.System.now() // Use current time as updatedAt
    )
}

private fun DutyDetails.toDutyOccurrence(): DutyOccurrence {
    val now = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).date
    return DutyOccurrence(
        id = id,
        dutyId = dutyId,
        paidAmount = paidAmount,
        completedDate = paidDate.toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).date,
        createdAt = now
    )
}