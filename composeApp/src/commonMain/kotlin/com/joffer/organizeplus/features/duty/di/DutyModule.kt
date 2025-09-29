package com.joffer.organizeplus.features.duty.di

import com.joffer.organizeplus.features.duty.domain.usecases.SaveDutyUseCase
import com.joffer.organizeplus.features.duty.domain.usecases.implementations.SaveDutyUseCaseImpl
import com.joffer.organizeplus.features.duty.presentation.DutyViewModel
import org.koin.dsl.module

val dutyModule = module {
    single<SaveDutyUseCase> { SaveDutyUseCaseImpl(get<com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository>()) }
    factory { DutyViewModel(get()) }
}