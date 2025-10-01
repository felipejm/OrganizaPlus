package com.joffer.organizeplus.features.duty.create.domain.usecases.implementations

import com.joffer.organizeplus.common.utils.currentTimeMillis
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyForm
import com.joffer.organizeplus.features.duty.create.domain.usecases.SaveCreateDutyUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.Clock

class SaveCreateDutyUseCaseImpl(
    private val repository: DutyRepository
) : SaveCreateDutyUseCase {

    override suspend operator fun invoke(form: CreateDutyForm): Flow<Result<Unit>> {
        return try {
            val duty = convertFormToDuty(form)
            if (form.id == null) {
                // Creating new duty
                repository.insertDuty(duty)
            } else {
                // Updating existing duty
                repository.updateDuty(duty)
            }
        } catch (e: Exception) {
            flowOf(Result.failure(e))
        }
    }

    private suspend fun convertFormToDuty(form: CreateDutyForm): Duty {
        val now = Clock.System.now()

        return Duty(
            id = form.id ?: generateId(),
            title = form.title,
            startDay = form.startDay,
            dueDay = form.dueDay,
            type = form.dutyType,
            categoryName = form.categoryName,
            createdAt = now
        )
    }

    private fun generateId(): String {
        return "obligation_${currentTimeMillis()}"
    }
}
