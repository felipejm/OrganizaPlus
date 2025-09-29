package com.joffer.organizeplus.database.dao

import com.joffer.organizeplus.database.entities.DutyOccurrenceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ManualDutyOccurrenceDao : DutyOccurrenceDao {
    private val dutyOccurrences = mutableListOf<DutyOccurrenceEntity>()
    private var nextId = 1L
    
    override fun getAllDutyOccurrences(): Flow<List<DutyOccurrenceEntity>> {
        return flowOf(dutyOccurrences.toList())
    }
    
    override suspend fun getDutyOccurrenceById(id: Long): DutyOccurrenceEntity? {
        return dutyOccurrences.find { it.id == id }
    }
    
    override fun getDutyOccurrencesByDutyId(dutyId: Long): Flow<List<DutyOccurrenceEntity>> {
        return flowOf(dutyOccurrences.filter { it.dutyId == dutyId })
    }
    
    override fun getDutyOccurrencesByDateRange(startDate: String, endDate: String): Flow<List<DutyOccurrenceEntity>> {
        return flowOf(dutyOccurrences.filter { 
            it.completedDate.toString() >= startDate && it.completedDate.toString() <= endDate
        })
    }
    
    override suspend fun insertDutyOccurrence(dutyOccurrence: DutyOccurrenceEntity): Long {
        val newDutyOccurrence = dutyOccurrence.copy(id = nextId++)
        dutyOccurrences.add(newDutyOccurrence)
        return newDutyOccurrence.id
    }
    
    override suspend fun insertDutyOccurrences(dutyOccurrences: List<DutyOccurrenceEntity>): List<Long> {
        val ids = dutyOccurrences.map { dutyOccurrence ->
            val newDutyOccurrence = dutyOccurrence.copy(id = nextId++)
            this.dutyOccurrences.add(newDutyOccurrence)
            newDutyOccurrence.id
        }
        return ids
    }
    
    override suspend fun updateDutyOccurrence(dutyOccurrence: DutyOccurrenceEntity) {
        val index = dutyOccurrences.indexOfFirst { it.id == dutyOccurrence.id }
        if (index != -1) {
            dutyOccurrences[index] = dutyOccurrence
        }
    }
    
    override suspend fun deleteDutyOccurrence(dutyOccurrence: DutyOccurrenceEntity) {
        dutyOccurrences.removeAll { it.id == dutyOccurrence.id }
    }
    
    override suspend fun deleteDutyOccurrenceById(id: Long) {
        dutyOccurrences.removeAll { it.id == id }
    }
    
    override suspend fun deleteDutyOccurrencesByDutyId(dutyId: Long) {
        dutyOccurrences.removeAll { it.dutyId == dutyId }
    }
    
    override suspend fun deleteAllDutyOccurrences() {
        dutyOccurrences.clear()
    }
}
