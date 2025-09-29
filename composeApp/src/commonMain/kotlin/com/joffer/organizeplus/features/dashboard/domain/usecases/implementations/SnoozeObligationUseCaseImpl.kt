package com.joffer.organizeplus.features.dashboard.domain.usecases.implementations

import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.dashboard.domain.usecases.SnoozeObligationUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus

class SnoozeObligationUseCaseImpl(
    private val repository: DutyRepository
) : SnoozeObligationUseCase {
    
    override suspend operator fun invoke(obligationId: String): Flow<Result<Unit>> {
        val now = Clock.System.now()
        val snoozeUntil = now
        return repository.snoozeDuty(obligationId, snoozeUntil)
    }
}
