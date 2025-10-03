package com.joffer.organizeplus.features.dashboard.data.repositories

import com.joffer.organizeplus.database.dao.DutyDao
import com.joffer.organizeplus.database.entities.DutyEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Fake implementation of DutyDao for testing purposes.
 * This provides a real implementation that simulates database behavior
 * without requiring an actual database.
 */
class FakeDutyDao : DutyDao {
    private val duties = mutableListOf<DutyEntity>()
    private var nextId = 1L

    override fun getAllDuties(): Flow<List<DutyEntity>> = flowOf(duties.toList())
    
    override suspend fun getDutyById(id: Long): DutyEntity? = duties.find { it.id == id }
    
    override suspend fun insertDuty(duty: DutyEntity) {
        val newDuty = duty.copy(id = if (duty.id == 0L) nextId++ else duty.id)
        duties.add(newDuty)
    }
    
    override suspend fun updateDuty(duty: DutyEntity) {
        val index = duties.indexOfFirst { it.id == duty.id }
        if (index != -1) {
            duties[index] = duty
        }
    }
    
    override suspend fun deleteDuty(duty: DutyEntity) {
        duties.removeAll { it.id == duty.id }
    }
    
    override suspend fun deleteDutyById(id: Long) {
        duties.removeAll { it.id == id }
    }
    
    override fun getUpcomingDuties(): Flow<List<DutyEntity>> = flowOf(duties.toList())
    
    override fun getLatestDuties(limit: Int): Flow<List<DutyEntity>> = flowOf(
        duties.sortedWith(compareByDescending<DutyEntity> { it.createdAt }
            .thenByDescending { it.id }).take(limit)
    )

    fun clearAll() {
        duties.clear()
        nextId = 1L
    }

    fun getDutyCount(): Int = duties.size

    fun getAllDutiesSync(): List<DutyEntity> = duties.toList()
}
