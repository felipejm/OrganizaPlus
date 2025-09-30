package com.joffer.organizeplus.features.duty.occurrence.domain.usecases.implementations

import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrence
import com.joffer.organizeplus.features.duty.occurrence.domain.repositories.DutyOccurrenceRepository
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SaveDutyOccurrenceUseCaseImplTest {

    @Test
    fun `invoke should delegate to repository and return result`() = runTest {
        // Given
        val fakeRepository = FakeDutyOccurrenceRepository()
        val useCase = SaveDutyOccurrenceUseCaseImpl(fakeRepository)
        val dutyOccurrence = DutyOccurrence(
            id = "",
            dutyId = "duty-123",
            paidAmount = 100.0,
            completedDate = LocalDate(2024, 1, 15),
            createdAt = LocalDate(2024, 1, 15)
        )

        // When
        val result = useCase(dutyOccurrence)

        // Then
        assertTrue(result.isSuccess)
        assertTrue(fakeRepository.saveDutyOccurrenceCalled)
        assertEquals(dutyOccurrence, fakeRepository.lastSavedOccurrence)
    }

    @Test
    fun `invoke should return failure when repository fails`() = runTest {
        // Given
        val fakeRepository = FakeDutyOccurrenceRepository()
        fakeRepository.shouldFail = true
        val useCase = SaveDutyOccurrenceUseCaseImpl(fakeRepository)
        val dutyOccurrence = DutyOccurrence(
            id = "",
            dutyId = "duty-123",
            paidAmount = 100.0,
            completedDate = LocalDate(2024, 1, 15),
            createdAt = LocalDate(2024, 1, 15)
        )

        // When
        val result = useCase(dutyOccurrence)

        // Then
        assertTrue(result.isFailure)
        assertTrue(fakeRepository.saveDutyOccurrenceCalled)
    }

    @Test
    fun `invoke should return success with updated occurrence when repository succeeds`() = runTest {
        // Given
        val fakeRepository = FakeDutyOccurrenceRepository()
        val useCase = SaveDutyOccurrenceUseCaseImpl(fakeRepository)
        val dutyOccurrence = DutyOccurrence(
            id = "",
            dutyId = "duty-123",
            paidAmount = 100.0,
            completedDate = LocalDate(2024, 1, 15),
            createdAt = LocalDate(2024, 1, 15)
        )

        // When
        val result = useCase(dutyOccurrence)

        // Then
        assertTrue(result.isSuccess)
        val savedOccurrence = result.getOrNull()
        assertEquals("1", savedOccurrence?.id) // Repository assigns ID
        assertEquals("duty-123", savedOccurrence?.dutyId)
        assertEquals(100.0, savedOccurrence?.paidAmount)
    }
}

// Fake implementation of DutyOccurrenceRepository for this test
class FakeDutyOccurrenceRepository : DutyOccurrenceRepository {
    var saveDutyOccurrenceCalled = false
    var lastSavedOccurrence: DutyOccurrence? = null
    var shouldFail = false

    override suspend fun saveDutyOccurrence(dutyOccurrence: DutyOccurrence): Result<DutyOccurrence> {
        saveDutyOccurrenceCalled = true
        lastSavedOccurrence = dutyOccurrence
        
        return if (shouldFail) {
            Result.failure(RuntimeException("Repository error"))
        } else {
            val savedOccurrence = dutyOccurrence.copy(id = "1")
            Result.success(savedOccurrence)
        }
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
