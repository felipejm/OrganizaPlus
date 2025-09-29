package com.joffer.organizeplus.features.duty.create.domain.usecases

import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyForm
import kotlinx.coroutines.flow.Flow

interface SaveCreateDutyUseCase {
    suspend operator fun invoke(form: CreateDutyForm): Flow<Result<Unit>>
}
