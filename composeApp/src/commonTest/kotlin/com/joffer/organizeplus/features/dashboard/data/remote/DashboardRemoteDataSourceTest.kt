package com.joffer.organizeplus.features.dashboard.data.remote

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class DashboardRemoteDataSourceTest {

    @Test
    fun `DashboardRemoteDataSourceImpl should be created with correct parameters`() {
        // Given
        val baseUrl = "https://wkh7195cqj.execute-api.us-east-1.amazonaws.com"
        
        // When
        val dataSource = DashboardRemoteDataSourceImpl(
            httpClient = io.ktor.client.HttpClient(),
            baseUrl = baseUrl
        )

        // Then
        assertNotNull(dataSource)
        // Note: We can't easily test the HTTP client behavior without complex mocking
        // This test ensures the class can be instantiated correctly
    }

    @Test
    fun `DashboardRemoteResponse should serialize and deserialize correctly`() {
        // Given
        val originalResponse = createSampleDashboardResponse()

        // When
        val json = kotlinx.serialization.json.Json.encodeToString(DashboardRemoteResponse.serializer(), originalResponse)
        val deserializedResponse = kotlinx.serialization.json.Json.decodeFromString(DashboardRemoteResponse.serializer(), json)

        // Then
        assertEquals(originalResponse.dashboard.personalDuties.size, deserializedResponse.dashboard.personalDuties.size)
        assertEquals(originalResponse.dashboard.companyDuties.size, deserializedResponse.dashboard.companyDuties.size)
        assertEquals(originalResponse.metadata.currentMonth, deserializedResponse.metadata.currentMonth)
        assertEquals(originalResponse.metadata.currentYear, deserializedResponse.metadata.currentYear)
    }

    @Test
    fun `DashboardRemoteResponse should handle empty data correctly`() {
        // Given
        val emptyResponse = DashboardRemoteResponse(
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
        val json = kotlinx.serialization.json.Json.encodeToString(DashboardRemoteResponse.serializer(), emptyResponse)
        val deserializedResponse = kotlinx.serialization.json.Json.decodeFromString(DashboardRemoteResponse.serializer(), json)

        // Then
        assertNotNull(deserializedResponse)
        assertEquals(0, deserializedResponse.dashboard.personalDuties.size)
        assertEquals(0, deserializedResponse.dashboard.companyDuties.size)
        assertTrue(deserializedResponse.metadata.isEmpty)
        assertFalse(deserializedResponse.metadata.hasData)
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
