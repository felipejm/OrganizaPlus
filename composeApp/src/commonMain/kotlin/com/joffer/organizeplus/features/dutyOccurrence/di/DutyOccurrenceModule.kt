package com.joffer.organizeplus.features.dutyOccurrence.di

import com.joffer.organizeplus.features.dutyOccurrence.data.repositories.FakeDutyOccurrenceRepository
import com.joffer.organizeplus.features.dutyOccurrence.domain.repositories.DutyOccurrenceRepository
import com.joffer.organizeplus.features.dutyOccurrence.domain.usecases.SaveDutyOccurrenceUseCase
import com.joffer.organizeplus.features.dutyOccurrence.domain.usecases.implementations.SaveDutyOccurrenceUseCaseImpl
import com.joffer.organizeplus.features.dutyOccurrence.presentation.AddDutyOccurrenceViewModel
import org.koin.dsl.module

val dutyOccurrenceModule = module {
    single<DutyOccurrenceRepository> { FakeDutyOccurrenceRepository() }
    single<SaveDutyOccurrenceUseCase> { SaveDutyOccurrenceUseCaseImpl(get()) }
    factory { (dutyId: String) -> AddDutyOccurrenceViewModel(get(), dutyId) }
}

