package com.joffer.organizeplus.features.dutyOccurrence.domain.usecases.implementations

import com.joffer.organizeplus.features.dutyOccurrence.domain.entities.DutyOccurrence
import com.joffer.organizeplus.features.dutyOccurrence.domain.repositories.DutyOccurrenceRepository
import com.joffer.organizeplus.features.dutyOccurrence.domain.usecases.SaveDutyOccurrenceUseCase

class SaveDutyOccurrenceUseCaseImpl(
    private val repository: DutyOccurrenceRepository
) : SaveDutyOccurrenceUseCase {
    
    override suspend fun invoke(dutyOccurrence: DutyOccurrence): Result<DutyOccurrence> {
        return repository.saveDutyOccurrence(dutyOccurrence)
    }
}
