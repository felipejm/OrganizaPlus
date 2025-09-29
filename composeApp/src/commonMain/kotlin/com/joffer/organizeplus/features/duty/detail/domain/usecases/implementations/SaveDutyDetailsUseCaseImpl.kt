package com.joffer.organizeplus.features.duty.detail.domain.usecases.implementations

import com.joffer.organizeplus.features.duty.detail.domain.entities.DutyDetails
import com.joffer.organizeplus.features.duty.detail.domain.entities.DutyDetailsForm
import com.joffer.organizeplus.features.duty.detail.domain.repositories.DutyDetailsRepository
import com.joffer.organizeplus.features.duty.detail.domain.usecases.SaveDutyDetailsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock

class SaveDutyDetailsUseCaseImpl(
    private val repository: DutyDetailsRepository
) : SaveDutyDetailsUseCase {
    override operator fun invoke(form: DutyDetailsForm): Flow<Result<Unit>> {
        val record = DutyDetails(
            id = Clock.System.now().toEpochMilliseconds().toString(),
            dutyId = form.dutyId,
            paidAmount = form.paidAmount,
            paidDate = form.paidDate,
            notes = form.notes,
            createdAt = Clock.System.now(),
            updatedAt = Clock.System.now()
        )
        return runBlocking { repository.insertRecord(record) }
    }
}
