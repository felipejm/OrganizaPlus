package com.joffer.organizeplus.features.dashboard.domain.repositories

import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import kotlinx.coroutines.flow.Flow

interface DutyRepository {

    suspend fun getAllDuties(): Flow<Result<List<Duty>>>
    suspend fun getDutyById(id: Long): Flow<Result<Duty?>>
    suspend fun insertDuty(duty: Duty): Flow<Result<Unit>>
    suspend fun updateDuty(duty: Duty): Flow<Result<Unit>>
    suspend fun deleteDuty(id: Long): Flow<Result<Unit>>
    suspend fun getUpcomingDuties(days: Int = 7): Flow<Result<List<Duty>>>
    suspend fun getLatestDuties(limit: Int = 3): Flow<Result<List<Duty>>>
}
