package com.joffer.organizeplus.features.dutyList.di

import com.joffer.organizeplus.features.dashboard.domain.usecases.DeleteDutyUseCase
import com.joffer.organizeplus.features.dashboard.domain.usecases.DeleteDutyUseCaseImpl
import com.joffer.organizeplus.features.dutyList.presentation.DutyListViewModel
import org.koin.dsl.module

val dutyListModule = module {
    single<DeleteDutyUseCase> { DeleteDutyUseCaseImpl(get<com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository>()) }
    factory { DutyListViewModel(get(), get<DeleteDutyUseCase>()) }
}
