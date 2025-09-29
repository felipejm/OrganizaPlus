package com.joffer.organizeplus.features.dashboard.di

import com.joffer.organizeplus.features.dashboard.data.repositories.FakeDutyRepository
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.dashboard.domain.usecases.GetDashboardDataUseCase
import com.joffer.organizeplus.features.dashboard.domain.usecases.MarkObligationPaidUseCase
import com.joffer.organizeplus.features.dashboard.domain.usecases.MarkObligationsPaidUseCase
import com.joffer.organizeplus.features.dashboard.domain.usecases.SnoozeObligationUseCase
import com.joffer.organizeplus.features.dashboard.domain.usecases.implementations.GetDashboardDataUseCaseImpl
import com.joffer.organizeplus.features.dashboard.domain.usecases.implementations.MarkObligationPaidUseCaseImpl
import com.joffer.organizeplus.features.dashboard.domain.usecases.implementations.MarkObligationsPaidUseCaseImpl
import com.joffer.organizeplus.features.dashboard.domain.usecases.implementations.SnoozeObligationUseCaseImpl
import com.joffer.organizeplus.features.dashboard.presentation.DashboardViewModel
import org.koin.dsl.module

val dashboardModule = module {
    single<DutyRepository> { FakeDutyRepository() }
    
    single<GetDashboardDataUseCase> { GetDashboardDataUseCaseImpl(get()) }
    single<MarkObligationPaidUseCase> { MarkObligationPaidUseCaseImpl(get()) }
    single<MarkObligationsPaidUseCase> { MarkObligationsPaidUseCaseImpl(get()) }
    single<SnoozeObligationUseCase> { SnoozeObligationUseCaseImpl(get()) }
    
    single { DashboardViewModel(get(), get(), get(), get()) }
}
