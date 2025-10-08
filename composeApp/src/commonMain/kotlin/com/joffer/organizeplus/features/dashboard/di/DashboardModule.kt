package com.joffer.organizeplus.features.dashboard.di

import com.joffer.organizeplus.BuildConfig
import com.joffer.organizeplus.features.dashboard.data.local.DashboardLocalDataSource
import com.joffer.organizeplus.features.dashboard.data.local.DashboardLocalDataSourceImpl
import com.joffer.organizeplus.features.dashboard.data.remote.DashboardRemoteDataSource
import com.joffer.organizeplus.features.dashboard.data.remote.DashboardRemoteDataSourceImpl
import com.joffer.organizeplus.features.dashboard.data.repositories.DashboardRepositoryImpl
import com.joffer.organizeplus.features.dashboard.domain.repositories.DashboardRepository
import com.joffer.organizeplus.features.dashboard.domain.usecases.GetDashboardDataUseCase
import com.joffer.organizeplus.features.dashboard.domain.usecases.implementations.GetDashboardDataUseCaseImpl
import com.joffer.organizeplus.features.dashboard.presentation.DashboardViewModel
import org.koin.dsl.module

val dashboardModule = module {
    // Remote Data Source
    single<DashboardRemoteDataSource> {
        DashboardRemoteDataSourceImpl(
            httpClient = get(),
            baseUrl = BuildConfig.API_BASE_URL
        )
    }

    // Local Data Source
    single<DashboardLocalDataSource> {
        DashboardLocalDataSourceImpl(
            dutyDao = get(),
            dutyOccurrenceDao = get()
        )
    }

    // Repositories
    single<DashboardRepository> {
        DashboardRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get(),
            settingsRepository = get()
        )
    }

    // Use Cases
    single<GetDashboardDataUseCase> {
        GetDashboardDataUseCaseImpl(repository = get<DashboardRepository>())
    }

    // ViewModels
    single {
        DashboardViewModel(
            getDashboardDataUseCase = get(),
            dutyRepository = get(),
            dutyOccurrenceRepository = get()
        )
    }
}
