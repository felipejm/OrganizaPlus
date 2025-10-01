package com.joffer.organizeplus.features.dashboard.domain.usecases

import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository

interface DeleteDutyUseCase {
    suspend operator fun invoke(dutyId: String): Result<Unit>
}

class DeleteDutyUseCaseImpl(
    private val repository: DutyRepository
) : DeleteDutyUseCase {
    override suspend fun invoke(dutyId: String): Result<Unit> {
        return try {
            repository.deleteDuty(dutyId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
