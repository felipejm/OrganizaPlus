package com.joffer.organizeplus.features.dutyOccurrence.domain.repositories

import com.joffer.organizeplus.features.dutyOccurrence.domain.entities.DutyOccurrence

interface DutyOccurrenceRepository {
    suspend fun saveDutyOccurrence(dutyOccurrence: DutyOccurrence): Result<DutyOccurrence>
    suspend fun getDutyOccurrencesByDutyId(dutyId: String): Result<List<DutyOccurrence>>
    suspend fun deleteDutyOccurrence(id: String): Result<Unit>
}
