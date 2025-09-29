package com.joffer.organizeplus.features.dashboard.domain.repositories

import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

interface DutyRepository {
    
    // Dutys
    suspend fun getAllDuties(): Flow<Result<List<Duty>>>
    suspend fun getDutyById(id: String): Flow<Result<Duty?>>
    suspend fun insertDuty(duty: Duty): Flow<Result<Unit>>
    suspend fun updateDuty(duty: Duty): Flow<Result<Unit>>
    suspend fun deleteDuty(id: String): Flow<Result<Unit>>
    suspend fun markDutyPaid(id: String, paidAt: Instant): Flow<Result<Unit>>
    suspend fun getUpcomingDuties(days: Int = 7): Flow<Result<List<Duty>>>
    suspend fun getLatestDuties(limit: Int = 3): Flow<Result<List<Duty>>>
}
