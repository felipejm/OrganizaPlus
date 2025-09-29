package com.joffer.organizeplus.features.duty.occurrence.di

import com.joffer.organizeplus.features.duty.occurrence.data.repositories.RoomDutyOccurrenceRepository
import com.joffer.organizeplus.features.duty.occurrence.domain.repositories.DutyOccurrenceRepository
import com.joffer.organizeplus.features.duty.occurrence.domain.usecases.SaveDutyOccurrenceUseCase
import com.joffer.organizeplus.features.duty.occurrence.domain.usecases.implementations.SaveDutyOccurrenceUseCaseImpl
import com.joffer.organizeplus.features.duty.occurrence.presentation.AddDutyOccurrenceViewModel
import org.koin.dsl.module

val dutyOccurrenceModule = module {
    single<DutyOccurrenceRepository> { RoomDutyOccurrenceRepository(get()) }
    single<SaveDutyOccurrenceUseCase> { SaveDutyOccurrenceUseCaseImpl(get()) }
    factory { (dutyId: String) -> AddDutyOccurrenceViewModel(get(), get(), dutyId) }
}

