package com.joffer.organizeplus.features.dashboard.domain.usecases.implementations

import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MarkObligationPaidUseCaseImplTest {

    @Test
    fun `invoke should call repository markDutyPaid with current time`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        val useCase = MarkObligationPaidUseCaseImpl(fakeRepository)
        val obligationId = "test-obligation-123"

        // When
        val result = useCase(obligationId).first()

        // Then
        assertTrue(result.isSuccess)
        assertTrue(fakeRepository.markDutyPaidCalled)
        assertEquals(obligationId, fakeRepository.lastMarkDutyPaidId)
        assertTrue(fakeRepository.lastMarkDutyPaidTime != null)
    }

    @Test
    fun `invoke should handle repository failure`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        fakeRepository.shouldFail = true
        val useCase = MarkObligationPaidUseCaseImpl(fakeRepository)
        val obligationId = "test-obligation-123"

        // When
        val result = useCase(obligationId).first()

        // Then
        assertTrue(result.isFailure)
        assertTrue(fakeRepository.markDutyPaidCalled)
    }

    @Test
    fun `invoke should use current time for paidAt`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        val useCase = MarkObligationPaidUseCaseImpl(fakeRepository)
        val obligationId = "test-obligation-123"
        val beforeCall = Clock.System.now()

        // When
        useCase(obligationId).first()

        // Then
        val afterCall = Clock.System.now()
        val paidAt = fakeRepository.lastMarkDutyPaidTime!!
        assertTrue(paidAt >= beforeCall)
        assertTrue(paidAt <= afterCall)
    }
}

// Fake implementation of DutyRepository for this test
class FakeDutyRepository : DutyRepository {
    var markDutyPaidCalled = false
    var lastMarkDutyPaidId: String? = null
    var lastMarkDutyPaidTime: kotlinx.datetime.Instant? = null
    var shouldFail = false

    override suspend fun getAllDuties() = kotlinx.coroutines.flow.flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(emptyList())
    )

    override suspend fun getDutyById(id: String) = kotlinx.coroutines.flow.flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(null)
    )

    override suspend fun insertDuty(duty: com.joffer.organizeplus.features.dashboard.domain.entities.Duty) = kotlinx.coroutines.flow.flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(Unit)
    )

    override suspend fun updateDuty(duty: com.joffer.organizeplus.features.dashboard.domain.entities.Duty) = kotlinx.coroutines.flow.flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(Unit)
    )

    override suspend fun deleteDuty(id: String) = kotlinx.coroutines.flow.flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(Unit)
    )

    override suspend fun markDutyPaid(id: String, paidAt: kotlinx.datetime.Instant) = kotlinx.coroutines.flow.flowOf {
        markDutyPaidCalled = true
        lastMarkDutyPaidId = id
        lastMarkDutyPaidTime = paidAt
        
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(Unit)
    }

    override suspend fun getUpcomingDuties(days: Int) = kotlinx.coroutines.flow.flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(emptyList())
    )

    override suspend fun getLatestDuties(limit: Int) = kotlinx.coroutines.flow.flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(emptyList())
    )
}
