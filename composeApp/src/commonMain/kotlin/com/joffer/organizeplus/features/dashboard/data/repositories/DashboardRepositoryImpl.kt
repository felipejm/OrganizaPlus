package com.joffer.organizeplus.features.dashboard.data.repositories

import com.joffer.organizeplus.common.constants.CategoryConstants
import com.joffer.organizeplus.features.dashboard.MonthlySummary
import com.joffer.organizeplus.features.dashboard.data.local.DashboardLocalDataSource
import com.joffer.organizeplus.features.dashboard.data.local.DashboardLocalMapper
import com.joffer.organizeplus.features.dashboard.data.remote.DashboardRemoteDataSource
import com.joffer.organizeplus.features.dashboard.data.remote.DashboardRemoteMapper
import com.joffer.organizeplus.features.dashboard.domain.entities.DashboardData
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyWithLastOccurrence
import com.joffer.organizeplus.features.dashboard.domain.repositories.DashboardRepository
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.duty.occurrence.domain.repositories.DutyOccurrenceRepository
import com.joffer.organizeplus.features.settings.domain.StorageMode
import com.joffer.organizeplus.features.settings.domain.repositories.SettingsRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * Dashboard repository implementation that coordinates data from multiple sources
 *
 * Responsibilities:
 * - Aggregate data from duty and duty occurrence repositories
 * - Handle storage mode (local/remote) switching
 * - Provide complete dashboard data in a single call
 */
class DashboardRepositoryImpl(
    private val remoteDataSource: DashboardRemoteDataSource,
    private val localDataSource: DashboardLocalDataSource,
    private val settingsRepository: SettingsRepository,
    private val dutyRepository: DutyRepository,
    private val dutyOccurrenceRepository: DutyOccurrenceRepository
) : DashboardRepository {

    override suspend fun getDashboardData(): Flow<Result<DashboardData>> = flow {
        try {
            val storageMode = settingsRepository.getStorageMode()

            when (storageMode) {
                StorageMode.REMOTE -> {
                    // Use consolidated dashboard endpoint - everything in one call!
                    val result = remoteDataSource.getDashboardData()
                        .map { remoteResponse -> DashboardRemoteMapper.toDomain(remoteResponse) }
                    emit(result)
                }
                StorageMode.LOCAL -> {
                    // Load from local database
                    loadLocalDashboardData().collect { emit(it) }
                }
            }
        } catch (exception: Exception) {
            Napier.e("Error loading dashboard data: ${exception.message}", exception)
            emit(Result.failure(exception))
        }
    }

    private suspend fun loadLocalDashboardData(): Flow<Result<DashboardData>> = flow {
        try {
            // Load all data in parallel for better performance
            coroutineScope {
                val upcomingDutiesDeferred = async { loadUpcomingDutiesLocal() }
                val categorizedDutiesDeferred = async { loadCategorizedDutiesLocal() }

                val upcomingDuties = upcomingDutiesDeferred.await()
                val (personalDuties, companyDuties) = categorizedDutiesDeferred.await()

                // Load summaries after we have the duty counts
                val summariesDeferred = async { loadMonthlySummariesLocal() }
                val (personalSummary, companySummary) = summariesDeferred.await()

                emit(
                    Result.success(
                        DashboardData(
                            upcomingDuties = upcomingDuties,
                            personalDuties = personalDuties,
                            companyDuties = companyDuties,
                            personalSummary = personalSummary,
                            companySummary = companySummary
                        )
                    )
                )
            }
        } catch (exception: Exception) {
            Napier.e("Error loading local dashboard data: ${exception.message}", exception)
            emit(Result.failure(exception))
        }
    }

    private suspend fun loadUpcomingDutiesLocal(): List<Duty> {
        return getLocalDashboardData()
            .catch { exception ->
                Napier.e("Error loading upcoming duties: ${exception.message}")
                emit(Result.success(DashboardData()))
            }
            .first()
            .getOrNull()
            ?.upcomingDuties
            ?: emptyList()
    }

    private suspend fun loadCategorizedDutiesLocal(): Pair<List<DutyWithLastOccurrence>, List<DutyWithLastOccurrence>> {
        val allDuties = dutyRepository.getAllDuties()
            .catch { exception ->
                Napier.e("Error loading duties: ${exception.message}")
                emit(Result.success(emptyList()))
            }
            .first()
            .getOrElse { emptyList() }

        val personalDuties = allDuties
            .filter { it.categoryName == CategoryConstants.PERSONAL }
            .take(1)
            .map { duty -> enrichDutyWithLastOccurrence(duty) }

        val companyDuties = allDuties
            .filter { it.categoryName == CategoryConstants.COMPANY }
            .take(1)
            .map { duty -> enrichDutyWithLastOccurrence(duty) }

        return Pair(personalDuties, companyDuties)
    }

    private suspend fun enrichDutyWithLastOccurrence(duty: Duty): DutyWithLastOccurrence {
        val lastOccurrence = dutyOccurrenceRepository.getLastOccurrenceByDutyId(duty.id)
            .getOrNull()

        return DutyWithLastOccurrence(
            duty = duty,
            lastOccurrence = lastOccurrence,
            hasCurrentMonthOccurrence = false
        )
    }

    private suspend fun loadMonthlySummariesLocal(): Pair<MonthlySummary?, MonthlySummary?> = coroutineScope {
        try {
            val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            val currentMonth = currentDate.monthNumber
            val currentYear = currentDate.year

            val allDuties = loadAllDutiesForSummary()
            val dutyCounts = calculateDutyCounts(allDuties)

            val personalSummaryDeferred = async {
                loadCategorySummary(
                    categoryName = CategoryConstants.PERSONAL,
                    totalTasks = dutyCounts.personal,
                    currentMonth = currentMonth,
                    currentYear = currentYear
                )
            }

            val companySummaryDeferred = async {
                loadCategorySummary(
                    categoryName = CategoryConstants.COMPANY,
                    totalTasks = dutyCounts.company,
                    currentMonth = currentMonth,
                    currentYear = currentYear
                )
            }

            Pair(personalSummaryDeferred.await(), companySummaryDeferred.await())
        } catch (e: Exception) {
            Napier.e("Failed to load monthly summaries: ${e.message}")
            Pair(null, null)
        }
    }

    private suspend fun loadAllDutiesForSummary(): List<Duty> {
        return dutyRepository.getAllDuties()
            .catch { error ->
                Napier.e("Failed to load duties for summary: ${error.message}")
                emit(Result.success(emptyList()))
            }
            .first()
            .getOrElse {
                Napier.e("Failed to get duties result for summary")
                emptyList()
            }
    }

    private data class DutyCounts(
        val personal: Int,
        val company: Int
    )

    private fun calculateDutyCounts(duties: List<Duty>): DutyCounts {
        return DutyCounts(
            personal = duties.count { it.categoryName == CategoryConstants.PERSONAL },
            company = duties.count { it.categoryName == CategoryConstants.COMPANY }
        )
    }

    private suspend fun loadCategorySummary(
        categoryName: String,
        totalTasks: Int,
        currentMonth: Int,
        currentYear: Int
    ): MonthlySummary? {
        return try {
            dutyOccurrenceRepository.getMonthlyOccurrences(
                categoryName,
                currentMonth,
                currentYear
            ).fold(
                onSuccess = { occurrences ->
                    val totalAmountPaid = occurrences.sumOf { it.paidAmount ?: 0.0 }
                    val totalCompleted = occurrences.map { it.dutyId }.distinct().size

                    MonthlySummary(
                        totalAmountPaid = totalAmountPaid,
                        totalCompleted = totalCompleted,
                        totalTasks = totalTasks,
                        currentMonth = currentMonth,
                        year = currentYear
                    )
                },
                onFailure = { error ->
                    Napier.e("Failed to load $categoryName occurrences: ${error.message}")
                    null
                }
            )
        } catch (e: Exception) {
            Napier.e("Exception loading $categoryName summary: ${e.message}")
            null
        }
    }

    private suspend fun getLocalDashboardData(): Flow<Result<DashboardData>> {
        return localDataSource.getAllDutiesWithLastOccurrence()
            .map { result ->
                result.map { dutiesWithOccurrences ->
                    DashboardLocalMapper.toDomain(dutiesWithOccurrences)
                }
            }
    }
}
