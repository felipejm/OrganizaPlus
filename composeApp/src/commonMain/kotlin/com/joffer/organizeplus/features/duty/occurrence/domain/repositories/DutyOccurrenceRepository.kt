package com.joffer.organizeplus.features.duty.occurrence.domain.repositories

import com.joffer.organizeplus.features.duty.detail.domain.entities.ChartData
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrence

interface DutyOccurrenceRepository {
    suspend fun saveDutyOccurrence(dutyOccurrence: DutyOccurrence): Result<DutyOccurrence>
    suspend fun getDutyOccurrencesByDutyId(dutyId: String): Result<List<DutyOccurrence>>
    suspend fun getLastOccurrenceByDutyId(dutyId: String): Result<DutyOccurrence?>
    suspend fun getMonthlyChartData(
        dutyId: String,
        dutyType: com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
    ): Result<ChartData>
    suspend fun getMonthlyOccurrences(categoryName: String, month: Int, year: Int): Result<List<DutyOccurrence>>
    suspend fun deleteDutyOccurrence(id: String): Result<Unit>
}
