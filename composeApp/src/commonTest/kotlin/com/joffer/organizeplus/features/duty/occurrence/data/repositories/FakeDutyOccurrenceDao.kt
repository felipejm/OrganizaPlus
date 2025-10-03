package com.joffer.organizeplus.features.duty.occurrence.data.repositories

import com.joffer.organizeplus.database.dao.DutyOccurrenceDao
import com.joffer.organizeplus.database.entities.DutyOccurrenceEntity
import com.joffer.organizeplus.database.entities.DutyOccurrenceWithDutyInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Fake implementation of DutyOccurrenceDao for testing purposes.
 */
class FakeDutyOccurrenceDao : DutyOccurrenceDao {
    private val occurrences = mutableListOf<DutyOccurrenceEntity>()
    private var nextId = 1L

    override fun getAllDutyOccurrences(): Flow<List<DutyOccurrenceEntity>> {
        return flowOf(occurrences.toList())
    }

    override suspend fun getDutyOccurrenceById(id: Long): DutyOccurrenceEntity? {
        return occurrences.find { it.id == id }
    }

    override fun getDutyOccurrencesByDutyId(dutyId: Long): Flow<List<DutyOccurrenceEntity>> {
        return flowOf(occurrences.filter { it.dutyId == dutyId })
    }

    override suspend fun getLastOccurrenceByDutyId(dutyId: Long): DutyOccurrenceEntity? {
        return occurrences.filter { it.dutyId == dutyId }
            .maxByOrNull { it.completedDateMillis }
    }

    override suspend fun insertDutyOccurrence(dutyOccurrence: DutyOccurrenceEntity) {
        val newOccurrence = dutyOccurrence.copy(id = if (dutyOccurrence.id == 0L) nextId++ else dutyOccurrence.id)
        occurrences.add(newOccurrence)
    }

    override suspend fun updateDutyOccurrence(dutyOccurrence: DutyOccurrenceEntity) {
        val index = occurrences.indexOfFirst { it.id == dutyOccurrence.id }
        if (index != -1) {
            occurrences[index] = dutyOccurrence
        }
    }

    override suspend fun deleteDutyOccurrence(dutyOccurrence: DutyOccurrenceEntity) {
        occurrences.removeAll { it.id == dutyOccurrence.id }
    }

    override suspend fun deleteDutyOccurrenceById(id: Long) {
        occurrences.removeAll { it.id == id }
    }

    override suspend fun deleteDutyOccurrencesByDutyId(dutyId: Long) {
        occurrences.removeAll { it.dutyId == dutyId }
    }

    override suspend fun getMonthlyOccurrencesByCategory(
        categoryName: String,
        monthStr: String,
        yearStr: String
    ): List<DutyOccurrenceEntity> {
        return occurrences.filter { occurrence ->
            // Simple filtering - in real implementation this would be more complex
            occurrence.paidAmount > 0
        }
    }

    override suspend fun getAllDutyOccurrencesWithDutyInfo(): List<DutyOccurrenceWithDutyInfo> {
        return occurrences.map { occurrence ->
            DutyOccurrenceWithDutyInfo(
                id = occurrence.id,
                dutyId = occurrence.dutyId,
                dutyTitle = "Duty ${occurrence.dutyId}",
                categoryName = "Personal",
                paidAmount = occurrence.paidAmount,
                completedDateMillis = occurrence.completedDateMillis
            )
        }
    }

    fun clearAll() {
        occurrences.clear()
        nextId = 1L
    }

    fun getOccurrenceCount(): Int = occurrences.size

    fun getAllOccurrencesSync(): List<DutyOccurrenceEntity> = occurrences.toList()
}
