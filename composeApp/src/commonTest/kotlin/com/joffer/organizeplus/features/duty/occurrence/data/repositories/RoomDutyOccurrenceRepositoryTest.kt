package com.joffer.organizeplus.features.duty.occurrence.data.repositories

import com.joffer.organizeplus.database.entities.DutyOccurrenceEntity
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrence
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.datetime.LocalDate

class RoomDutyOccurrenceRepositoryTest {

    private val fakeDutyOccurrenceDao = FakeDutyOccurrenceDao()
    private val repository = RoomDutyOccurrenceRepository(fakeDutyOccurrenceDao)

    @BeforeTest
    fun setUp() {
        fakeDutyOccurrenceDao.clearAll()
    }

    @Test
    fun `test save duty occurrence`() = runTest {
        val now = Clock.System.now()
        val localDate = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
        val occurrence = DutyOccurrence(
            id = 0L,
            dutyId = 1L,
            paidAmount = 50.0,
            completedDate = localDate,
            createdAt = localDate
        )

        val result = repository.saveDutyOccurrence(occurrence)

        assertTrue(result.isSuccess)
        assertEquals(1, fakeDutyOccurrenceDao.getOccurrenceCount())
    }

    @Test
    fun `test find occurrences by duty id`() = runTest {
        val now = Clock.System.now()
        fakeDutyOccurrenceDao.insertDutyOccurrence(occurrence(1L, 1L, 25.0, now))
        fakeDutyOccurrenceDao.insertDutyOccurrence(occurrence(2L, 1L, 30.0, now))
        fakeDutyOccurrenceDao.insertDutyOccurrence(occurrence(3L, 2L, 40.0, now))

        val result = repository.getDutyOccurrencesByDutyId(1L)

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()!!.size)
    }

    @Test
    fun `test get most recent occurrence`() = runTest {
        val now = Clock.System.now()
        val later = kotlinx.datetime.Instant.fromEpochMilliseconds(now.toEpochMilliseconds() + 1000)
        fakeDutyOccurrenceDao.insertDutyOccurrence(occurrence(1L, 1L, 20.0, now))
        fakeDutyOccurrenceDao.insertDutyOccurrence(occurrence(2L, 1L, 35.0, later))

        val result = repository.getLastOccurrenceByDutyId(1L)

        assertTrue(result.isSuccess)
        assertEquals(2L, result.getOrNull()!!.id)
    }

    @Test
    fun `test get chart data for actionable duties`() = runTest {
        val now = Clock.System.now()
        fakeDutyOccurrenceDao.insertDutyOccurrence(occurrence(1L, 1L, 0.0, now))
        fakeDutyOccurrenceDao.insertDutyOccurrence(occurrence(2L, 1L, 0.0, now))

        val result = repository.getMonthlyChartData(1L, DutyType.ACTIONABLE)

        assertTrue(result.isSuccess)
        val chartData = result.getOrNull()!!
        assertEquals(DutyType.ACTIONABLE, chartData.dutyType)
        assertEquals(2.0f, chartData.monthlyData[0].value)
    }

    @Test
    fun `test get chart data for payable duties`() = runTest {
        val now = Clock.System.now()
        fakeDutyOccurrenceDao.insertDutyOccurrence(occurrence(1L, 1L, 15.0, now))
        fakeDutyOccurrenceDao.insertDutyOccurrence(occurrence(2L, 1L, 25.0, now))

        val result = repository.getMonthlyChartData(1L, DutyType.PAYABLE)

        assertTrue(result.isSuccess)
        val chartData = result.getOrNull()!!
        assertEquals(DutyType.PAYABLE, chartData.dutyType)
        assertEquals(40.0f, chartData.monthlyData[0].value)
    }

    @Test
    fun `test delete occurrence`() = runTest {
        val now = Clock.System.now()
        fakeDutyOccurrenceDao.insertDutyOccurrence(occurrence(1L, 1L, 10.0, now))

        val result = repository.deleteDutyOccurrence(1L)

        assertTrue(result.isSuccess)
        assertEquals(0, fakeDutyOccurrenceDao.getOccurrenceCount())
    }

    private fun occurrence(
        id: Long,
        dutyId: Long,
        paidAmount: Double,
        completedDate: kotlinx.datetime.Instant
    ) = DutyOccurrenceEntity(
        id = id,
        dutyId = dutyId,
        paidAmount = paidAmount,
        completedDateMillis = completedDate.toEpochMilliseconds()
    )
}
