package com.joffer.organizeplus.features.dashboard.di

import com.joffer.organizeplus.features.dashboard.data.repositories.RoomDutyRepository
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.dashboard.domain.usecases.GetDashboardDataUseCase
import com.joffer.organizeplus.features.dashboard.domain.usecases.implementations.GetDashboardDataUseCaseImpl
import com.joffer.organizeplus.features.dashboard.presentation.DashboardViewModel
import org.koin.dsl.module

val dashboardModule = module {
    single<DutyRepository> { RoomDutyRepository(get()) }
    
    single<GetDashboardDataUseCase> { GetDashboardDataUseCaseImpl(repository = get()) }

    single {
        DashboardViewModel(
            getDashboardDataUseCase = get(),
            dutyRepository = get(),
            dutyOccurrenceRepository = get()
        )
    }
}
