package com.joffer.organizeplus.features.dashboard.domain.usecases

import kotlinx.coroutines.flow.Flow

interface SnoozeObligationUseCase {
    suspend operator fun invoke(obligationId: String): Flow<Result<Unit>>
}

