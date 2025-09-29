package com.joffer.organizeplus.features.dutyOccurrence.domain.usecases

import com.joffer.organizeplus.features.dutyOccurrence.domain.entities.DutyOccurrence

interface SaveDutyOccurrenceUseCase {
    suspend operator fun invoke(dutyOccurrence: DutyOccurrence): Result<DutyOccurrence>
}
