package com.joffer.organizeplus.features.duty.occurrence.data.repositories

import com.joffer.organizeplus.database.dao.DutyOccurrenceDao
import com.joffer.organizeplus.database.mappers.toDomainEntity
import com.joffer.organizeplus.database.mappers.toRoomEntity
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.duty.detail.domain.entities.ChartData
import com.joffer.organizeplus.features.duty.detail.domain.entities.MonthlyChartData
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrence
import com.joffer.organizeplus.features.duty.occurrence.domain.repositories.DutyOccurrenceRepository
import kotlinx.coroutines.flow.first

class RoomDutyOccurrenceRepository(
    private val dutyOccurrenceDao: DutyOccurrenceDao
) : DutyOccurrenceRepository {

    override suspend fun saveDutyOccurrence(dutyOccurrence: DutyOccurrence): Result<DutyOccurrence> {
        return try {
            val entity = dutyOccurrence.toRoomEntity()
            val id = dutyOccurrenceDao.insertDutyOccurrence(entity)
            val savedEntity = dutyOccurrence.copy(id = id.toString())
            Result.success(savedEntity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDutyOccurrencesByDutyId(dutyId: String): Result<List<DutyOccurrence>> {
        return try {
            val entities = dutyOccurrenceDao.getDutyOccurrencesByDutyId(dutyId.toLongOrNull() ?: 0L).first()
            val dutyOccurrences = entities.map { it.toDomainEntity() }
            Result.success(dutyOccurrences)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getLastOccurrenceByDutyId(dutyId: String): Result<DutyOccurrence?> {
        return try {
            val entity = dutyOccurrenceDao.getLastOccurrenceByDutyId(dutyId.toLongOrNull() ?: 0L)
            val dutyOccurrence = entity?.toDomainEntity()
            Result.success(dutyOccurrence)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMonthlyChartData(dutyId: String, dutyType: DutyType): Result<ChartData> {
        return try {
            val entities = dutyOccurrenceDao.getDutyOccurrencesByDutyId(dutyId.toLongOrNull() ?: 0L).first()
            val dutyOccurrences = entities.map { it.toDomainEntity() }

            // Group occurrences by month and year
            val monthlyData = dutyOccurrences
                .groupBy { occurrence ->
                    val localDate = occurrence.completedDate
                    "${localDate.year}-${localDate.monthNumber.toString().padStart(2, '0')}"
                }
                .map { (monthKey, occurrences) ->
                    val firstOccurrence = occurrences.first()
                    val localDate = firstOccurrence.completedDate

                    val value = when (dutyType) {
                        DutyType.ACTIONABLE -> occurrences.size.toFloat() // Count of occurrences
                        DutyType.PAYABLE -> occurrences.sumOf { it.paidAmount ?: 0.0 }.toFloat() // Sum of paid amounts
                    }

                    MonthlyChartData(
                        month = localDate.monthNumber,
                        year = localDate.year,
                        value = value,
                        dutyType = dutyType
                    )
                }
                .sortedWith(compareBy<MonthlyChartData> { it.year }.thenBy { it.month })

            Result.success(ChartData(monthlyData = monthlyData, dutyType = dutyType))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMonthlyOccurrences(
        categoryName: String,
        month: Int,
        year: Int
    ): Result<List<DutyOccurrence>> {
        return try {
            val monthStr = month.toString().padStart(2, '0')
            val yearStr = year.toString()
            val entities = dutyOccurrenceDao.getMonthlyOccurrencesByCategory(categoryName, monthStr, yearStr)
            val dutyOccurrences = entities.map { it.toDomainEntity() }
            Result.success(dutyOccurrences)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteDutyOccurrence(id: String): Result<Unit> {
        return try {
            dutyOccurrenceDao.deleteDutyOccurrenceById(
                id.toLongOrNull() ?: return Result.failure(IllegalArgumentException("Invalid ID"))
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
