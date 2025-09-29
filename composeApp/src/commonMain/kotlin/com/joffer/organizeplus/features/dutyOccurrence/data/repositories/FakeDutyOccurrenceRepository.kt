package com.joffer.organizeplus.features.dutyOccurrence.data.repositories

import com.joffer.organizeplus.features.dutyOccurrence.domain.entities.DutyOccurrence
import com.joffer.organizeplus.features.dutyOccurrence.domain.repositories.DutyOccurrenceRepository
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock

class FakeDutyOccurrenceRepository : DutyOccurrenceRepository {
    
    private val dutyOccurrences = mutableListOf<DutyOccurrence>()
    
    override suspend fun saveDutyOccurrence(dutyOccurrence: DutyOccurrence): Result<DutyOccurrence> {
        return try {
            delay(1000) // Simulate network delay
            
            val savedOccurrence = dutyOccurrence.copy(
                id = kotlin.random.Random.nextLong().toString(),
                createdAt = Clock.System.now(),
                updatedAt = Clock.System.now()
            )
            
            dutyOccurrences.add(savedOccurrence)
            Result.success(savedOccurrence)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getDutyOccurrencesByDutyId(dutyId: String): Result<List<DutyOccurrence>> {
        return try {
            delay(500) // Simulate network delay
            val occurrences = dutyOccurrences.filter { it.dutyId == dutyId }
            Result.success(occurrences)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun deleteDutyOccurrence(id: String): Result<Unit> {
        return try {
            delay(500) // Simulate network delay
            dutyOccurrences.removeAll { it.id == id }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
