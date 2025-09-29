package com.joffer.organizeplus.features.dashboard.domain.usecases.implementations

import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.dashboard.domain.usecases.MarkObligationsPaidUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock

class MarkObligationsPaidUseCaseImpl(
    private val repository: DutyRepository
) : MarkObligationsPaidUseCase {
    
    override suspend operator fun invoke(obligationIds: List<String>): Flow<Result<Unit>> {
        val paidAt = Clock.System.now()
        return repository.markDutiesPaid(obligationIds, paidAt)
    }
}
