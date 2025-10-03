package com.joffer.organizeplus.features.dashboard.data.repositories

import com.joffer.organizeplus.database.entities.DutyEntity
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.datetime.Instant

class RoomDutyRepositoryTest {

    private val fakeDutyDao = FakeDutyDao()
    private val repository = RoomDutyRepository(fakeDutyDao)

    @BeforeTest
    fun setUp() {
        fakeDutyDao.clearAll()
    }

    @Test
    fun `test retrieve all duties`() = runTest {
        val now = Clock.System.now()
        fakeDutyDao.insertDuty(dutyEntity(1L, "Buy groceries", "Personal", now))
        fakeDutyDao.insertDuty(dutyEntity(2L, "Fix bug", "Work", now))

        val result = repository.getAllDuties().first()

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()!!.size)
    }

    @Test
    fun `test find duty by id`() = runTest {
        val now = Clock.System.now()
        fakeDutyDao.insertDuty(dutyEntity(1L, "Walk the dog", "Personal", now))

        val result = repository.getDutyById(1L).first()

        assertTrue(result.isSuccess)
        assertEquals("Walk the dog", result.getOrNull()!!.title)
    }

    @Test
    fun `test add new duty`() = runTest {
        val now = Clock.System.now()
        val newDuty = Duty(
            id = 0L,
            title = "Learn Kotlin",
            type = DutyType.ACTIONABLE,
            categoryName = "Learning",
            createdAt = now
        )

        val result = repository.insertDuty(newDuty).first()

        assertTrue(result.isSuccess)
        assertEquals(1, fakeDutyDao.getDutyCount())
    }

    @Test
    fun `test update existing duty`() = runTest {
        val now = Clock.System.now()
        fakeDutyDao.insertDuty(dutyEntity(1L, "Old title", "Personal", now))
        
        val updatedDuty = Duty(
            id = 1L,
            title = "New title",
            type = DutyType.ACTIONABLE,
            categoryName = "Work",
            createdAt = now
        )

        repository.updateDuty(updatedDuty).first()
        val retrieved = repository.getDutyById(1L).first()

        assertEquals("New title", retrieved.getOrNull()!!.title)
    }

    @Test
    fun `test delete duty`() = runTest {
        val now = Clock.System.now()
        fakeDutyDao.insertDuty(dutyEntity(1L, "Delete me", "Personal", now))

        val result = repository.deleteDuty(1L).first()

        assertTrue(result.isSuccess)
        assertEquals(0, fakeDutyDao.getDutyCount())
    }

    @Test
    fun `test get latest duties with limit`() = runTest {
        val now = Clock.System.now()
        fakeDutyDao.insertDuty(dutyEntity(1L, "First", "Personal", now))
        fakeDutyDao.insertDuty(dutyEntity(2L, "Second", "Work", now))
        fakeDutyDao.insertDuty(dutyEntity(3L, "Third", "Personal", now))

        val result = repository.getLatestDuties(2).first()

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()!!.size)
    }

    private fun dutyEntity(
        id: Long,
        title: String,
        categoryName: String,
        createdAt: Instant
    ) = DutyEntity(
        id = id,
        title = title,
        description = null,
        type = "PAYABLE",
        isCompleted = false,
        categoryName = categoryName,
        createdAt = createdAt
    )
}
