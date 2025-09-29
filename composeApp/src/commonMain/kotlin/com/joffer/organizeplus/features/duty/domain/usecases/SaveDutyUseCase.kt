package com.joffer.organizeplus.features.duty.domain.usecases

import com.joffer.organizeplus.features.duty.domain.entities.DutyForm
import kotlinx.coroutines.flow.Flow

interface SaveDutyUseCase {
    suspend operator fun invoke(form: DutyForm): Flow<Result<Unit>>
}
