package com.joffer.organizeplus.features.duty.occurrence.domain.repositories

import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrence

interface DutyOccurrenceRepository {
    suspend fun saveDutyOccurrence(dutyOccurrence: DutyOccurrence): Result<DutyOccurrence>
    suspend fun getDutyOccurrencesByDutyId(dutyId: String): Result<List<DutyOccurrence>>
    suspend fun deleteDutyOccurrence(id: String): Result<Unit>
}
