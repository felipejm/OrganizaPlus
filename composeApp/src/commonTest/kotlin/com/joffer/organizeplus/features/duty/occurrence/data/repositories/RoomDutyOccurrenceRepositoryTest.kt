package com.joffer.organizeplus.features.duty.occurrence.data.repositories

import com.joffer.organizeplus.database.dao.DutyOccurrenceDao
import com.joffer.organizeplus.database.entities.DutyOccurrenceEntity
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.duty.detail.domain.entities.ChartData
import com.joffer.organizeplus.features.duty.detail.domain.entities.MonthlyChartData
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrence
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RoomDutyOccurrenceRepositoryTest {

    @Test
    fun `saveDutyOccurrence should save and return duty occurrence with id`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        val repository = RoomDutyOccurrenceRepository(fakeDao)
        val dutyOccurrence = createDutyOccurrence("", "1", 100.0)

        // When
        val result = repository.saveDutyOccurrence(dutyOccurrence)

        // Then
        assertTrue(result.isSuccess)
        val savedOccurrence = result.getOrNull()
        assertEquals("1", savedOccurrence?.id)
        assertEquals("1", savedOccurrence?.dutyId)
        assertEquals(100.0, savedOccurrence?.paidAmount)
        assertEquals(1, fakeDao.occurrences.size)
    }

    @Test
    fun `saveDutyOccurrence should handle dao exception`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        fakeDao.shouldThrowException = true
        val repository = RoomDutyOccurrenceRepository(fakeDao)
        val dutyOccurrence = createDutyOccurrence("", "1", 100.0)

        // When
        val result = repository.saveDutyOccurrence(dutyOccurrence)

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `getDutyOccurrencesByDutyId should return occurrences for duty`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        val repository = RoomDutyOccurrenceRepository(fakeDao)
        val occurrences = listOf(
            createDutyOccurrenceEntity("1", "1", 100.0),
            createDutyOccurrenceEntity("2", "1", 200.0)
        )
        fakeDao.occurrences = occurrences.toMutableList()

        // When
        val result = repository.getDutyOccurrencesByDutyId("1")

        // Then
        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
    }

    @Test
    fun `getDutyOccurrencesByDutyId should handle invalid duty id`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        val repository = RoomDutyOccurrenceRepository(fakeDao)

        // When
        val result = repository.getDutyOccurrencesByDutyId("invalid")

        // Then
        assertTrue(result.isSuccess)
        assertEquals(emptyList<DutyOccurrence>(), result.getOrNull())
    }

    @Test
    fun `getDutyOccurrencesByDutyId should handle dao exception`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        fakeDao.shouldThrowException = true
        val repository = RoomDutyOccurrenceRepository(fakeDao)

        // When
        val result = repository.getDutyOccurrencesByDutyId("1")

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `getLastOccurrenceByDutyId should return last occurrence`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        val repository = RoomDutyOccurrenceRepository(fakeDao)
        val occurrences = listOf(
            createDutyOccurrenceEntity("1", "1", 100.0),
            createDutyOccurrenceEntity("2", "1", 200.0)
        )
        fakeDao.occurrences = occurrences.toMutableList()

        // When
        val result = repository.getLastOccurrenceByDutyId("1")

        // Then
        assertTrue(result.isSuccess)
        assertEquals("2", result.getOrNull()?.id)
        assertEquals(200.0, result.getOrNull()?.paidAmount)
    }

    @Test
    fun `getLastOccurrenceByDutyId should return null when no occurrences`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        val repository = RoomDutyOccurrenceRepository(fakeDao)

        // When
        val result = repository.getLastOccurrenceByDutyId("1")

        // Then
        assertTrue(result.isSuccess)
        assertEquals(null, result.getOrNull())
    }

    @Test
    fun `getLastOccurrenceByDutyId should handle dao exception`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        fakeDao.shouldThrowException = true
        val repository = RoomDutyOccurrenceRepository(fakeDao)

        // When
        val result = repository.getLastOccurrenceByDutyId("1")

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `getMonthlyChartData should return chart data for actionable duty`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        val repository = RoomDutyOccurrenceRepository(fakeDao)
        val occurrences = listOf(
            createDutyOccurrenceEntity("1", "1", 100.0, LocalDate(2024, 1, 15)),
            createDutyOccurrenceEntity("2", "1", 200.0, LocalDate(2024, 1, 20)),
            createDutyOccurrenceEntity("3", "1", 150.0, LocalDate(2024, 2, 10))
        )
        fakeDao.occurrences = occurrences.toMutableList()

        // When
        val result = repository.getMonthlyChartData("1", DutyType.ACTIONABLE)

        // Then
        assertTrue(result.isSuccess)
        val chartData = result.getOrNull()
        assertEquals(DutyType.ACTIONABLE, chartData?.dutyType)
        assertEquals(2, chartData?.monthlyData?.size)
        assertEquals(2, chartData?.monthlyData?.find { it.month == 1 }?.value) // Count of occurrences
        assertEquals(1, chartData?.monthlyData?.find { it.month == 2 }?.value) // Count of occurrences
    }

    @Test
    fun `getMonthlyChartData should return chart data for payable duty`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        val repository = RoomDutyOccurrenceRepository(fakeDao)
        val occurrences = listOf(
            createDutyOccurrenceEntity("1", "1", 100.0, LocalDate(2024, 1, 15)),
            createDutyOccurrenceEntity("2", "1", 200.0, LocalDate(2024, 1, 20)),
            createDutyOccurrenceEntity("3", "1", 150.0, LocalDate(2024, 2, 10))
        )
        fakeDao.occurrences = occurrences.toMutableList()

        // When
        val result = repository.getMonthlyChartData("1", DutyType.PAYABLE)

        // Then
        assertTrue(result.isSuccess)
        val chartData = result.getOrNull()
        assertEquals(DutyType.PAYABLE, chartData?.dutyType)
        assertEquals(2, chartData?.monthlyData?.size)
        assertEquals(300.0, chartData?.monthlyData?.find { it.month == 1 }?.value) // Sum of amounts
        assertEquals(150.0, chartData?.monthlyData?.find { it.month == 2 }?.value) // Sum of amounts
    }

    @Test
    fun `getMonthlyChartData should handle dao exception`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        fakeDao.shouldThrowException = true
        val repository = RoomDutyOccurrenceRepository(fakeDao)

        // When
        val result = repository.getMonthlyChartData("1", DutyType.ACTIONABLE)

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `deleteDutyOccurrence should remove occurrence`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        val repository = RoomDutyOccurrenceRepository(fakeDao)
        val occurrences = listOf(
            createDutyOccurrenceEntity("1", "1", 100.0),
            createDutyOccurrenceEntity("2", "1", 200.0)
        )
        fakeDao.occurrences = occurrences.toMutableList()

        // When
        val result = repository.deleteDutyOccurrence("1")

        // Then
        assertTrue(result.isSuccess)
        assertEquals(1, fakeDao.occurrences.size)
        assertFalse(fakeDao.occurrences.any { it.id == 1L })
    }

    @Test
    fun `deleteDutyOccurrence should handle invalid id`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        val repository = RoomDutyOccurrenceRepository(fakeDao)

        // When
        val result = repository.deleteDutyOccurrence("invalid")

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `deleteDutyOccurrence should handle dao exception`() = runTest {
        // Given
        val fakeDao = FakeDutyOccurrenceDao()
        fakeDao.shouldThrowException = true
        val repository = RoomDutyOccurrenceRepository(fakeDao)

        // When
        val result = repository.deleteDutyOccurrence("1")

        // Then
        assertTrue(result.isFailure)
    }

    private fun createDutyOccurrence(id: String, dutyId: String, paidAmount: Double?): DutyOccurrence {
        return DutyOccurrence(
            id = id,
            dutyId = dutyId,
            paidAmount = paidAmount,
            completedDate = LocalDate(2024, 1, 15),
            createdAt = LocalDate(2024, 1, 15)
        )
    }

    private fun createDutyOccurrenceEntity(
        id: String, 
        dutyId: String, 
        paidAmount: Double?, 
        completedDate: LocalDate = LocalDate(2024, 1, 15)
    ): DutyOccurrenceEntity {
        return DutyOccurrenceEntity(
            id = id.toLong(),
            dutyId = dutyId.toLong(),
            paidAmount = paidAmount,
            completedDate = completedDate,
            createdAt = LocalDate(2024, 1, 15)
        )
    }
}

// Fake implementation of DutyOccurrenceDao
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
