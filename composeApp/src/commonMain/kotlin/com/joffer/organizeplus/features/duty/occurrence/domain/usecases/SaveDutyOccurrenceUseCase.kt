package com.joffer.organizeplus.features.duty.occurrence.domain.usecases

import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrence

interface SaveDutyOccurrenceUseCase {
    suspend operator fun invoke(dutyOccurrence: DutyOccurrence): Result<DutyOccurrence>
}
