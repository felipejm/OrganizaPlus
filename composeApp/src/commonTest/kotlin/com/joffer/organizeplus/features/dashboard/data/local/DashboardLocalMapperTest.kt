package com.joffer.organizeplus.features.dashboard.data.local

import com.joffer.organizeplus.database.entities.DutyEntity
import com.joffer.organizeplus.database.entities.DutyOccurrenceEntity
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DashboardLocalMapperTest {

    @Test
    fun `toDomain should map empty list correctly`() {
        // Given
        val emptyList = emptyList<Pair<DutyEntity, DutyOccurrenceEntity?>>()

        // When
        val result = DashboardLocalMapper.toDomain(emptyList)

        // Then
        assertNotNull(result)
        assertEquals(0, result.upcomingDuties.size)
    }

    @Test
    fun `toDomain should map duties without occurrences correctly`() {
        // Given
        val dutiesWithoutOccurrences = listOf(
            Pair(createDutyEntity(1L, "Duty 1", "Personal", "PAYABLE"), null),
            Pair(createDutyEntity(2L, "Duty 2", "Company", "ACTIONABLE"), null)
        )

        // When
        val result = DashboardLocalMapper.toDomain(dutiesWithoutOccurrences)

        // Then
        assertEquals(2, result.upcomingDuties.size)
        assertEquals("Duty 1", result.upcomingDuties[0].title)
        assertEquals(DutyType.PAYABLE, result.upcomingDuties[0].type)
        assertEquals("Duty 2", result.upcomingDuties[1].title)
        assertEquals(DutyType.ACTIONABLE, result.upcomingDuties[1].type)
    }

    @Test
    fun `toDomain should map duties with occurrences correctly`() {
        // Given
        val dutiesWithOccurrences = listOf(
            Pair(
                createDutyEntity(1L, "Duty 1", "Personal", "PAYABLE"),
                createOccurrenceEntity(1L, 1L, 100.0)
            )
        )

        // When
        val result = DashboardLocalMapper.toDomain(dutiesWithOccurrences)

        // Then
        assertEquals(1, result.upcomingDuties.size)
        assertEquals("Duty 1", result.upcomingDuties[0].title)
    }

    @Test
    fun `toDutyWithLastOccurrence should map duty without occurrence correctly`() {
        // Given
        val dutyEntity = createDutyEntity(1L, "Test Duty", "Personal", "PAYABLE")

        // When
        val result = DashboardLocalMapper.toDutyWithLastOccurrence(dutyEntity, null)

        // Then
        assertEquals(1L, result.duty.id)
        assertEquals("Test Duty", result.duty.title)
        assertNull(result.lastOccurrence)
        assertFalse(result.hasCurrentMonthOccurrence)
    }

    @Test
    fun `toDutyWithLastOccurrence should map duty with occurrence correctly`() {
        // Given
        val dutyEntity = createDutyEntity(1L, "Test Duty", "Personal", "PAYABLE")
        val occurrenceEntity = createOccurrenceEntity(1L, 1L, 150.0)

        // When
        val result = DashboardLocalMapper.toDutyWithLastOccurrence(dutyEntity, occurrenceEntity)

        // Then
        assertEquals(1L, result.duty.id)
        assertEquals("Test Duty", result.duty.title)
        assertNotNull(result.lastOccurrence)
        assertEquals(150.0, result.lastOccurrence?.paidAmount)
    }

    @Test
    fun `toDutyWithLastOccurrence should detect current month occurrence correctly`() {
        // Given
        val dutyEntity = createDutyEntity(1L, "Test Duty", "Personal", "PAYABLE")
        val now = Clock.System.now()
        val currentMonthOccurrence = createOccurrenceEntity(
            1L, 1L, 100.0, 
            completedAt = now.toEpochMilliseconds()
        )

        // When
        val result = DashboardLocalMapper.toDutyWithLastOccurrence(dutyEntity, currentMonthOccurrence)

        // Then
        assertTrue(result.hasCurrentMonthOccurrence)
    }

    @Test
    fun `toDutyWithLastOccurrence should detect previous month occurrence correctly`() {
        // Given
        val dutyEntity = createDutyEntity(1L, "Test Duty", "Personal", "PAYABLE")
        
        // Create a timestamp from 60 days ago (definitely not current month)
        val now = Clock.System.now()
        val sixtyDaysInMillis = 60L * 24L * 60L * 60L * 1000L
        val lastMonthMillis = now.toEpochMilliseconds() - sixtyDaysInMillis
        
        val lastMonthOccurrence = createOccurrenceEntity(1L, 1L, 100.0, completedAt = lastMonthMillis)

        // When
        val result = DashboardLocalMapper.toDutyWithLastOccurrence(dutyEntity, lastMonthOccurrence)

        // Then
        assertFalse(result.hasCurrentMonthOccurrence)
    }

    @Test
    fun `toDutiesWithLastOccurrence should map multiple duties correctly`() {
        // Given
        val dutiesWithOccurrences = listOf(
            Pair(createDutyEntity(1L, "Duty 1", "Personal", "PAYABLE"), createOccurrenceEntity(1L, 1L, 100.0)),
            Pair(createDutyEntity(2L, "Duty 2", "Company", "ACTIONABLE"), null),
            Pair(createDutyEntity(3L, "Duty 3", "Personal", "PAYABLE"), createOccurrenceEntity(3L, 3L, 200.0))
        )

        // When
        val result = DashboardLocalMapper.toDutiesWithLastOccurrence(dutiesWithOccurrences)

        // Then
        assertEquals(3, result.size)
        assertEquals("Duty 1", result[0].duty.title)
        assertNotNull(result[0].lastOccurrence)
        assertEquals("Duty 2", result[1].duty.title)
        assertNull(result[1].lastOccurrence)
        assertEquals("Duty 3", result[2].duty.title)
        assertNotNull(result[2].lastOccurrence)
    }

    @Test
    fun `toDomain should handle mixed duty types correctly`() {
        // Given
        val mixedDuties = listOf(
            Pair(createDutyEntity(1L, "Payable Duty", "Personal", "PAYABLE"), null),
            Pair(createDutyEntity(2L, "Actionable Duty", "Company", "ACTIONABLE"), null)
        )

        // When
        val result = DashboardLocalMapper.toDomain(mixedDuties)

        // Then
        assertEquals(2, result.upcomingDuties.size)
        assertEquals(DutyType.PAYABLE, result.upcomingDuties[0].type)
        assertEquals(DutyType.ACTIONABLE, result.upcomingDuties[1].type)
    }

    private fun createDutyEntity(
        id: Long,
        title: String,
        categoryName: String,
        type: String
    ) = DutyEntity(
        id = id,
        title = title,
        description = null,
        type = type,
        isCompleted = false,
        categoryName = categoryName,
        createdAt = Clock.System.now()
    )

    private fun createOccurrenceEntity(
        id: Long,
        dutyId: Long,
        paidAmount: Double,
        completedAt: Long = Clock.System.now().toEpochMilliseconds()
    ) = DutyOccurrenceEntity(
        id = id,
        dutyId = dutyId,
        paidAmount = paidAmount,
        completedDateMillis = completedAt
    )
}

