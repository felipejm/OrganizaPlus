package com.joffer.organizeplus.features.duty.detail.domain.repositories

import com.joffer.organizeplus.features.duty.detail.domain.entities.DutyDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

interface DutyDetailsRepository {
    suspend fun getAllRecords(): Flow<Result<List<DutyDetails>>>
    suspend fun getRecordsByDutyId(dutyId: String): Flow<Result<List<DutyDetails>>>
    suspend fun getRecordById(id: String): Flow<Result<DutyDetails?>>
    suspend fun insertRecord(record: DutyDetails): Flow<Result<Unit>>
    suspend fun updateRecord(record: DutyDetails): Flow<Result<Unit>>
    suspend fun deleteRecord(id: String): Flow<Result<Unit>>
    suspend fun getRecordsByDateRange(startDate: Instant, endDate: Instant): Flow<Result<List<DutyDetails>>>
}
