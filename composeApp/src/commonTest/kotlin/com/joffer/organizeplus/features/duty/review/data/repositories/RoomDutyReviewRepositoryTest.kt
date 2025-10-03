package com.joffer.organizeplus.features.duty.review.data.repositories

import com.joffer.organizeplus.database.entities.DutyOccurrenceEntity
import com.joffer.organizeplus.features.duty.review.domain.entities.DutyReviewData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RoomDutyReviewRepositoryTest {

    private val fakeDutyOccurrenceDao = com.joffer.organizeplus.features.duty.occurrence.data.repositories.FakeDutyOccurrenceDao()
    private val repository = RoomDutyReviewRepository(fakeDutyOccurrenceDao)

    @BeforeTest
    fun setUp() {
        fakeDutyOccurrenceDao.clearAll()
    }

    @Test
    fun `test get duty review data`() = runTest {
        val now = Clock.System.now()
        fakeDutyOccurrenceDao.insertDutyOccurrence(occurrence(1L, 1L, 100.0, now))
        fakeDutyOccurrenceDao.insertDutyOccurrence(occurrence(2L, 2L, 200.0, now))

        val result = repository.getDutyReviewData(null).first()

        assertTrue(result.isSuccess)
        val reviewData = result.getOrNull()!!
        assertTrue(reviewData.monthlyReviews.isNotEmpty())
        assertEquals(300.0, reviewData.grandTotal)
    }

    @Test
    fun `test filter by category`() = runTest {
        val now = Clock.System.now()
        fakeDutyOccurrenceDao.insertDutyOccurrence(occurrence(1L, 1L, 100.0, now))
        fakeDutyOccurrenceDao.insertDutyOccurrence(occurrence(2L, 2L, 200.0, now))

        val result = repository.getDutyReviewData("Personal").first()

        assertTrue(result.isSuccess)
        val reviewData = result.getOrNull()!!
        assertTrue(reviewData.monthlyReviews.isNotEmpty())
    }

    @Test
    fun `test empty data when no occurrences`() = runTest {
        val result = repository.getDutyReviewData(null).first()

        assertTrue(result.isSuccess)
        val reviewData = result.getOrNull()!!
        assertTrue(reviewData.monthlyReviews.isEmpty())
        assertEquals(0.0, reviewData.grandTotal)
    }

    @Test
    fun `test group by month`() = runTest {
        val now = Clock.System.now()
        fakeDutyOccurrenceDao.insertDutyOccurrence(occurrence(1L, 1L, 100.0, now))
        fakeDutyOccurrenceDao.insertDutyOccurrence(occurrence(2L, 2L, 200.0, now))

        val result = repository.getDutyReviewData(null).first()

        assertTrue(result.isSuccess)
        val reviewData = result.getOrNull()!!
        assertEquals(1, reviewData.monthlyReviews.size)
        val monthlyReview = reviewData.monthlyReviews[0]
        assertEquals(2, monthlyReview.dutyItems.size)
        assertEquals(300.0, monthlyReview.totalPaid)
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
