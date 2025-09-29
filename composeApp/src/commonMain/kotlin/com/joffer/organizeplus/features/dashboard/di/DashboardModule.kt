package com.joffer.organizeplus.features.dashboard.di

import com.joffer.organizeplus.features.dashboard.data.repositories.RoomDutyRepository
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.dashboard.domain.usecases.GetDashboardDataUseCase
import com.joffer.organizeplus.features.dashboard.domain.usecases.MarkObligationPaidUseCase
import com.joffer.organizeplus.features.dashboard.domain.usecases.implementations.GetDashboardDataUseCaseImpl
import com.joffer.organizeplus.features.dashboard.domain.usecases.implementations.MarkObligationPaidUseCaseImpl
import com.joffer.organizeplus.features.dashboard.presentation.DashboardViewModel
import org.koin.dsl.module

val dashboardModule = module {
    single<DutyRepository> { RoomDutyRepository(get()) }
    
    single<GetDashboardDataUseCase> { GetDashboardDataUseCaseImpl(get()) }
    single<MarkObligationPaidUseCase> { MarkObligationPaidUseCaseImpl(get()) }
    
    single { DashboardViewModel(get(), get(), get()) }
}
