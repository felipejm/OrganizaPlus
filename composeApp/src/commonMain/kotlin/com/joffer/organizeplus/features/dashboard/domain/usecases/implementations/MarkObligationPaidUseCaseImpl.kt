package com.joffer.organizeplus.features.dashboard.domain.usecases.implementations

import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.dashboard.domain.usecases.MarkObligationPaidUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock

class MarkObligationPaidUseCaseImpl(
    private val repository: DutyRepository
) : MarkObligationPaidUseCase {

    override suspend operator fun invoke(obligationId: String): Flow<Result<Unit>> {
        val paidAt = Clock.System.now()
        return repository.markDutyPaid(obligationId, paidAt)
    }
}
