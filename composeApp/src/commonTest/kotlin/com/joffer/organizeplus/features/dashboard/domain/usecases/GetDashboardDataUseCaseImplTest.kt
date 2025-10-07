package com.joffer.organizeplus.features.dashboard.domain.usecases

import com.joffer.organizeplus.features.dashboard.domain.entities.DashboardData
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.dashboard.domain.usecases.implementations.GetDashboardDataUseCaseImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GetDashboardDataUseCaseImplTest {

    private lateinit var fakeRepository: FakeDashboardRepository
    private lateinit var useCase: GetDashboardDataUseCaseImpl

    @BeforeTest
    fun setUp() {
        fakeRepository = FakeDashboardRepository()
        useCase = GetDashboardDataUseCaseImpl(fakeRepository)
    }

    @Test
    fun `invoke should return success when repository returns data`() = runTest {
        // Given
        val expectedData = createSampleDashboardData()
        fakeRepository.dashboardData = expectedData

        // When
        val result = useCase().first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedData, result.getOrNull())
    }

    @Test
    fun `invoke should return failure when repository fails`() = runTest {
        // Given
        fakeRepository.shouldThrowError = true
        fakeRepository.errorMessage = "Failed to fetch data"

        // When
        val result = useCase().first()

        // Then
        assertFalse(result.isSuccess)
        assertEquals("Failed to fetch data", result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke should return empty dashboard when repository returns empty data`() = runTest {
        // Given
        val emptyData = DashboardData(upcomingDuties = emptyList())
        fakeRepository.dashboardData = emptyData

        // When
        val result = useCase().first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(0, result.getOrNull()?.upcomingDuties?.size)
    }

    @Test
    fun `invoke should propagate dashboard data with multiple duties`() = runTest {
        // Given
        val dashboardData = DashboardData(
            upcomingDuties = listOf(
                createDuty(1L, "Duty 1", DutyType.PAYABLE),
                createDuty(2L, "Duty 2", DutyType.ACTIONABLE),
                createDuty(3L, "Duty 3", DutyType.PAYABLE)
            )
        )
        fakeRepository.dashboardData = dashboardData

        // When
        val result = useCase().first()

        // Then
        assertTrue(result.isSuccess)
        val data = result.getOrNull()!!
        assertEquals(3, data.upcomingDuties.size)
        assertEquals("Duty 1", data.upcomingDuties[0].title)
        assertEquals("Duty 2", data.upcomingDuties[1].title)
        assertEquals("Duty 3", data.upcomingDuties[2].title)
    }

    @Test
    fun `invoke should handle repository exceptions gracefully`() = runTest {
        // Given
        fakeRepository.shouldThrowError = true
        fakeRepository.errorMessage = "Network timeout"

        // When
        val result = useCase().first()

        // Then
        assertFalse(result.isSuccess)
        assertTrue(result.exceptionOrNull() is Exception)
    }

    private fun createSampleDashboardData(): DashboardData {
        return DashboardData(
            upcomingDuties = listOf(
                createDuty(1L, "Gym Membership", DutyType.PAYABLE),
                createDuty(2L, "Read Books", DutyType.ACTIONABLE)
            )
        )
    }

    private fun createDuty(id: Long, title: String, type: DutyType): Duty {
        return Duty(
            id = id,
            title = title,
            type = type,
            categoryName = "Personal",
            createdAt = Clock.System.now()
        )
    }
}

