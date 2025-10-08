package com.joffer.organizeplus.features.dashboard.data.remote

import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class DashboardRemoteMapperTest {

    @Test
    fun `toDomain should map DashboardRemoteResponse to DashboardData correctly`() {
        // Given
        val remoteResponse = createSampleDashboardResponse()

        // When
        val result = DashboardRemoteMapper.toDomain(remoteResponse)

        // Then
        assertNotNull(result)
        assertEquals(2, result.upcomingDuties.size)
    }

    @Test
    fun `toDomain should map EnhancedDutyRemote to Duty correctly`() {
        // Given
        val remoteDuty = EnhancedDutyRemote(
            id = 1L,
            title = "Test Duty",
            type = "PAYABLE",
            categoryName = "Personal",
            createdAt = "2024-10-01T10:00:00Z",
            frequency = "MONTHLY",
            estimatedAmount = 100.0
        )

        // When
        val result = DashboardRemoteMapper.toDomain(remoteDuty)

        // Then
        assertEquals(1L, result.id)
        assertEquals("Test Duty", result.title)
        assertEquals(DutyType.PAYABLE, result.type)
        assertEquals("Personal", result.categoryName)
    }

    @Test
    fun `toDomain should map ACTIONABLE type correctly`() {
        // Given
        val remoteDuty = EnhancedDutyRemote(
            id = 2L,
            title = "Action Item",
            type = "ACTIONABLE",
            categoryName = "Work",
            createdAt = "2024-10-01T10:00:00Z",
            frequency = null,
            estimatedAmount = null
        )

        // When
        val result = DashboardRemoteMapper.toDomain(remoteDuty)

        // Then
        assertEquals(DutyType.ACTIONABLE, result.type)
    }

    @Test
    fun `toDomain should handle unknown type as ACTIONABLE`() {
        // Given
        val remoteDuty = EnhancedDutyRemote(
            id = 3L,
            title = "Unknown Type",
            type = "UNKNOWN",
            categoryName = "Other",
            createdAt = "2024-10-01T10:00:00Z",
            frequency = null,
            estimatedAmount = null
        )

        // When
        val result = DashboardRemoteMapper.toDomain(remoteDuty)

        // Then
        assertEquals(DutyType.ACTIONABLE, result.type)
    }

    @Test
    fun `toDomain should map EnhancedDutyOccurrenceRemote to DutyOccurrence correctly`() {
        // Given
        val remoteOccurrence = EnhancedDutyOccurrenceRemote(
            id = 1L,
            dutyId = 1L,
            amountPaid = 50.0,
            completedAt = "2024-10-01T14:30:00Z",
            notes = "Test note",
            formattedAmount = "$50.00",
            daysAgo = 7,
            isRecent = true
        )

        // When
        val result = DashboardRemoteMapper.toDomain(remoteOccurrence)

        // Then
        assertEquals(1L, result.id)
        assertEquals(1L, result.dutyId)
        assertEquals(50.0, result.paidAmount)
    }

    @Test
    fun `toDomain should handle null occurrence correctly`() {
        // Given
        val dutyWithoutOccurrence = EnhancedDutyWithOccurrenceRemote(
            duty = EnhancedDutyRemote(
                id = 1L,
                title = "No Occurrence",
                type = "PAYABLE",
                categoryName = "Personal",
                createdAt = "2024-10-01T10:00:00Z",
                frequency = null,
                estimatedAmount = null
            ),
            lastOccurrence = null,
            hasCurrentMonthOccurrence = false,
            status = DutyStatusRemote(
                isCompleted = false,
                isPaid = false,
                statusText = "PENDING",
                statusColor = "#FFC107"
            ),
            nextDueDate = null,
            isOverdue = false,
            displayInfo = DutyDisplayInfoRemote(
                priority = "NORMAL",
                categoryColor = "#2196F3",
                categoryIcon = "duty",
                typeDisplayName = "Payable",
                shortDescription = null
            )
        )

        // When
        val result = DashboardRemoteMapper.toDomain(dutyWithoutOccurrence)

        // Then
        assertNotNull(result.duty)
        assertNull(result.lastOccurrence)
        assertEquals(false, result.hasCurrentMonthOccurrence)
    }

    @Test
    fun `toDomain should map empty dashboard correctly`() {
        // Given
        val emptyResponse = createEmptyDashboardResponse()

        // When
        val result = DashboardRemoteMapper.toDomain(emptyResponse)

        // Then
        assertEquals(0, result.upcomingDuties.size)
    }

    @Test
    fun `toDomain should handle invalid date format gracefully`() {
        // Given
        val dutyWithInvalidDate = EnhancedDutyRemote(
            id = 1L,
            title = "Invalid Date",
            type = "PAYABLE",
            categoryName = "Personal",
            createdAt = "invalid-date-format",
            frequency = null,
            estimatedAmount = null
        )

        // When
        val result = DashboardRemoteMapper.toDomain(dutyWithInvalidDate)

        // Then
        assertNotNull(result.createdAt)
    }

    @Test
    fun `toDomain should map EnhancedMonthlySummaryRemote correctly`() {
        // Given
        val remoteSummary = EnhancedMonthlySummaryRemote(
            totalAmountPaid = 150.50,
            totalCompleted = 5,
            totalTasks = 10,
            currentMonth = 10,
            year = 2024,
            formattedAmount = "$150.50",
            completionRate = 50.0,
            monthName = "October",
            comparisonWithPreviousMonth = "+20% from last month"
        )

        // When
        val result = DashboardRemoteMapper.toDomain(remoteSummary)

        // Then
        assertEquals(150.50, result.totalAmountPaid)
        assertEquals(5, result.totalCompleted)
        assertEquals(10, result.totalTasks)
        assertEquals(10, result.currentMonth)
        assertEquals(2024, result.year)
    }

    private fun createSampleDashboardResponse(): DashboardRemoteResponse {
        return DashboardRemoteResponse(
            dashboard = DashboardDataRemote(
                personalDuties = listOf(
                    EnhancedDutyWithOccurrenceRemote(
                        duty = EnhancedDutyRemote(
                            id = 1L,
                            title = "Gym Membership",
                            type = "PAYABLE",
                            categoryName = "Personal",
                            createdAt = "2024-01-15T10:00:00Z",
                            frequency = "MONTHLY",
                            estimatedAmount = 49.99
                        ),
                        lastOccurrence = EnhancedDutyOccurrenceRemote(
                            id = 1L,
                            dutyId = 1L,
                            amountPaid = 49.99,
                            completedAt = "2024-10-01T14:30:00Z",
                            notes = "Paid",
                            formattedAmount = "$49.99",
                            daysAgo = 7,
                            isRecent = true
                        ),
                        hasCurrentMonthOccurrence = true,
                        status = DutyStatusRemote(
                            isCompleted = true,
                            isPaid = true,
                            statusText = "COMPLETED",
                            statusColor = "#4CAF50"
                        ),
                        nextDueDate = "2024-11-01",
                        isOverdue = false,
                        displayInfo = DutyDisplayInfoRemote(
                            priority = "NORMAL",
                            categoryColor = "#4CAF50",
                            categoryIcon = "gym",
                            typeDisplayName = "Payable",
                            shortDescription = "Monthly gym payment"
                        )
                    )
                ),
                companyDuties = listOf(
                    EnhancedDutyWithOccurrenceRemote(
                        duty = EnhancedDutyRemote(
                            id = 2L,
                            title = "Office Rent",
                            type = "PAYABLE",
                            categoryName = "Company",
                            createdAt = "2024-01-01T08:00:00Z",
                            frequency = "MONTHLY",
                            estimatedAmount = 2500.0
                        ),
                        lastOccurrence = null,
                        hasCurrentMonthOccurrence = false,
                        status = DutyStatusRemote(
                            isCompleted = false,
                            isPaid = false,
                            statusText = "PENDING",
                            statusColor = "#FFC107"
                        ),
                        nextDueDate = "2024-11-01",
                        isOverdue = false,
                        displayInfo = DutyDisplayInfoRemote(
                            priority = "HIGH",
                            categoryColor = "#2196F3",
                            categoryIcon = "business",
                            typeDisplayName = "Payable",
                            shortDescription = null
                        )
                    )
                ),
                personalSummary = EnhancedMonthlySummaryRemote(
                    totalAmountPaid = 49.99,
                    totalCompleted = 1,
                    totalTasks = 1,
                    currentMonth = 10,
                    year = 2024,
                    formattedAmount = "$49.99",
                    completionRate = 100.0,
                    monthName = "October",
                    comparisonWithPreviousMonth = null
                ),
                companySummary = EnhancedMonthlySummaryRemote(
                    totalAmountPaid = 0.0,
                    totalCompleted = 0,
                    totalTasks = 1,
                    currentMonth = 10,
                    year = 2024,
                    formattedAmount = "$0.00",
                    completionRate = 0.0,
                    monthName = "October",
                    comparisonWithPreviousMonth = null
                ),
                overallStats = OverallStatsRemote(
                    totalDuties = 2,
                    completedThisMonth = 1,
                    pendingDuties = 1,
                    overdueDuties = 0,
                    totalAmountPaidThisMonth = 49.99,
                    formattedTotalAmount = "$49.99",
                    completionRate = 50.0,
                    hasOverdueItems = false,
                    needsAttention = emptyList()
                )
            ),
            metadata = DashboardMetadataRemote(
                generatedAt = "2024-10-15T10:00:00Z",
                currentMonth = 10,
                currentYear = 2024,
                monthName = "October",
                timezone = "UTC",
                version = "1.0.0",
                hasData = true,
                isEmpty = false
            )
        )
    }

    private fun createEmptyDashboardResponse(): DashboardRemoteResponse {
        return DashboardRemoteResponse(
            dashboard = DashboardDataRemote(
                personalDuties = emptyList(),
                companyDuties = emptyList(),
                personalSummary = EnhancedMonthlySummaryRemote(
                    totalAmountPaid = 0.0,
                    totalCompleted = 0,
                    totalTasks = 0,
                    currentMonth = 10,
                    year = 2024,
                    formattedAmount = "$0.00",
                    completionRate = 0.0,
                    monthName = "October",
                    comparisonWithPreviousMonth = null
                ),
                companySummary = EnhancedMonthlySummaryRemote(
                    totalAmountPaid = 0.0,
                    totalCompleted = 0,
                    totalTasks = 0,
                    currentMonth = 10,
                    year = 2024,
                    formattedAmount = "$0.00",
                    completionRate = 0.0,
                    monthName = "October",
                    comparisonWithPreviousMonth = null
                ),
                overallStats = OverallStatsRemote(
                    totalDuties = 0,
                    completedThisMonth = 0,
                    pendingDuties = 0,
                    overdueDuties = 0,
                    totalAmountPaidThisMonth = 0.0,
                    formattedTotalAmount = "$0.00",
                    completionRate = 0.0,
                    hasOverdueItems = false,
                    needsAttention = emptyList()
                )
            ),
            metadata = DashboardMetadataRemote(
                generatedAt = "2024-10-15T10:00:00Z",
                currentMonth = 10,
                currentYear = 2024,
                monthName = "October",
                timezone = "UTC",
                version = "1.0.0",
                hasData = false,
                isEmpty = true
            )
        )
    }
}

