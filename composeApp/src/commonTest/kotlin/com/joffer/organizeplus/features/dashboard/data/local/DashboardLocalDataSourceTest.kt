package com.joffer.organizeplus.features.dashboard.data.local

import com.joffer.organizeplus.database.entities.DutyEntity
import com.joffer.organizeplus.database.entities.DutyOccurrenceEntity
import com.joffer.organizeplus.features.dashboard.data.repositories.FakeDutyDao
import com.joffer.organizeplus.features.duty.occurrence.data.repositories.FakeDutyOccurrenceDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DashboardLocalDataSourceTest {

    private lateinit var fakeDutyDao: FakeDutyDao
    private lateinit var fakeDutyOccurrenceDao: FakeDutyOccurrenceDao
    private lateinit var dataSource: DashboardLocalDataSource

    @BeforeTest
    fun setUp() {
        fakeDutyDao = FakeDutyDao()
        fakeDutyOccurrenceDao = FakeDutyOccurrenceDao()
        dataSource = DashboardLocalDataSourceImpl(fakeDutyDao, fakeDutyOccurrenceDao)
    }

    @Test
    fun `getAllDutiesWithLastOccurrence should return empty list when no duties exist`() = runTest {
        // When
        val result = dataSource.getAllDutiesWithLastOccurrence().first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(0, result.getOrNull()?.size)
    }

    @Test
    fun `getAllDutiesWithLastOccurrence should return duties without occurrences`() = runTest {
        // Given
        val duty1 = createDutyEntity(1L, "Duty 1", "Personal")
        val duty2 = createDutyEntity(2L, "Duty 2", "Company")
        fakeDutyDao.insertDuty(duty1)
        fakeDutyDao.insertDuty(duty2)

        // When
        val result = dataSource.getAllDutiesWithLastOccurrence().first()

        // Then
        assertTrue(result.isSuccess)
        val duties = result.getOrNull()!!
        assertEquals(2, duties.size)
        assertNull(duties[0].second) // No occurrence
        assertNull(duties[1].second) // No occurrence
    }

    @Test
    fun `getAllDutiesWithLastOccurrence should return duties with their last occurrences`() = runTest {
        // Given
        val duty1 = createDutyEntity(1L, "Duty 1", "Personal")
        val duty2 = createDutyEntity(2L, "Duty 2", "Company")
        fakeDutyDao.insertDuty(duty1)
        fakeDutyDao.insertDuty(duty2)

        val occurrence1 = createOccurrenceEntity(1L, duty1.id, 100.0)
        val occurrence2 = createOccurrenceEntity(2L, duty2.id, 200.0)
        fakeDutyOccurrenceDao.insertDutyOccurrence(occurrence1)
        fakeDutyOccurrenceDao.insertDutyOccurrence(occurrence2)

        // When
        val result = dataSource.getAllDutiesWithLastOccurrence().first()

        // Then
        assertTrue(result.isSuccess)
        val duties = result.getOrNull()!!
        assertEquals(2, duties.size)
        assertEquals(1L, duties[0].first.id)
        assertEquals(100.0, duties[0].second?.paidAmount)
        assertEquals(2L, duties[1].first.id)
        assertEquals(200.0, duties[1].second?.paidAmount)
    }

    @Test
    fun `getAllDutiesWithLastOccurrence should return only the last occurrence per duty`() = runTest {
        // Given
        val duty = createDutyEntity(1L, "Duty with multiple occurrences", "Personal")
        fakeDutyDao.insertDuty(duty)

        // Insert multiple occurrences (FakeDutyOccurrenceDao returns the latest one)
        val oldOccurrence = createOccurrenceEntity(1L, duty.id, 50.0, completedAt = 1000L)
        val latestOccurrence = createOccurrenceEntity(2L, duty.id, 100.0, completedAt = 2000L)
        fakeDutyOccurrenceDao.insertDutyOccurrence(oldOccurrence)
        fakeDutyOccurrenceDao.insertDutyOccurrence(latestOccurrence)

        // When
        val result = dataSource.getAllDutiesWithLastOccurrence().first()

        // Then
        assertTrue(result.isSuccess)
        val duties = result.getOrNull()!!
        assertEquals(1, duties.size)
        assertEquals(2L, duties[0].second?.id) // Should be the latest occurrence
        assertEquals(100.0, duties[0].second?.paidAmount)
    }

    @Test
    fun `getAllDutiesWithLastOccurrence should handle mixed duties with and without occurrences`() = runTest {
        // Given
        val dutyWithOccurrence = createDutyEntity(1L, "Has occurrence", "Personal")
        val dutyWithoutOccurrence = createDutyEntity(2L, "No occurrence", "Company")
        fakeDutyDao.insertDuty(dutyWithOccurrence)
        fakeDutyDao.insertDuty(dutyWithoutOccurrence)

        val occurrence = createOccurrenceEntity(1L, dutyWithOccurrence.id, 150.0)
        fakeDutyOccurrenceDao.insertDutyOccurrence(occurrence)

        // When
        val result = dataSource.getAllDutiesWithLastOccurrence().first()

        // Then
        assertTrue(result.isSuccess)
        val duties = result.getOrNull()!!
        assertEquals(2, duties.size)
        
        val dutyWithOcc = duties.find { it.first.id == 1L }!!
        val dutyWithoutOcc = duties.find { it.first.id == 2L }!!
        
        assertEquals(150.0, dutyWithOcc.second?.paidAmount)
        assertNull(dutyWithoutOcc.second)
    }

    private fun createDutyEntity(
        id: Long,
        title: String,
        categoryName: String
    ) = DutyEntity(
        id = id,
        title = title,
        description = null,
        type = "PAYABLE",
        isCompleted = false,
        categoryName = categoryName,
        createdAt = Clock.System.now()
    )

    private fun createOccurrenceEntity(
        id: Long,
        dutyId: Long,
        paidAmount: Double,
        completedAt: Long = Clock.System.now().toEpochMilliseconds()
    ) = DutyOccurrenceEntity(
        id = id,
        dutyId = dutyId,
        paidAmount = paidAmount,
        completedDateMillis = completedAt
    )
}

