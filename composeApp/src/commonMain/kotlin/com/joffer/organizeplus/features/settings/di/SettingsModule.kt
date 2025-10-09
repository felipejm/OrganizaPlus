package com.joffer.organizeplus.features.settings.di

import com.joffer.organizeplus.features.settings.data.repositories.SettingsRepositoryImpl
import com.joffer.organizeplus.features.settings.domain.repositories.SettingsRepository
import com.joffer.organizeplus.features.settings.presentation.SettingsViewModel
import org.koin.dsl.module

val settingsModule = module {
    single<SettingsRepository> {
        SettingsRepositoryImpl(secureStorage = get())
    }
    single {
        SettingsViewModel(
            settingsRepository = get(),
            authRepository = get()
        )
    }
}
