package com.joffer.organizeplus.features.duty.data

import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDutyRepository : DutyRepository {

    private val duties = mutableListOf<Duty>()
    var getAllDutiesCallCount = 0
    var deleteDutyCallCount = 0
    var insertDutyCallCount = 0
    var lastDeletedDutyId: Long? = null

    override suspend fun getAllDuties(): Flow<Result<List<Duty>>> {
        getAllDutiesCallCount++
        return flowOf(Result.success(duties.toList()))
    }

    override suspend fun getUpcomingDuties(days: Int): Flow<Result<List<Duty>>> {
        return flowOf(Result.success(duties.toList()))
    }

    override suspend fun getLatestDuties(limit: Int): Flow<Result<List<Duty>>> {
        return flowOf(Result.success(duties.take(limit)))
    }

    override suspend fun getDutyById(id: Long): Flow<Result<Duty>> {
        val duty = duties.find { it.id == id }
        return if (duty != null) {
            flowOf(Result.success(duty))
        } else {
            flowOf(Result.failure(Exception("Duty not found")))
        }
    }

    override suspend fun insertDuty(duty: Duty): Flow<Result<Unit>> {
        insertDutyCallCount++
        duties.add(duty)
        return flowOf(Result.success(Unit))
    }

    override suspend fun updateDuty(duty: Duty): Flow<Result<Unit>> {
        val index = duties.indexOfFirst { it.id == duty.id }
        if (index != -1) {
            duties[index] = duty
            return flowOf(Result.success(Unit))
        }
        return flowOf(Result.failure(Exception("Duty not found")))
    }

    override suspend fun deleteDuty(id: Long): Flow<Result<Unit>> {
        deleteDutyCallCount++
        lastDeletedDutyId = id
        duties.removeAll { it.id == id }
        return flowOf(Result.success(Unit))
    }

    fun addDuty(duty: Duty) {
        duties.add(duty)
    }

    fun clear() {
        duties.clear()
        getAllDutiesCallCount = 0
        deleteDutyCallCount = 0
        insertDutyCallCount = 0
        lastDeletedDutyId = null
    }

    fun reset() = clear()
}

