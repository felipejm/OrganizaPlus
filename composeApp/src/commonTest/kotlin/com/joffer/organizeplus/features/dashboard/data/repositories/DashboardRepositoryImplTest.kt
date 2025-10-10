package com.joffer.organizeplus.features.dashboard.data.repositories

import com.joffer.organizeplus.database.entities.DutyEntity
import com.joffer.organizeplus.database.entities.DutyOccurrenceEntity
import com.joffer.organizeplus.features.dashboard.data.local.FakeDashboardLocalDataSource
import com.joffer.organizeplus.features.dashboard.data.remote.*
import com.joffer.organizeplus.features.duty.data.FakeDutyRepository
import com.joffer.organizeplus.features.duty.occurrence.data.FakeDutyOccurrenceRepository
import com.joffer.organizeplus.features.settings.data.FakeSettingsRepository
import com.joffer.organizeplus.features.settings.domain.StorageMode
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DashboardRepositoryImplTest {

    private lateinit var fakeRemoteDataSource: FakeDashboardRemoteDataSource
    private lateinit var fakeLocalDataSource: FakeDashboardLocalDataSource
    private lateinit var fakeSettingsRepository: FakeSettingsRepository
    private lateinit var fakeDutyRepository: FakeDutyRepository
    private lateinit var fakeDutyOccurrenceRepository: FakeDutyOccurrenceRepository
    private lateinit var repository: DashboardRepositoryImpl

    @BeforeTest
    fun setUp() {
        fakeRemoteDataSource = FakeDashboardRemoteDataSource()
        fakeLocalDataSource = FakeDashboardLocalDataSource()
        fakeSettingsRepository = FakeSettingsRepository()
        fakeDutyRepository = FakeDutyRepository()
        fakeDutyOccurrenceRepository = FakeDutyOccurrenceRepository()

        repository = DashboardRepositoryImpl(
            remoteDataSource = fakeRemoteDataSource,
            localDataSource = fakeLocalDataSource,
            settingsRepository = fakeSettingsRepository,
            dutyRepository = fakeDutyRepository,
            dutyOccurrenceRepository = fakeDutyOccurrenceRepository
        )
    }

    @Test
    fun `getDashboardData should fetch from remote when storage mode is REMOTE`() = runTest {
        // Given
        fakeSettingsRepository.setStorageMode(StorageMode.REMOTE)
        fakeRemoteDataSource.remoteResponse = createSampleRemoteResponse()

        // When
        val result = repository.getDashboardData().first()

        // Then
        assertTrue(result.isSuccess)
        val dashboardData = result.getOrNull()!!
        assertEquals(2, dashboardData.upcomingDuties.size)
    }

    @Test
    fun `getDashboardData should fetch from local when storage mode is LOCAL`() = runTest {
        // Given
        fakeSettingsRepository.setStorageMode(StorageMode.LOCAL)
        fakeLocalDataSource.setDuties(createSampleLocalData())

        // When
        val result = repository.getDashboardData().first()

        // Then
        assertTrue(result.isSuccess)
        val dashboardData = result.getOrNull()!!
        assertEquals(2, dashboardData.upcomingDuties.size)
    }

    @Test
    fun `getDashboardData should return failure when remote mode fails`() = runTest {
        // Given
        fakeSettingsRepository.setStorageMode(StorageMode.REMOTE)
        fakeRemoteDataSource.shouldThrowError = true
        fakeRemoteDataSource.errorMessage = "Network error"

        // When
        val result = repository.getDashboardData().first()

        // Then
        assertFalse(result.isSuccess)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `getDashboardData should return failure when local mode fails`() = runTest {
        // Given
        fakeSettingsRepository.setStorageMode(StorageMode.LOCAL)
        fakeLocalDataSource.shouldThrowError = true
        fakeLocalDataSource.errorMessage = "Database error"

        // When
        val result = repository.getDashboardData().first()

        // Then
        assertFalse(result.isSuccess)
        assertEquals("Database error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `getDashboardData should switch data source based on storage mode changes`() = runTest {
        // Given - Start with REMOTE mode
        fakeSettingsRepository.setStorageMode(StorageMode.REMOTE)
        fakeRemoteDataSource.remoteResponse = createSampleRemoteResponse()

        // When - Fetch with REMOTE mode
        val remoteResult = repository.getDashboardData().first()

        // Then
        assertTrue(remoteResult.isSuccess)

        // Given - Switch to LOCAL mode
        fakeSettingsRepository.setStorageMode(StorageMode.LOCAL)
        fakeLocalDataSource.setDuties(createSampleLocalData())

        // When - Fetch with LOCAL mode
        val localResult = repository.getDashboardData().first()

        // Then
        assertTrue(localResult.isSuccess)
    }

    @Test
    fun `getDashboardData should return empty list when local has no data`() = runTest {
        // Given
        fakeSettingsRepository.setStorageMode(StorageMode.LOCAL)
        fakeLocalDataSource.setDuties(emptyList())

        // When
        val result = repository.getDashboardData().first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(0, result.getOrNull()!!.upcomingDuties.size)
    }

    @Test
    fun `getDashboardData should return empty list when remote has no data`() = runTest {
        // Given
        fakeSettingsRepository.setStorageMode(StorageMode.REMOTE)
        fakeRemoteDataSource.remoteResponse = createEmptyRemoteResponse()

        // When
        val result = repository.getDashboardData().first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(0, result.getOrNull()!!.upcomingDuties.size)
    }

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
                            createdAt = "2024-01-15T10:00:00Z",
                            frequency = "MONTHLY",
                            estimatedAmount = 49.99
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
                            categoryIcon = "payable",
                            typeDisplayName = "Payable",
                            shortDescription = null
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
                        nextDueDate = null,
                        isOverdue = false,
                        displayInfo = DutyDisplayInfoRemote(
                            priority = "HIGH",
                            categoryColor = "#2196F3",
                            categoryIcon = "payable",
                            typeDisplayName = "Payable",
                            shortDescription = null
                        )
                    )
                ),
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
                    totalDuties = 2,
                    completedThisMonth = 0,
                    pendingDuties = 2,
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
                hasData = true,
                isEmpty = false
            )
        )
    }

    private fun createEmptyRemoteResponse(): DashboardRemoteResponse {
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

    private fun createSampleLocalData(): List<Pair<DutyEntity, DutyOccurrenceEntity?>> {
        return listOf(
            Pair(
                DutyEntity(
                    id = 1L,
                    title = "Local Duty 1",
                    description = null,
                    type = "PAYABLE",
                    isCompleted = false,
                    categoryName = "Personal",
                    createdAt = Clock.System.now()
                ),
                null
            ),
            Pair(
                DutyEntity(
                    id = 2L,
                    title = "Local Duty 2",
                    description = null,
                    type = "ACTIONABLE",
                    isCompleted = false,
                    categoryName = "Company",
                    createdAt = Clock.System.now()
                ),
                null
            )
        )
    }
}

