package com.joffer.organizeplus.features.duty.occurrence.data

import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.duty.detail.domain.entities.ChartData
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrence
import com.joffer.organizeplus.features.duty.occurrence.domain.repositories.DutyOccurrenceRepository

class FakeDutyOccurrenceRepository : DutyOccurrenceRepository {

    private val occurrences = mutableListOf<DutyOccurrence>()
    var saveDutyOccurrenceCallCount = 0
    var deleteDutyOccurrenceCallCount = 0

    override suspend fun saveDutyOccurrence(dutyOccurrence: DutyOccurrence): Result<DutyOccurrence> {
        saveDutyOccurrenceCallCount++
        occurrences.add(dutyOccurrence)
        return Result.success(dutyOccurrence)
    }

    override suspend fun getDutyOccurrencesByDutyId(dutyId: Long): Result<List<DutyOccurrence>> {
        val filtered = occurrences.filter { it.dutyId == dutyId }
        return Result.success(filtered)
    }

    override suspend fun getLastOccurrenceByDutyId(dutyId: Long): Result<DutyOccurrence?> {
        val last = occurrences.filter { it.dutyId == dutyId }.maxByOrNull { it.completedDate }
        return Result.success(last)
    }

    override suspend fun getMonthlyChartData(
        dutyId: Long,
        dutyType: DutyType
    ): Result<ChartData> {
        return Result.success(
            ChartData(
                monthlyData = emptyList(),
                dutyType = dutyType
            )
        )
    }

    override suspend fun getMonthlyOccurrences(
        categoryName: String,
        month: Int,
        year: Int
    ): Result<List<DutyOccurrence>> {
        return Result.success(emptyList())
    }

    override suspend fun deleteDutyOccurrence(id: Long): Result<Unit> {
        deleteDutyOccurrenceCallCount++
        occurrences.removeAll { it.id == id }
        return Result.success(Unit)
    }

    fun clear() {
        occurrences.clear()
        saveDutyOccurrenceCallCount = 0
        deleteDutyOccurrenceCallCount = 0
    }

    fun reset() = clear()
}

