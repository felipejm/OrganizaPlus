package com.joffer.organizeplus.features.duty.di

import com.joffer.organizeplus.BuildConfig
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.duty.create.di.createDutyModule
import com.joffer.organizeplus.features.duty.data.remote.DutyRemoteDataSource
import com.joffer.organizeplus.features.duty.data.remote.DutyRemoteDataSourceImpl
import com.joffer.organizeplus.features.duty.data.repositories.DutyRepositoryImpl
import com.joffer.organizeplus.features.duty.detail.di.dutyDetailsModule
import com.joffer.organizeplus.features.duty.list.di.dutyListModule
import com.joffer.organizeplus.features.duty.occurrence.di.dutyOccurrenceModule
import org.koin.dsl.module

val dutyModule = module {
    // Remote Data Source
    single<DutyRemoteDataSource> {
        DutyRemoteDataSourceImpl(
            httpClient = get(),
            baseUrl = BuildConfig.API_BASE_URL
        )
    }

    // Repository
    single<DutyRepository> {
        DutyRepositoryImpl(
            remoteDataSource = get(),
            dutyDao = get(),
            settingsRepository = get()
        )
    }

    // Sub-modules
    includes(createDutyModule)
    includes(dutyListModule)
    includes(dutyDetailsModule)
    includes(dutyOccurrenceModule)
}
