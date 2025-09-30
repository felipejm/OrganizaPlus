package com.joffer.organizeplus.features.duty.detail.data.repositories

import com.joffer.organizeplus.database.dao.DutyOccurrenceDao
import com.joffer.organizeplus.database.entities.DutyOccurrenceEntity
import com.joffer.organizeplus.features.duty.detail.domain.entities.DutyDetails
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RoomDutyDetailsRepositoryTest {

    @Test
    fun `getAllRecords should return all duty details`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        val repository = RoomDutyDetailsRepository(fakeDao)
        val occurrences = listOf(
            createDutyOccurrenceEntity("1", "1", 100.0),
            createDutyOccurrenceEntity("2", "1", 200.0)
        )
        fakeDao.occurrences = occurrences.toMutableList()

        // When
        val result = repository.getAllRecords().first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
    }

    @Test
    fun `getRecordsByDutyId should return records for specific duty`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        val repository = RoomDutyDetailsRepository(fakeDao)
        val occurrences = listOf(
            createDutyOccurrenceEntity("1", "1", 100.0),
            createDutyOccurrenceEntity("2", "2", 200.0),
            createDutyOccurrenceEntity("3", "1", 300.0)
        )
        fakeDao.occurrences = occurrences.toMutableList()

        // When
        val result = repository.getRecordsByDutyId("1").first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
        assertTrue(result.getOrNull()?.all { it.dutyId == "1" } == true)
    }

    @Test
    fun `getRecordsByDutyId should handle invalid duty id`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        val repository = RoomDutyDetailsRepository(fakeDao)

        // When
        val result = repository.getRecordsByDutyId("invalid").first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(emptyList<DutyDetails>(), result.getOrNull())
    }

    @Test
    fun `getRecordById should return specific record`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        val repository = RoomDutyDetailsRepository(fakeDao)
        val occurrences = listOf(
            createDutyOccurrenceEntity("1", "1", 100.0),
            createDutyOccurrenceEntity("2", "1", 200.0)
        )
        fakeDao.occurrences = occurrences.toMutableList()

        // When
        val result = repository.getRecordById("1").first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals("1", result.getOrNull()?.id)
        assertEquals(100.0, result.getOrNull()?.paidAmount)
    }

    @Test
    fun `getRecordById should return null when not found`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        val repository = RoomDutyDetailsRepository(fakeDao)

        // When
        val result = repository.getRecordById("999").first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(null, result.getOrNull())
    }

    @Test
    fun `getRecordById should handle invalid id`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        val repository = RoomDutyDetailsRepository(fakeDao)

        // When
        val result = repository.getRecordById("invalid").first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(null, result.getOrNull())
    }

    @Test
    fun `insertRecord should add new record`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        val repository = RoomDutyDetailsRepository(fakeDao)
        val dutyDetails = createDutyDetails("1", "1", 100.0)

        // When
        val result = repository.insertRecord(dutyDetails).first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(1, fakeDao.occurrences.size)
    }

    @Test
    fun `insertRecord should handle dao exception`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        fakeDao.shouldThrowException = true
        val repository = RoomDutyDetailsRepository(fakeDao)
        val dutyDetails = createDutyDetails("1", "1", 100.0)

        // When
        val result = repository.insertRecord(dutyDetails).first()

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `updateRecord should update existing record`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        val repository = RoomDutyDetailsRepository(fakeDao)
        val existingOccurrence = createDutyOccurrenceEntity("1", "1", 100.0)
        fakeDao.occurrences = mutableListOf(existingOccurrence)
        val updatedDetails = createDutyDetails("1", "1", 200.0)

        // When
        val result = repository.updateRecord(updatedDetails).first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(200.0, fakeDao.occurrences.first().paidAmount)
    }

    @Test
    fun `updateRecord should handle dao exception`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        fakeDao.shouldThrowException = true
        val repository = RoomDutyDetailsRepository(fakeDao)
        val dutyDetails = createDutyDetails("1", "1", 200.0)

        // When
        val result = repository.updateRecord(dutyDetails).first()

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `deleteRecord should remove record`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        val repository = RoomDutyDetailsRepository(fakeDao)
        val occurrences = listOf(
            createDutyOccurrenceEntity("1", "1", 100.0),
            createDutyOccurrenceEntity("2", "1", 200.0)
        )
        fakeDao.occurrences = occurrences.toMutableList()

        // When
        val result = repository.deleteRecord("1").first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(1, fakeDao.occurrences.size)
        assertFalse(fakeDao.occurrences.any { it.id == 1L })
    }

    @Test
    fun `deleteRecord should handle invalid id`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        val repository = RoomDutyDetailsRepository(fakeDao)

        // When
        val result = repository.deleteRecord("invalid").first()

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `deleteRecord should handle dao exception`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        fakeDao.shouldThrowException = true
        val repository = RoomDutyDetailsRepository(fakeDao)

        // When
        val result = repository.deleteRecord("1").first()

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `getRecordsByDateRange should return empty list`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        val repository = RoomDutyDetailsRepository(fakeDao)
        val startDate = Clock.System.now()
        val endDate = Clock.System.now()

        // When
        val result = repository.getRecordsByDateRange(startDate, endDate).first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(emptyList<DutyDetails>(), result.getOrNull())
    }

    private fun createDutyDetails(id: String, dutyId: String, paidAmount: Double): DutyDetails {
        return DutyDetails(
            id = id,
            dutyId = dutyId,
            paidAmount = paidAmount,
            paidDate = Clock.System.now(),
            notes = "",
            createdAt = Clock.System.now(),
            updatedAt = Clock.System.now()
        )
    }

    private fun createDutyOccurrenceEntity(id: String, dutyId: String, paidAmount: Double?): DutyOccurrenceEntity {
        return DutyOccurrenceEntity(
            id = id.toLong(),
            dutyId = dutyId.toLong(),
            paidAmount = paidAmount,
            completedDate = LocalDate(2024, 1, 15),
            createdAt = LocalDate(2024, 1, 15)
        )
    }
}

// Fake implementation of DutyOccurrenceDao for this test
class FakeDutyOccurrenceDao : DutyOccurrenceDao {
    var occurrences = mutableListOf<DutyOccurrenceEntity>()
    var shouldThrowException = false

    override suspend fun getAllDutyOccurrences() = flowOf(occurrences.toList())

    override suspend fun getDutyOccurrencesByDutyId(dutyId: Long) = flowOf(
        occurrences.filter { it.dutyId == dutyId }
    )

    override suspend fun getDutyOccurrenceById(id: Long) = occurrences.find { it.id == id }

    override suspend fun getLastOccurrenceByDutyId(dutyId: Long) = 
        occurrences.filter { it.dutyId == dutyId }.maxByOrNull { it.completedDate }

    override suspend fun insertDutyOccurrence(dutyOccurrence: DutyOccurrenceEntity): Long {
        if (shouldThrowException) throw RuntimeException("Database error")
        val newId = (occurrences.maxOfOrNull { it.id } ?: 0L) + 1L
        val newOccurrence = dutyOccurrence.copy(id = newId)
        occurrences.add(newOccurrence)
        return newId
    }

    override suspend fun updateDutyOccurrence(dutyOccurrence: DutyOccurrenceEntity) {
        if (shouldThrowException) throw RuntimeException("Database error")
        val index = occurrences.indexOfFirst { it.id == dutyOccurrence.id }
        if (index != -1) {
            occurrences[index] = dutyOccurrence
        }
    }

    override suspend fun deleteDutyOccurrenceById(id: Long) {
        if (shouldThrowException) throw RuntimeException("Database error")
        occurrences.removeAll { it.id == id }
    }
}
