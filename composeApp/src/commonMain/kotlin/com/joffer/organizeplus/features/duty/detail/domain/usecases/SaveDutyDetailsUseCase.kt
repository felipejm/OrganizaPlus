package com.joffer.organizeplus.features.duty.detail.domain.usecases

import com.joffer.organizeplus.features.duty.detail.domain.entities.DutyDetails
import com.joffer.organizeplus.features.duty.detail.domain.entities.DutyDetailsForm
import com.joffer.organizeplus.features.duty.detail.domain.repositories.DutyDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock

interface SaveDutyDetailsUseCase {
    operator fun invoke(form: DutyDetailsForm): Flow<Result<Unit>>
}

class SaveDutyDetailsUseCaseImpl(
    private val repository: DutyDetailsRepository
) : SaveDutyDetailsUseCase {

    override fun invoke(form: DutyDetailsForm): Flow<Result<Unit>> = flow {
        val record = convertFormToRecord(form)
        repository.insertRecord(record)
            .catch { exception ->
                emit(Result.failure(exception))
            }
            .collect { result ->
                emit(result)
            }
    }

    private fun convertFormToRecord(form: DutyDetailsForm): DutyDetails {
        val now = Clock.System.now()
        return DutyDetails(
            id = form.id ?: generateId(),
            dutyId = form.dutyId,
            paidAmount = form.paidAmount,
            paidDate = form.paidDate,
            notes = form.notes.takeIf { it.isNotBlank() },
            createdAt = now,
            updatedAt = now
        )
    }

    private fun generateId(): String {
        return "record_${System.currentTimeMillis()}"
    }
}
