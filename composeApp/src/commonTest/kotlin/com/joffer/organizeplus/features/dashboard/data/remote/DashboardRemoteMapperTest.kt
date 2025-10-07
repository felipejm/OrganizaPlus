package com.joffer.organizeplus.features.dashboard.data.remote

import com.joffer.organizeplus.features.dashboard.domain.entities.*
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrence
import kotlinx.datetime.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class DashboardRemoteMapperTest {

    @Test
    fun `toDomain should map DashboardRemoteResponse to DashboardData correctly`() {
        // Given
        val remoteResponse = createSampleRemoteResponse()

        // When
        val result = DashboardRemoteMapper.toDomain(remoteResponse)

        // Then
        assertNotNull(result)
        assertEquals(3, result.upcomingDuties.size) // 2 personal + 1 company duty
        
        val personalDuties = result.upcomingDuties.filter { it.categoryName == "Personal" }
        val companyDuties = result.upcomingDuties.filter { it.categoryName == "Company" }
        
        assertEquals(2, personalDuties.size)
        assertEquals(1, companyDuties.size)
    }

    @Test
    fun `toDomain should map duty with PAYABLE type correctly`() {
        // Given
        val remoteDuty = EnhancedDutyRemote(
            id = 1L,
            title = "Gym Membership",
            type = "PAYABLE",
            categoryName = "Personal",
            createdAt = "2024-01-15T10:00:00Z"
        )

        // When
        val result = DashboardRemoteMapper.toDomain(remoteDuty)

        // Then
        assertEquals(1L, result.id)
        assertEquals("Gym Membership", result.title)
        assertEquals(DutyType.PAYABLE, result.type)
        assertEquals("Personal", result.categoryName)
        assertNotNull(result.createdAt)
    }

    @Test
    fun `toDomain should map duty with ACTIONABLE type correctly`() {
        // Given
        val remoteDuty = EnhancedDutyRemote(
            id = 2L,
            title = "Read Books",
            type = "ACTIONABLE",
            categoryName = "Personal",
            createdAt = "2024-03-10T11:30:00Z"
        )

        // When
        val result = DashboardRemoteMapper.toDomain(remoteDuty)

        // Then
        assertEquals(2L, result.id)
        assertEquals("Read Books", result.title)
        assertEquals(DutyType.ACTIONABLE, result.type)
        assertEquals("Personal", result.categoryName)
        assertNotNull(result.createdAt)
    }

    @Test
    fun `toDomain should default to ACTIONABLE for unknown duty type`() {
        // Given
        val remoteDuty = EnhancedDutyRemote(
            id = 3L,
            title = "Unknown Type",
            type = "UNKNOWN_TYPE",
            categoryName = "Personal",
            createdAt = "2024-03-10T11:30:00Z"
        )

        // When
        val result = DashboardRemoteMapper.toDomain(remoteDuty)

        // Then
        assertEquals(DutyType.ACTIONABLE, result.type)
    }

    @Test
    fun `toDomain should handle invalid createdAt timestamp gracefully`() {
        // Given
        val remoteDuty = EnhancedDutyRemote(
            id = 4L,
            title = "Invalid Timestamp",
            type = "PAYABLE",
            categoryName = "Personal",
            createdAt = "invalid-timestamp"
        )

        // When
        val result = DashboardRemoteMapper.toDomain(remoteDuty)

        // Then
        assertNotNull(result.createdAt) // Should fallback to current time
        assertEquals(4L, result.id)
        assertEquals("Invalid Timestamp", result.title)
    }

    @Test
    fun `toDomain should map duty occurrence with amount correctly`() {
        // Given
        val remoteOccurrence = EnhancedDutyOccurrenceRemote(
            id = 1L,
            dutyId = 1L,
            amountPaid = 49.99,
            completedAt = "2024-10-01T14:30:00Z",
            notes = "Monthly gym membership payment"
        )

        // When
        val result = DashboardRemoteMapper.toDomain(remoteOccurrence)

        // Then
        assertEquals(1L, result.id)
        assertEquals(1L, result.dutyId)
        assertEquals(49.99, result.paidAmount)
        assertNotNull(result.completedDate)
        assertNotNull(result.createdAt)
    }

    @Test
    fun `toDomain should map duty occurrence without amount correctly`() {
        // Given
        val remoteOccurrence = EnhancedDutyOccurrenceRemote(
            id = 2L,
            dutyId = 2L,
            amountPaid = null,
            completedAt = "2024-09-28T20:15:00Z",
            notes = "Finished reading 'Clean Code'"
        )

        // When
        val result = DashboardRemoteMapper.toDomain(remoteOccurrence)

        // Then
        assertEquals(2L, result.id)
        assertEquals(2L, result.dutyId)
        assertNull(result.paidAmount)
        assertNotNull(result.completedDate)
        assertNotNull(result.createdAt)
    }

    @Test
    fun `toDomain should handle invalid completedAt timestamp in occurrence gracefully`() {
        // Given
        val remoteOccurrence = EnhancedDutyOccurrenceRemote(
            id = 3L,
            dutyId = 3L,
            amountPaid = 100.0,
            completedAt = "invalid-timestamp",
            notes = "Invalid timestamp occurrence"
        )

        // When
        val result = DashboardRemoteMapper.toDomain(remoteOccurrence)

        // Then
        assertNotNull(result.completedDate) // Should fallback to current time
        assertNotNull(result.createdAt) // Should fallback to current time
        assertEquals(100.0, result.paidAmount)
    }

    @Test
    fun `toDomain should map monthly summary correctly`() {
        // Given
        val remoteSummary = EnhancedMonthlySummaryRemote(
            totalAmountPaid = 175.49,
            totalCompleted = 2,
            totalTasks = 3,
            currentMonth = 10,
            year = 2024
        )

        // When
        val result = DashboardRemoteMapper.toDomain(remoteSummary)

        // Then
        assertEquals(175.49, result.totalAmountPaid)
        assertEquals(2, result.totalCompleted)
        assertEquals(3, result.totalTasks)
        assertEquals(10, result.currentMonth)
        assertEquals(2024, result.year)
    }

    @Test
    fun `toDomain should map duty with last occurrence correctly`() {
        // Given
        val remoteDutyWithOccurrence = EnhancedDutyWithOccurrenceRemote(
            duty = EnhancedDutyRemote(
                id = 1L,
                title = "Gym Membership",
                type = "PAYABLE",
                categoryName = "Personal",
                createdAt = "2024-01-15T10:00:00Z"
            ),
            lastOccurrence = EnhancedDutyOccurrenceRemote(
                id = 1L,
                dutyId = 1L,
                amountPaid = 49.99,
                completedAt = "2024-10-01T14:30:00Z",
                notes = "Monthly gym membership payment"
            ),
            hasCurrentMonthOccurrence = true,
            status = "COMPLETED",
            nextDueDate = "2024-11-01",
            isOverdue = false,
            displayInfo = DutyDisplayInfoRemote(
                iconName = "paid",
                colorHex = "#4CAF50"
            )
        )

        // When
        val result = DashboardRemoteMapper.toDomain(remoteDutyWithOccurrence)

        // Then
        assertEquals(1L, result.duty.id)
        assertEquals("Gym Membership", result.duty.title)
        assertEquals(DutyType.PAYABLE, result.duty.type)
        assertNotNull(result.lastOccurrence)
        assertEquals(1L, result.lastOccurrence!!.id)
        assertEquals(49.99, result.lastOccurrence!!.paidAmount)
        assertEquals(true, result.hasCurrentMonthOccurrence)
    }

    @Test
    fun `toDomain should map duty without last occurrence correctly`() {
        // Given
        val remoteDutyWithOccurrence = EnhancedDutyWithOccurrenceRemote(
            duty = EnhancedDutyRemote(
                id = 2L,
                title = "Car Insurance",
                type = "PAYABLE",
                categoryName = "Personal",
                createdAt = "2024-02-01T09:00:00Z"
            ),
            lastOccurrence = null,
            hasCurrentMonthOccurrence = false,
            status = "PENDING",
            nextDueDate = "2024-11-01",
            isOverdue = false,
            displayInfo = DutyDisplayInfoRemote(
                iconName = "payable",
                colorHex = "#2196F3"
            )
        )

        // When
        val result = DashboardRemoteMapper.toDomain(remoteDutyWithOccurrence)

        // Then
        assertEquals(2L, result.duty.id)
        assertEquals("Car Insurance", result.duty.title)
        assertEquals(DutyType.PAYABLE, result.duty.type)
        assertNull(result.lastOccurrence)
        assertEquals(false, result.hasCurrentMonthOccurrence)
    }

    @Test
    fun `toDomain should handle empty remote response correctly`() {
        // Given
        val emptyRemoteResponse = DashboardRemoteResponse(
            dashboard = DashboardDataRemote(
                personalDuties = emptyList(),
                companyDuties = emptyList(),
                personalSummary = EnhancedMonthlySummaryRemote(
                    totalAmountPaid = 0.0,
                    totalCompleted = 0,
                    totalTasks = 0,
                    currentMonth = 10,
                    year = 2024
                ),
                companySummary = EnhancedMonthlySummaryRemote(
                    totalAmountPaid = 0.0,
                    totalCompleted = 0,
                    totalTasks = 0,
                    currentMonth = 10,
                    year = 2024
                ),
                overallStats = OverallStatsRemote(
                    totalDuties = 0,
                    totalCompletedThisMonth = 0,
                    totalAmountPaidThisMonth = 0.0,
                    hasOverdueItems = false
                )
            ),
            metadata = DashboardMetadataRemote(
                generatedAt = "2024-10-15T10:00:00Z",
                currentMonth = 10,
                currentYear = 2024,
                monthName = "October",
                hasData = false,
                isEmpty = true
            )
        )

        // When
        val result = DashboardRemoteMapper.toDomain(emptyRemoteResponse)

        // Then
        assertNotNull(result)
        assertEquals(0, result.upcomingDuties.size)
    }

    // Helper function to create sample remote response
    private fun createSampleRemoteResponse(): DashboardRemoteResponse {
        return DashboardRemoteResponse(
            dashboard = DashboardDataRemote(
                personalDuties = listOf(
                    EnhancedDutyWithOccurrenceRemote(
                        duty = EnhancedDutyRemote(
                            id = 1L,
                            title = "Gym Membership",
                            type = "PAYABLE",
                            categoryName = "Personal",
                            createdAt = "2024-01-15T10:00:00Z"
                        ),
                        lastOccurrence = EnhancedDutyOccurrenceRemote(
                            id = 1L,
                            dutyId = 1L,
                            amountPaid = 49.99,
                            completedAt = "2024-10-01T14:30:00Z",
                            notes = "Monthly gym membership payment"
                        ),
                        hasCurrentMonthOccurrence = true,
                        status = "COMPLETED",
                        nextDueDate = "2024-11-01",
                        isOverdue = false,
                        displayInfo = DutyDisplayInfoRemote(
                            iconName = "paid",
                            colorHex = "#4CAF50"
                        )
                    ),
                    EnhancedDutyWithOccurrenceRemote(
                        duty = EnhancedDutyRemote(
                            id = 2L,
                            title = "Read Books",
                            type = "ACTIONABLE",
                            categoryName = "Personal",
                            createdAt = "2024-03-10T11:30:00Z"
                        ),
                        lastOccurrence = null,
                        hasCurrentMonthOccurrence = false,
                        status = "PENDING",
                        nextDueDate = null,
                        isOverdue = false,
                        displayInfo = DutyDisplayInfoRemote(
                            iconName = "actionable",
                            colorHex = "#FFC107"
                        )
                    )
                ),
                companyDuties = listOf(
                    EnhancedDutyWithOccurrenceRemote(
                        duty = EnhancedDutyRemote(
                            id = 4L,
                            title = "Office Rent",
                            type = "PAYABLE",
                            categoryName = "Company",
                            createdAt = "2024-01-01T08:00:00Z"
                        ),
                        lastOccurrence = EnhancedDutyOccurrenceRemote(
                            id = 4L,
                            dutyId = 4L,
                            amountPaid = 2500.00,
                            completedAt = "2024-10-01T09:00:00Z",
                            notes = "Monthly office rent payment"
                        ),
                        hasCurrentMonthOccurrence = true,
                        status = "COMPLETED",
                        nextDueDate = "2024-11-01",
                        isOverdue = false,
                        displayInfo = DutyDisplayInfoRemote(
                            iconName = "paid",
                            colorHex = "#4CAF50"
                        )
                    )
                ),
                personalSummary = EnhancedMonthlySummaryRemote(
                    totalAmountPaid = 175.49,
                    totalCompleted = 2,
                    totalTasks = 3,
                    currentMonth = 10,
                    year = 2024
                ),
                companySummary = EnhancedMonthlySummaryRemote(
                    totalAmountPaid = 2500.00,
                    totalCompleted = 1,
                    totalTasks = 2,
                    currentMonth = 10,
                    year = 2024
                ),
                overallStats = OverallStatsRemote(
                    totalDuties = 5,
                    totalCompletedThisMonth = 3,
                    totalAmountPaidThisMonth = 2675.49,
                    hasOverdueItems = false
                )
            ),
            metadata = DashboardMetadataRemote(
                generatedAt = "2024-10-15T10:00:00Z",
                currentMonth = 10,
                currentYear = 2024,
                monthName = "October",
                hasData = true,
                isEmpty = false
            )
        )
    }
}
