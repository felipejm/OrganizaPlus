package com.joffer.organizeplus.features.duty.detail.domain.usecases.implementations

import com.joffer.organizeplus.features.duty.detail.domain.entities.DutyDetailsForm
import com.joffer.organizeplus.features.duty.detail.domain.repositories.DutyDetailsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SaveDutyDetailsUseCaseImplTest {

    @Test
    fun `invoke should create DutyDetails and insert record`() = runTest {
        // Given
        val fakeRepository = FakeDutyDetailsRepository()
        val useCase = SaveDutyDetailsUseCaseImpl(fakeRepository)
        val form = DutyDetailsForm(
            dutyId = "duty-123",
            paidAmount = 150.0,
            paidDate = Clock.System.now(),
            notes = "Test notes"
        )

        // When
        val result = useCase(form).first()

        // Then
        assertTrue(result.isSuccess)
        assertTrue(fakeRepository.insertRecordCalled)
        val insertedRecord = fakeRepository.lastInsertedRecord
        assertEquals("duty-123", insertedRecord?.dutyId)
        assertEquals(150.0, insertedRecord?.paidAmount)
        assertEquals("Test notes", insertedRecord?.notes)
        assertTrue(insertedRecord?.id?.isNotEmpty() == true)
    }

    @Test
    fun `invoke should generate unique id for record`() = runTest {
        // Given
        val fakeRepository = FakeDutyDetailsRepository()
        val useCase = SaveDutyDetailsUseCaseImpl(fakeRepository)
        val form = DutyDetailsForm(
            dutyId = "duty-123",
            paidAmount = 150.0,
            paidDate = Clock.System.now(),
            notes = "Test notes"
        )

        // When
        useCase(form).first()

        // Then
        val insertedRecord = fakeRepository.lastInsertedRecord
        assertTrue(insertedRecord?.id?.isNotEmpty() == true)
        // ID should be numeric (timestamp)
        assertTrue(insertedRecord?.id?.toLongOrNull() != null)
    }

    @Test
    fun `invoke should set current time for createdAt and updatedAt`() = runTest {
        // Given
        val fakeRepository = FakeDutyDetailsRepository()
        val useCase = SaveDutyDetailsUseCaseImpl(fakeRepository)
        val beforeCall = Clock.System.now()
        val form = DutyDetailsForm(
            dutyId = "duty-123",
            paidAmount = 150.0,
            paidDate = Clock.System.now(),
            notes = "Test notes"
        )

        // When
        useCase(form).first()

        // Then
        val afterCall = Clock.System.now()
        val insertedRecord = fakeRepository.lastInsertedRecord
        assertTrue(insertedRecord?.createdAt!! >= beforeCall)
        assertTrue(insertedRecord.createdAt <= afterCall)
        assertTrue(insertedRecord.updatedAt >= beforeCall)
        assertTrue(insertedRecord.updatedAt <= afterCall)
    }

    @Test
    fun `invoke should handle repository failure`() = runTest {
        // Given
        val fakeRepository = FakeDutyDetailsRepository()
        fakeRepository.shouldFail = true
        val useCase = SaveDutyDetailsUseCaseImpl(fakeRepository)
        val form = DutyDetailsForm(
            dutyId = "duty-123",
            paidAmount = 150.0,
            paidDate = Clock.System.now(),
            notes = "Test notes"
        )

        // When
        val result = useCase(form).first()

        // Then
        assertTrue(result.isFailure)
        assertTrue(fakeRepository.insertRecordCalled)
    }

    @Test
    fun `invoke should preserve all form data`() = runTest {
        // Given
        val fakeRepository = FakeDutyDetailsRepository()
        val useCase = SaveDutyDetailsUseCaseImpl(fakeRepository)
        val paidDate = Clock.System.now()
        val form = DutyDetailsForm(
            dutyId = "duty-456",
            paidAmount = 250.75,
            paidDate = paidDate,
            notes = "Detailed notes"
        )

        // When
        useCase(form).first()

        // Then
        val insertedRecord = fakeRepository.lastInsertedRecord
        assertEquals("duty-456", insertedRecord?.dutyId)
        assertEquals(250.75, insertedRecord?.paidAmount)
        assertEquals(paidDate, insertedRecord?.paidDate)
        assertEquals("Detailed notes", insertedRecord?.notes)
    }
}

// Fake implementation of DutyDetailsRepository for this test
class FakeDutyDetailsRepository : DutyDetailsRepository {
    var insertRecordCalled = false
    var lastInsertedRecord: com.joffer.organizeplus.features.duty.detail.domain.entities.DutyDetails? = null
    var shouldFail = false

    override suspend fun getAllRecords() = kotlinx.coroutines.flow.flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(emptyList())
    )

    override suspend fun getRecordsByDutyId(dutyId: String) = kotlinx.coroutines.flow.flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(emptyList())
    )

    override suspend fun getRecordById(id: String) = kotlinx.coroutines.flow.flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(null)
    )

    override suspend fun insertRecord(record: com.joffer.organizeplus.features.duty.detail.domain.entities.DutyDetails) = kotlinx.coroutines.flow.flowOf {
        insertRecordCalled = true
        lastInsertedRecord = record
        
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(Unit)
    }

    override suspend fun updateRecord(record: com.joffer.organizeplus.features.duty.detail.domain.entities.DutyDetails) = kotlinx.coroutines.flow.flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(Unit)
    )

    override suspend fun deleteRecord(id: String) = kotlinx.coroutines.flow.flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(Unit)
    )

    override suspend fun getRecordsByDateRange(startDate: kotlinx.datetime.Instant, endDate: kotlinx.datetime.Instant) = kotlinx.coroutines.flow.flowOf(
        if (shouldFail) Result.failure(RuntimeException("Repository error"))
        else Result.success(emptyList())
    )
}
