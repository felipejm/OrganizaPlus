package com.joffer.organizeplus.features.dashboard.presentation

import com.joffer.organizeplus.features.dashboard.DashboardIntent
import com.joffer.organizeplus.features.dashboard.DashboardUiState
import com.joffer.organizeplus.features.dashboard.domain.entities.DashboardData
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty.Status
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.dashboard.domain.usecases.GetDashboardDataUseCase
import com.joffer.organizeplus.features.dashboard.domain.usecases.MarkObligationPaidUseCase
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrence
import com.joffer.organizeplus.features.duty.occurrence.domain.repositories.DutyOccurrenceRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DashboardViewModelTest {

    @Test
    fun `init should load dashboard data`() = runTest {
        // Given
        val fakeGetDashboardDataUseCase = FakeGetDashboardDataUseCase()
        val fakeMarkObligationPaidUseCase = FakeMarkObligationPaidUseCase()
        val fakeDutyRepository = FakeDutyRepository()
        val fakeDutyOccurrenceRepository = FakeDutyOccurrenceRepository()
        
        val viewModel = DashboardViewModel(
            fakeGetDashboardDataUseCase,
            fakeMarkObligationPaidUseCase,
            fakeDutyRepository,
            fakeDutyOccurrenceRepository
        )

        // When
        val uiState = viewModel.uiState.first()

        // Then
        assertTrue(fakeGetDashboardDataUseCase.invokeCalled)
        assertTrue(fakeDutyRepository.getLatestDutiesCalled)
    }

    @Test
    fun `onIntent LoadDashboard should load dashboard data`() = runTest {
        // Given
        val fakeGetDashboardDataUseCase = FakeGetDashboardDataUseCase()
        val fakeMarkObligationPaidUseCase = FakeMarkObligationPaidUseCase()
        val fakeDutyRepository = FakeDutyRepository()
        val fakeDutyOccurrenceRepository = FakeDutyOccurrenceRepository()
        
        val viewModel = DashboardViewModel(
            fakeGetDashboardDataUseCase,
            fakeMarkObligationPaidUseCase,
            fakeDutyRepository,
            fakeDutyOccurrenceRepository
        )

        // When
        viewModel.onIntent(DashboardIntent.LoadDashboard)

        // Then
        assertTrue(fakeGetDashboardDataUseCase.invokeCalled)
    }

    @Test
    fun `onIntent RefreshDashboard should load dashboard data`() = runTest {
        // Given
        val fakeGetDashboardDataUseCase = FakeGetDashboardDataUseCase()
        val fakeMarkObligationPaidUseCase = FakeMarkObligationPaidUseCase()
        val fakeDutyRepository = FakeDutyRepository()
        val fakeDutyOccurrenceRepository = FakeDutyOccurrenceRepository()
        
        val viewModel = DashboardViewModel(
            fakeGetDashboardDataUseCase,
            fakeMarkObligationPaidUseCase,
            fakeDutyRepository,
            fakeDutyOccurrenceRepository
        )

        // When
        viewModel.onIntent(DashboardIntent.RefreshDashboard)

        // Then
        assertTrue(fakeGetDashboardDataUseCase.invokeCalled)
    }

    @Test
    fun `onIntent MarkObligationPaid should call mark obligation paid use case`() = runTest {
        // Given
        val fakeGetDashboardDataUseCase = FakeGetDashboardDataUseCase()
        val fakeMarkObligationPaidUseCase = FakeMarkObligationPaidUseCase()
        val fakeDutyRepository = FakeDutyRepository()
        val fakeDutyOccurrenceRepository = FakeDutyOccurrenceRepository()
        
        val viewModel = DashboardViewModel(
            fakeGetDashboardDataUseCase,
            fakeMarkObligationPaidUseCase,
            fakeDutyRepository,
            fakeDutyOccurrenceRepository
        )
        val obligationId = "obligation-123"

        // When
        viewModel.onIntent(DashboardIntent.MarkObligationPaid(obligationId))

        // Then
        assertTrue(fakeMarkObligationPaidUseCase.invokeCalled)
        assertEquals(obligationId, fakeMarkObligationPaidUseCase.lastObligationId)
    }

    @Test
    fun `onIntent ClearError should clear error in ui state`() = runTest {
        // Given
        val fakeGetDashboardDataUseCase = FakeGetDashboardDataUseCase()
        val fakeMarkObligationPaidUseCase = FakeMarkObligationPaidUseCase()
        val fakeDutyRepository = FakeDutyRepository()
        val fakeDutyOccurrenceRepository = FakeDutyOccurrenceRepository()
        
        val viewModel = DashboardViewModel(
            fakeGetDashboardDataUseCase,
            fakeMarkObligationPaidUseCase,
            fakeDutyRepository,
            fakeDutyOccurrenceRepository
        )

        // When
        viewModel.onIntent(DashboardIntent.ClearError)

        // Then
        val uiState = viewModel.uiState.first()
        assertEquals(null, uiState.error)
    }

    @Test
    fun `onIntent Retry should load dashboard data`() = runTest {
        // Given
        val fakeGetDashboardDataUseCase = FakeGetDashboardDataUseCase()
        val fakeMarkObligationPaidUseCase = FakeMarkObligationPaidUseCase()
        val fakeDutyRepository = FakeDutyRepository()
        val fakeDutyOccurrenceRepository = FakeDutyOccurrenceRepository()
        
        val viewModel = DashboardViewModel(
            fakeGetDashboardDataUseCase,
            fakeMarkObligationPaidUseCase,
            fakeDutyRepository,
            fakeDutyOccurrenceRepository
        )

        // When
        viewModel.onIntent(DashboardIntent.Retry)

        // Then
        assertTrue(fakeGetDashboardDataUseCase.invokeCalled)
    }

    @Test
    fun `setUserName should update user name`() = runTest {
        // Given
        val fakeGetDashboardDataUseCase = FakeGetDashboardDataUseCase()
        val fakeMarkObligationPaidUseCase = FakeMarkObligationPaidUseCase()
        val fakeDutyRepository = FakeDutyRepository()
        val fakeDutyOccurrenceRepository = FakeDutyOccurrenceRepository()
        
        val viewModel = DashboardViewModel(
            fakeGetDashboardDataUseCase,
            fakeMarkObligationPaidUseCase,
            fakeDutyRepository,
            fakeDutyOccurrenceRepository
        )
        val newName = "John Doe"

        // When
        viewModel.setUserName(newName)

        // Then
        val userName = viewModel.userName.first()
        assertEquals(newName, userName)
    }

    @Test
    fun `should handle dashboard data success`() = runTest {
        // Given
        val fakeGetDashboardDataUseCase = FakeGetDashboardDataUseCase()
        val fakeMarkObligationPaidUseCase = FakeMarkObligationPaidUseCase()
        val fakeDutyRepository = FakeDutyRepository()
        val fakeDutyOccurrenceRepository = FakeDutyOccurrenceRepository()
        
        val upcomingDuties = listOf(createDuty("1", "Upcoming Duty"))
        fakeGetDashboardDataUseCase.dashboardData = DashboardData(upcomingDuties, emptyList())
        
        val viewModel = DashboardViewModel(
            fakeGetDashboardDataUseCase,
            fakeMarkObligationPaidUseCase,
            fakeDutyRepository,
            fakeDutyOccurrenceRepository
        )

        // When
        val uiState = viewModel.uiState.first()

        // Then
        assertFalse(uiState.isLoading)
        assertEquals(1, uiState.upcomingDuties.size)
        assertEquals("Upcoming Duty", uiState.upcomingDuties.first().title)
        assertEquals(null, uiState.error)
    }

    @Test
    fun `should handle dashboard data failure`() = runTest {
        // Given
        val fakeGetDashboardDataUseCase = FakeGetDashboardDataUseCase()
        val fakeMarkObligationPaidUseCase = FakeMarkObligationPaidUseCase()
        val fakeDutyRepository = FakeDutyRepository()
        val fakeDutyOccurrenceRepository = FakeDutyOccurrenceRepository()
        
        fakeGetDashboardDataUseCase.shouldFail = true
        fakeGetDashboardDataUseCase.errorMessage = "Network error"
        
        val viewModel = DashboardViewModel(
            fakeGetDashboardDataUseCase,
            fakeMarkObligationPaidUseCase,
            fakeDutyRepository,
            fakeDutyOccurrenceRepository
        )

        // When
        val uiState = viewModel.uiState.first()

        // Then
        assertFalse(uiState.isLoading)
        assertEquals("Network error", uiState.error)
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

// Fake implementations
class FakeGetDashboardDataUseCase : GetDashboardDataUseCase {
    var invokeCalled = false
    var dashboardData = DashboardData(emptyList(), emptyList())
    var shouldFail = false
    var errorMessage = "Error"

    override suspend fun invoke() = flowOf(
        run {
            invokeCalled = true
            if (shouldFail) Result.failure(RuntimeException(errorMessage))
            else Result.success(dashboardData)
        }
    )
}

class FakeMarkObligationPaidUseCase : MarkObligationPaidUseCase {
    var invokeCalled = false
    var lastObligationId: String? = null
    var shouldFail = false

    override suspend fun invoke(obligationId: String) = flowOf(
        run {
            invokeCalled = true
            lastObligationId = obligationId
            if (shouldFail) Result.failure(RuntimeException("Error"))
            else Result.success(Unit)
        }
    )
}

class FakeDutyRepository : DutyRepository {
    var getLatestDutiesCalled = false

    override suspend fun getAllDuties() = flowOf(Result.success(emptyList()))

    override suspend fun getDutyById(id: String) = flowOf(Result.success(null))

    override suspend fun insertDuty(duty: Duty) = flowOf(Result.success(Unit))

    override suspend fun updateDuty(duty: Duty) = flowOf(Result.success(Unit))

    override suspend fun deleteDuty(id: String) = flowOf(Result.success(Unit))

    override suspend fun markDutyPaid(id: String, paidAt: kotlinx.datetime.Instant) = flowOf(Result.success(Unit))

    override suspend fun getUpcomingDuties(days: Int) = flowOf(Result.success(emptyList()))

    override suspend fun getLatestDuties(limit: Int) = flowOf(
        run {
            getLatestDutiesCalled = true
            Result.success(emptyList())
        }
    )
}

class FakeDutyOccurrenceRepository : DutyOccurrenceRepository {
    override suspend fun saveDutyOccurrence(dutyOccurrence: DutyOccurrence): Result<DutyOccurrence> {
        return Result.success(dutyOccurrence)
    }

    override suspend fun getDutyOccurrencesByDutyId(dutyId: String): Result<List<DutyOccurrence>> {
        return Result.success(emptyList())
    }

    override suspend fun getLastOccurrenceByDutyId(dutyId: String): Result<DutyOccurrence?> {
        return Result.success(null)
    }

    override suspend fun getMonthlyChartData(dutyId: String, dutyType: com.joffer.organizeplus.features.dashboard.domain.entities.DutyType): Result<com.joffer.organizeplus.features.duty.detail.domain.entities.ChartData> {
        return Result.success(com.joffer.organizeplus.features.duty.detail.domain.entities.ChartData(emptyList(), 0.0))
    }

    override suspend fun getMonthlyOccurrences(categoryName: String, month: Int, year: Int): Result<List<DutyOccurrence>> {
        return Result.success(emptyList())
    }

    override suspend fun deleteDutyOccurrence(id: String): Result<Unit> {
        return Result.success(Unit)
    }
}
