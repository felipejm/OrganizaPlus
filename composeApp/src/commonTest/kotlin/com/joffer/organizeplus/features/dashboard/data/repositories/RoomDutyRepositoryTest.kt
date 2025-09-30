package com.joffer.organizeplus.features.dashboard.data.repositories

import com.joffer.organizeplus.database.dao.DutyDao
import com.joffer.organizeplus.database.entities.DutyEntity
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.dashboard.domain.entities.Status
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RoomDutyRepositoryTest {

    @Test
    fun `getAllDuties should return all duties from dao`() = runTest {
        // Given
        val fakeDao = FakeDutyDao()
        val repository = RoomDutyRepository(fakeDao)
        val expectedDuties = listOf(
            createDutyEntity("1", "Test Duty 1"),
            createDutyEntity("2", "Test Duty 2")
        )
        fakeDao.duties = expectedDuties

        // When
        val result = repository.getAllDuties().first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
        assertEquals("Test Duty 1", result.getOrNull()?.get(0)?.title)
        assertEquals("Test Duty 2", result.getOrNull()?.get(1)?.title)
    }

    @Test
    fun `getDutyById should return duty when found`() = runTest {
        // Given
        val fakeDao = FakeDutyDao()
        val repository = RoomDutyRepository(fakeDao)
        val expectedDuty = createDutyEntity("1", "Test Duty")
        fakeDao.duties = listOf(expectedDuty)

        // When
        val result = repository.getDutyById("1").first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals("Test Duty", result.getOrNull()?.title)
    }

    @Test
    fun `getDutyById should return null when not found`() = runTest {
        // Given
        val fakeDao = FakeDutyDao()
        val repository = RoomDutyRepository(fakeDao)
        fakeDao.duties = emptyList()

        // When
        val result = repository.getDutyById("999").first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(null, result.getOrNull())
    }

    @Test
    fun `getDutyById should return null for invalid id`() = runTest {
        // Given
        val fakeDao = FakeDutyDao()
        val repository = RoomDutyRepository(fakeDao)

        // When
        val result = repository.getDutyById("invalid").first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(null, result.getOrNull())
    }

    @Test
    fun `insertDuty should add duty to dao`() = runTest {
        // Given
        val fakeDao = FakeDutyDao()
        val repository = RoomDutyRepository(fakeDao)
        val duty = createDuty("1", "New Duty")

        // When
        val result = repository.insertDuty(duty).first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(1, fakeDao.duties.size)
        assertEquals("New Duty", fakeDao.duties.first().title)
    }

    @Test
    fun `insertDuty should handle dao exception`() = runTest {
        // Given
        val fakeDao = FakeDutyDao()
        fakeDao.shouldThrowException = true
        val repository = RoomDutyRepository(fakeDao)
        val duty = createDuty("1", "New Duty")

        // When
        val result = repository.insertDuty(duty).first()

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `updateDuty should update duty in dao`() = runTest {
        // Given
        val fakeDao = FakeDutyDao()
        val repository = RoomDutyRepository(fakeDao)
        val existingDuty = createDutyEntity("1", "Original Title")
        fakeDao.duties = listOf(existingDuty)
        val updatedDuty = createDuty("1", "Updated Title")

        // When
        val result = repository.updateDuty(updatedDuty).first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals("Updated Title", fakeDao.duties.first().title)
    }

    @Test
    fun `updateDuty should handle dao exception`() = runTest {
        // Given
        val fakeDao = FakeDutyDao()
        fakeDao.shouldThrowException = true
        val repository = RoomDutyRepository(fakeDao)
        val duty = createDuty("1", "Updated Duty")

        // When
        val result = repository.updateDuty(duty).first()

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `deleteDuty should remove duty from dao`() = runTest {
        // Given
        val fakeDao = FakeDutyDao()
        val repository = RoomDutyRepository(fakeDao)
        val existingDuty = createDutyEntity("1", "To Delete")
        fakeDao.duties = listOf(existingDuty)

        // When
        val result = repository.deleteDuty("1").first()

        // Then
        assertTrue(result.isSuccess)
        assertTrue(fakeDao.duties.isEmpty())
    }

    @Test
    fun `deleteDuty should handle invalid id`() = runTest {
        // Given
        val fakeDao = FakeDutyDao()
        val repository = RoomDutyRepository(fakeDao)

        // When
        val result = repository.deleteDuty("invalid").first()

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `deleteDuty should handle dao exception`() = runTest {
        // Given
        val fakeDao = FakeDutyDao()
        fakeDao.shouldThrowException = true
        val repository = RoomDutyRepository(fakeDao)

        // When
        val result = repository.deleteDuty("1").first()

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `markDutyPaid should return success`() = runTest {
        // Given
        val fakeDao = FakeDutyDao()
        val repository = RoomDutyRepository(fakeDao)
        val now = Clock.System.now()

        // When
        val result = repository.markDutyPaid("1", now).first()

        // Then
        assertTrue(result.isSuccess)
    }

    @Test
    fun `getUpcomingDuties should return all duties`() = runTest {
        // Given
        val fakeDao = FakeDutyDao()
        val repository = RoomDutyRepository(fakeDao)
        val expectedDuties = listOf(
            createDutyEntity("1", "Upcoming 1"),
            createDutyEntity("2", "Upcoming 2")
        )
        fakeDao.duties = expectedDuties

        // When
        val result = repository.getUpcomingDuties(7).first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
    }

    @Test
    fun `getLatestDuties should return limited duties`() = runTest {
        // Given
        val fakeDao = FakeDutyDao()
        val repository = RoomDutyRepository(fakeDao)
        val expectedDuties = listOf(
            createDutyEntity("1", "Latest 1"),
            createDutyEntity("2", "Latest 2"),
            createDutyEntity("3", "Latest 3"),
            createDutyEntity("4", "Latest 4")
        )
        fakeDao.duties = expectedDuties

        // When
        val result = repository.getLatestDuties(2).first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
    }

    private fun createDutyEntity(id: String, title: String): DutyEntity {
        return DutyEntity(
            id = id.toLong(),
            title = title,
            startDay = 1,
            dueDay = 15,
            type = DutyType.ACTIONABLE.name,
            categoryName = "Personal",
            status = Status.PENDING.name,
            snoozeUntil = null,
            createdAt = Clock.System.now()
        )
    }

    private fun createDuty(id: String, title: String): Duty {
        return Duty(
            id = id,
            title = title,
            startDay = 1,
            dueDay = 15,
            type = DutyType.ACTIONABLE,
            categoryName = "Personal",
            status = Status.PENDING,
            snoozeUntil = null,
            createdAt = Clock.System.now()
        )
    }
}

// Fake implementation of DutyDao
class FakeDutyDao : DutyDao {
    var duties = mutableListOf<DutyEntity>()
    var shouldThrowException = false

    override suspend fun getAllDuties() = flowOf(duties.toList())

    override suspend fun getDutyById(id: Long) = duties.find { it.id == id }

    override suspend fun insertDuty(duty: DutyEntity) {
        if (shouldThrowException) throw RuntimeException("Database error")
        duties.add(duty)
    }

    override suspend fun updateDuty(duty: DutyEntity) {
        if (shouldThrowException) throw RuntimeException("Database error")
        val index = duties.indexOfFirst { it.id == duty.id }
        if (index != -1) {
            duties[index] = duty
        }
    }

    override suspend fun deleteDutyById(id: Long) {
        if (shouldThrowException) throw RuntimeException("Database error")
        duties.removeAll { it.id == id }
    }
}
