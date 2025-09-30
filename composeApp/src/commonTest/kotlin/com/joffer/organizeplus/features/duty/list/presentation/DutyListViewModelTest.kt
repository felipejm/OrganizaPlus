package com.joffer.organizeplus.features.duty.list.presentation

import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty.Status
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.dashboard.domain.usecases.DeleteDutyUseCase
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

class DutyListViewModelTest {

    @Test
    fun `init should load duties`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        val fakeDeleteDutyUseCase = FakeDeleteDutyUseCase()
        val fakeDutyOccurrenceRepository = FakeDutyOccurrenceRepository()
        
        val viewModel = DutyListViewModel(
            fakeRepository,
            fakeDeleteDutyUseCase,
            fakeDutyOccurrenceRepository
        )

        // When
        val uiState = viewModel.uiState.first()

        // Then
        assertTrue(fakeRepository.getAllDutiesCalled)
        assertFalse(uiState.isLoading)
    }

    @Test
    fun `onIntent LoadDuties should load duties`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        val fakeDeleteDutyUseCase = FakeDeleteDutyUseCase()
        val fakeDutyOccurrenceRepository = FakeDutyOccurrenceRepository()
        
        val viewModel = DutyListViewModel(
            fakeRepository,
            fakeDeleteDutyUseCase,
            fakeDutyOccurrenceRepository
        )

        // When
        viewModel.onIntent(DutyListIntent.LoadDuties)

        // Then
        assertTrue(fakeRepository.getAllDutiesCalled)
    }

    @Test
    fun `onIntent RefreshDuties should load duties`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        val fakeDeleteDutyUseCase = FakeDeleteDutyUseCase()
        val fakeDutyOccurrenceRepository = FakeDutyOccurrenceRepository()
        
        val viewModel = DutyListViewModel(
            fakeRepository,
            fakeDeleteDutyUseCase,
            fakeDutyOccurrenceRepository
        )

        // When
        viewModel.onIntent(DutyListIntent.RefreshDuties)

        // Then
        assertTrue(fakeRepository.getAllDutiesCalled)
    }

    @Test
    fun `onIntent SearchDuties should update search query`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        val fakeDeleteDutyUseCase = FakeDeleteDutyUseCase()
        val fakeDutyOccurrenceRepository = FakeDutyOccurrenceRepository()
        
        val viewModel = DutyListViewModel(
            fakeRepository,
            fakeDutyRepository,
            fakeDutyOccurrenceRepository
        )
        val searchQuery = "test search"

        // When
        viewModel.onIntent(DutyListIntent.SearchDuties(searchQuery))

        // Then
        val uiState = viewModel.uiState.first()
        assertEquals(searchQuery, uiState.searchQuery)
    }

    @Test
    fun `onIntent MarkDutyPaid should call repository markDutyPaid`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        val fakeDeleteDutyUseCase = FakeDeleteDutyUseCase()
        val fakeDutyOccurrenceRepository = FakeDutyOccurrenceRepository()
        
        val viewModel = DutyListViewModel(
            fakeRepository,
            fakeDeleteDutyUseCase,
            fakeDutyOccurrenceRepository
        )
        val dutyId = "duty-123"

        // When
        viewModel.onIntent(DutyListIntent.MarkDutyPaid(dutyId))

        // Then
        assertTrue(fakeRepository.markDutyPaidCalled)
        assertEquals(dutyId, fakeRepository.lastMarkDutyPaidId)
    }

    @Test
    fun `onIntent EditDuty should not throw exception`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        val fakeDeleteDutyUseCase = FakeDeleteDutyUseCase()
        val fakeDutyOccurrenceRepository = FakeDutyOccurrenceRepository()
        
        val viewModel = DutyListViewModel(
            fakeRepository,
            fakeDeleteDutyUseCase,
            fakeDutyOccurrenceRepository
        )
        val dutyId = "duty-123"

        // When & Then (should not throw)
        viewModel.onIntent(DutyListIntent.EditDuty(dutyId))
    }

    @Test
    fun `onIntent DeleteDuty should call delete use case`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        val fakeDeleteDutyUseCase = FakeDeleteDutyUseCase()
        val fakeDutyOccurrenceRepository = FakeDutyOccurrenceRepository()
        
        val viewModel = DutyListViewModel(
            fakeRepository,
            fakeDeleteDutyUseCase,
            fakeDutyOccurrenceRepository
        )
        val dutyId = "duty-123"

        // When
        viewModel.onIntent(DutyListIntent.DeleteDuty(dutyId))

        // Then
        assertTrue(fakeDeleteDutyUseCase.invokeCalled)
        assertEquals(dutyId, fakeDeleteDutyUseCase.lastDutyId)
    }

    @Test
    fun `onIntent ClearError should clear error`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        val fakeDeleteDutyUseCase = FakeDeleteDutyUseCase()
        val fakeDutyOccurrenceRepository = FakeDutyOccurrenceRepository()
        
        val viewModel = DutyListViewModel(
            fakeRepository,
            fakeDeleteDutyUseCase,
            fakeDutyOccurrenceRepository
        )

        // When
        viewModel.onIntent(DutyListIntent.ClearError)

        // Then
        val uiState = viewModel.uiState.first()
        assertEquals(null, uiState.error)
    }

    @Test
    fun `onIntent Retry should load duties`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        val fakeDeleteDutyUseCase = FakeDeleteDutyUseCase()
        val fakeDutyOccurrenceRepository = FakeDutyOccurrenceRepository()
        
        val viewModel = DutyListViewModel(
            fakeRepository,
            fakeDeleteDutyUseCase,
            fakeDutyOccurrenceRepository
        )

        // When
        viewModel.onIntent(DutyListIntent.Retry)

        // Then
        assertTrue(fakeRepository.getAllDutiesCalled)
    }

    @Test
    fun `should handle duties loading success`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        val fakeDeleteDutyUseCase = FakeDeleteDutyUseCase()
        val fakeDutyOccurrenceRepository = FakeDutyOccurrenceRepository()
        
        val duties = listOf(createDuty("1", "Test Duty"))
        fakeRepository.duties = duties
        
        val viewModel = DutyListViewModel(
            fakeRepository,
            fakeDeleteDutyUseCase,
            fakeDutyOccurrenceRepository
        )

        // When
        val uiState = viewModel.uiState.first()

        // Then
        assertFalse(uiState.isLoading)
        assertEquals(1, uiState.duties.size)
        assertEquals("Test Duty", uiState.duties.first().duty.title)
        assertEquals(null, uiState.error)
    }

    @Test
    fun `should handle duties loading failure`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        val fakeDeleteDutyUseCase = FakeDeleteDutyUseCase()
        val fakeDutyOccurrenceRepository = FakeDutyOccurrenceRepository()
        
        fakeRepository.shouldFail = true
        fakeRepository.errorMessage = "Network error"
        
        val viewModel = DutyListViewModel(
            fakeRepository,
            fakeDeleteDutyUseCase,
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
class FakeDutyRepository : DutyRepository {
    var getAllDutiesCalled = false
    var markDutyPaidCalled = false
    var lastMarkDutyPaidId: String? = null
    var duties = listOf<Duty>()
    var shouldFail = false
    var errorMessage = "Error"

    override suspend fun getAllDuties() = flowOf {
        getAllDutiesCalled = true
        if (shouldFail) Result.failure(RuntimeException(errorMessage))
        else Result.success(duties)
    }

    override suspend fun getDutyById(id: String) = flowOf(Result.success(null))

    override suspend fun insertDuty(duty: Duty) = flowOf(Result.success(Unit))

    override suspend fun updateDuty(duty: Duty) = flowOf(Result.success(Unit))

    override suspend fun deleteDuty(id: String) = flowOf(Result.success(Unit))

    override suspend fun markDutyPaid(id: String, paidAt: kotlinx.datetime.Instant) = flowOf {
        markDutyPaidCalled = true
        lastMarkDutyPaidId = id
        Result.success(Unit)
    }

    override suspend fun getUpcomingDuties(days: Int) = flowOf(Result.success(emptyList()))

    override suspend fun getLatestDuties(limit: Int) = flowOf(Result.success(emptyList()))
}

class FakeDeleteDutyUseCase : DeleteDutyUseCase {
    var invokeCalled = false
    var lastDutyId: String? = null
    var shouldFail = false

    override suspend fun invoke(dutyId: String): Result<Unit> {
        invokeCalled = true
        lastDutyId = dutyId
        return if (shouldFail) Result.failure(RuntimeException("Error"))
        else Result.success(Unit)
    }
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
        return Result.success(com.joffer.organizeplus.features.duty.detail.domain.entities.ChartData(emptyList(), dutyType))
    }

    override suspend fun deleteDutyOccurrence(id: String): Result<Unit> {
        return Result.success(Unit)
    }
}
