package com.joffer.organizeplus.features.dashboard.domain.usecases

import kotlinx.coroutines.flow.Flow

interface MarkObligationsPaidUseCase {
    suspend operator fun invoke(obligationIds: List<String>): Flow<Result<Unit>>
}

