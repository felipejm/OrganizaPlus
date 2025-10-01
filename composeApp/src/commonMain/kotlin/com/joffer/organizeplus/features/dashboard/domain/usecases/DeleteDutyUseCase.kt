package com.joffer.organizeplus.features.dashboard.domain.usecases

import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import kotlinx.coroutines.flow.collect

interface DeleteDutyUseCase {
    suspend operator fun invoke(dutyId: Long): Result<Unit>
}

class DeleteDutyUseCaseImpl(
    private val repository: DutyRepository
) : DeleteDutyUseCase {
    override suspend fun invoke(dutyId: Long): Result<Unit> {
        return try {
            repository.deleteDuty(dutyId).collect()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
