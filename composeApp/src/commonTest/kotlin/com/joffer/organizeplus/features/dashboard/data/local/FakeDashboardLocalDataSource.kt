package com.joffer.organizeplus.features.dashboard.data.local

import com.joffer.organizeplus.database.entities.DutyEntity
import com.joffer.organizeplus.database.entities.DutyOccurrenceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDashboardLocalDataSource : DashboardLocalDataSource {
    
    private val duties = mutableListOf<Pair<DutyEntity, DutyOccurrenceEntity?>>()
    var shouldThrowError = false
    var errorMessage = "Local data source error"

    override suspend fun getAllDutiesWithLastOccurrence(): Flow<Result<List<Pair<DutyEntity, DutyOccurrenceEntity?>>>> {
        return if (shouldThrowError) {
            flowOf(Result.failure(Exception(errorMessage)))
        } else {
            flowOf(Result.success(duties.toList()))
        }
    }

    fun setDuties(newDuties: List<Pair<DutyEntity, DutyOccurrenceEntity?>>) {
        duties.clear()
        duties.addAll(newDuties)
    }

    fun clear() {
        duties.clear()
        shouldThrowError = false
    }
}

