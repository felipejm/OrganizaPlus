package com.joffer.organizeplus.features.dutyOccurrence.di

import com.joffer.organizeplus.features.dutyOccurrence.data.repositories.RoomDutyOccurrenceRepository
import com.joffer.organizeplus.features.dutyOccurrence.domain.repositories.DutyOccurrenceRepository
import com.joffer.organizeplus.features.dutyOccurrence.domain.usecases.SaveDutyOccurrenceUseCase
import com.joffer.organizeplus.features.dutyOccurrence.domain.usecases.implementations.SaveDutyOccurrenceUseCaseImpl
import com.joffer.organizeplus.features.dutyOccurrence.presentation.AddDutyOccurrenceViewModel
import org.koin.dsl.module

val dutyOccurrenceModule = module {
    single<DutyOccurrenceRepository> { RoomDutyOccurrenceRepository(get()) }
    single<SaveDutyOccurrenceUseCase> { SaveDutyOccurrenceUseCaseImpl(get()) }
    factory { (dutyId: String) -> AddDutyOccurrenceViewModel(get(), dutyId) }
}

