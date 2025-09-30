package com.joffer.organizeplus.features.duty.create.domain.usecases.implementations

import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.dashboard.domain.entities.Status
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyForm
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SaveCreateDutyUseCaseImplTest {

    @Test
    fun `invoke should insert new duty when id is null`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        val useCase = SaveCreateDutyUseCaseImpl(fakeRepository)
        val form = CreateDutyForm(
            id = null,
            title = "New Duty",
            startDay = 1,
            dueDay = 15,
            dutyType = DutyType.ACTIONABLE,
            categoryName = "Personal"
        )

        // When
        val result = useCase(form).first()

        // Then
        assertTrue(result.isSuccess)
        assertTrue(fakeRepository.insertDutyCalled)
        assertFalse(fakeRepository.updateDutyCalled)
        assertEquals("New Duty", fakeRepository.lastInsertedDuty?.title)
        assertEquals(Status.PENDING, fakeRepository.lastInsertedDuty?.status)
    }

    @Test
    fun `invoke should update existing duty when id is provided`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        val useCase = SaveCreateDutyUseCaseImpl(fakeRepository)
        val form = CreateDutyForm(
            id = "existing-duty-123",
            title = "Updated Duty",
            startDay = 1,
            dueDay = 15,
            dutyType = DutyType.PAYABLE,
            categoryName = "Business"
        )

        // When
        val result = useCase(form).first()

        // Then
        assertTrue(result.isSuccess)
        assertFalse(fakeRepository.insertDutyCalled)
        assertTrue(fakeRepository.updateDutyCalled)
        assertEquals("existing-duty-123", fakeRepository.lastUpdatedDuty?.id)
        assertEquals("Updated Duty", fakeRepository.lastUpdatedDuty?.title)
    }

    @Test
    fun `invoke should generate unique id for new duty`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        val useCase = SaveCreateDutyUseCaseImpl(fakeRepository)
        val form = CreateDutyForm(
            id = null,
            title = "New Duty",
            startDay = 1,
            dueDay = 15,
            dutyType = DutyType.ACTIONABLE,
            categoryName = "Personal"
        )

        // When
        useCase(form).first()

        // Then
        val generatedId = fakeRepository.lastInsertedDuty?.id
        assertTrue(generatedId?.startsWith("obligation_") == true)
        assertTrue(generatedId?.length!! > 20) // Should have timestamp
    }

    @Test
    fun `invoke should set correct duty properties`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        val useCase = SaveCreateDutyUseCaseImpl(fakeRepository)
        val form = CreateDutyForm(
            id = null,
            title = "Test Duty",
            startDay = 5,
            dueDay = 20,
            dutyType = DutyType.PAYABLE,
            categoryName = "Business"
        )

        // When
        useCase(form).first()

        // Then
        val duty = fakeRepository.lastInsertedDuty!!
        assertEquals("Test Duty", duty.title)
        assertEquals(5, duty.startDay)
        assertEquals(20, duty.dueDay)
        assertEquals(DutyType.PAYABLE, duty.type)
        assertEquals("Business", duty.categoryName)
        assertEquals(Status.PENDING, duty.status)
        assertTrue(duty.createdAt != null)
    }

    @Test
    fun `invoke should handle repository failure`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        fakeRepository.shouldFail = true
        val useCase = SaveCreateDutyUseCaseImpl(fakeRepository)
        val form = CreateDutyForm(
            id = null,
            title = "New Duty",
            startDay = 1,
            dueDay = 15,
            dutyType = DutyType.ACTIONABLE,
            categoryName = "Personal"
        )

        // When
        val result = useCase(form).first()

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `invoke should handle conversion exception`() = runTest {
        // Given
        val fakeRepository = FakeDutyRepository()
        val useCase = SaveCreateDutyUseCaseImpl(fakeRepository)
        // Create a form that might cause conversion issues
        val form = CreateDutyForm(
            id = null,
            title = "", // Empty title might cause issues
            startDay = 1,
            dueDay = 15,
            dutyType = DutyType.ACTIONABLE,
            categoryName = "Personal"
        )

        // When
        val result = useCase(form).first()

        // Then
        // Should still succeed as the form validation is not in this use case
        assertTrue(result.isSuccess)
    }
}

// Fake implementation of DutyRepository for this test
class FakeDutyRepository : DutyRepository {
    var insertDutyCalled = false
    var updateDutyCalled = false
    var lastInsertedDuty: Duty? = null
    var lastUpdatedDuty: Duty? = null
    var shouldFail = false

    override suspend fun getAllDuties() = kotlinx.coroutines.flow.flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(emptyList())
    )

    override suspend fun getDutyById(id: String) = kotlinx.coroutines.flow.flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(null)
    )

    override suspend fun insertDuty(duty: Duty) = kotlinx.coroutines.flow.flowOf {
        insertDutyCalled = true
        lastInsertedDuty = duty
        
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(Unit)
    }

    override suspend fun updateDuty(duty: Duty) = kotlinx.coroutines.flow.flowOf {
        updateDutyCalled = true
        lastUpdatedDuty = duty
        
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(Unit)
    }

    override suspend fun deleteDuty(id: String) = kotlinx.coroutines.flow.flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(Unit)
    )

    override suspend fun markDutyPaid(id: String, paidAt: kotlinx.datetime.Instant) = kotlinx.coroutines.flow.flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(Unit)
    )

    override suspend fun getUpcomingDuties(days: Int) = kotlinx.coroutines.flow.flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(emptyList())
    )

    override suspend fun getLatestDuties(limit: Int) = kotlinx.coroutines.flow.flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(emptyList())
    )
}
