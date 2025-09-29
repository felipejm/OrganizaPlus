package com.joffer.organizeplus.features.createDuty.di

import com.joffer.organizeplus.features.createDuty.domain.usecases.SaveCreateDutyUseCase
import com.joffer.organizeplus.features.createDuty.domain.usecases.implementations.SaveCreateDutyUseCaseImpl
import com.joffer.organizeplus.features.createDuty.presentation.CreateDutyViewModel
import org.koin.dsl.module

val createDutyModule = module {
    single<SaveCreateDutyUseCase> { SaveCreateDutyUseCaseImpl(get<com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository>()) }
    factory { CreateDutyViewModel(get()) }
}
