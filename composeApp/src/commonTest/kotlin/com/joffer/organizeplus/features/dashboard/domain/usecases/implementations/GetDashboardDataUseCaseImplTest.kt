package com.joffer.organizeplus.features.dashboard.domain.usecases.implementations

import com.joffer.organizeplus.features.dashboard.domain.entities.DashboardData
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty.Status
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetDashboardDataUseCaseImplTest {

    @Test
    fun `invoke should return dashboard data with upcoming duties`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        val useCase = GetDashboardDataUseCaseImpl(fakeRepository)
        val upcomingDuties = listOf(
            createDuty("1", "Upcoming Duty 1"),
            createDuty("2", "Upcoming Duty 2")
        )
        fakeRepository.upcomingDuties = upcomingDuties

        // When
        val result = useCase().first()

        // Then
        assertTrue(result.isSuccess)
        val dashboardData = result.getOrNull()
        assertEquals(2, dashboardData?.upcomingDuties?.size)
        assertEquals("Upcoming Duty 1", dashboardData?.upcomingDuties?.get(0)?.title)
        assertEquals("Upcoming Duty 2", dashboardData?.upcomingDuties?.get(1)?.title)
        assertEquals(emptyList<Duty>(), dashboardData?.latestDuties)
    }

    @Test
    fun `invoke should return empty dashboard data when no duties`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        val useCase = GetDashboardDataUseCaseImpl(fakeRepository)
        fakeRepository.upcomingDuties = emptyList()

        // When
        val result = useCase().first()

        // Then
        assertTrue(result.isSuccess)
        val dashboardData = result.getOrNull()
        assertEquals(emptyList<Duty>(), dashboardData?.upcomingDuties)
        assertEquals(emptyList<Duty>(), dashboardData?.latestDuties)
    }

    @Test
    fun `invoke should handle repository failure`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        fakeRepository.shouldFail = true
        val useCase = GetDashboardDataUseCaseImpl(fakeRepository)

        // When
        val result = useCase().first()

        // Then
        assertTrue(result.isFailure)
    }

    private fun createDuty(id: String, title: String): Duty {
        return Duty(
            id = id,
            title = title,
            startDay = 1,
            dueDay = 15,
            type = DutyType.ACTIONABLE,
            categoryName = "Personal",
            status = Duty.Status.PENDING,
            snoozeUntil = null,
            createdAt = Clock.System.now()
        )
    }
}

// Fake implementation of DutyRepository
class FakeDutyRepository : DutyRepository {
    var upcomingDuties = listOf<Duty>()
    var shouldFail = false

    override suspend fun getAllDuties() = flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error")) 
        else Result.success(upcomingDuties)
    )

    override suspend fun getDutyById(id: String) = flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(upcomingDuties.find { it.id == id })
    )

    override suspend fun insertDuty(duty: Duty) = flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(Unit)
    )

    override suspend fun updateDuty(duty: Duty) = flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(Unit)
    )

    override suspend fun deleteDuty(id: String) = flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(Unit)
    )

    override suspend fun markDutyPaid(id: String, paidAt: kotlinx.datetime.Instant) = flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(Unit)
    )

    override suspend fun getUpcomingDuties(days: Int) = flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(upcomingDuties)
    )

    override suspend fun getLatestDuties(limit: Int) = flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(upcomingDuties.take(limit))
    )
}
