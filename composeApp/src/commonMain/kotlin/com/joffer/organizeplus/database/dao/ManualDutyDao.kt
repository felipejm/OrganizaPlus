package com.joffer.organizeplus.database.dao

import com.joffer.organizeplus.database.entities.DutyEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ManualDutyDao : DutyDao {
    private val duties = mutableListOf<DutyEntity>()
    private var nextId = 1L
    
    override fun getAllDuties(): Flow<List<DutyEntity>> {
        return flowOf(duties.toList())
    }
    
    override suspend fun getDutyById(id: Long): DutyEntity? {
        return duties.find { it.id == id }
    }
    
    override fun getDutiesByCompletionStatus(isCompleted: Boolean): Flow<List<DutyEntity>> {
        return flowOf(duties.filter { it.isCompleted == isCompleted })
    }
    
    override fun getDutiesByDateRange(startDate: Long, endDate: Long): Flow<List<DutyEntity>> {
        return flowOf(duties.filter { 
            it.dueDate?.toEpochMilliseconds()?.let { dueTime ->
                dueTime in startDate..endDate
            } ?: false
        })
    }
    
    override suspend fun insertDuty(duty: DutyEntity): Long {
        val newDuty = duty.copy(id = nextId++)
        duties.add(newDuty)
        return newDuty.id
    }
    
    override suspend fun insertDuties(duties: List<DutyEntity>): List<Long> {
        val ids = duties.map { duty ->
            val newDuty = duty.copy(id = nextId++)
            this.duties.add(newDuty)
            newDuty.id
        }
        return ids
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
    
    override suspend fun deleteAllDuties() {
        duties.clear()
    }
}
