package com.joffer.organizeplus.features.settings.di

import com.joffer.organizeplus.features.settings.presentation.SettingsViewModel
import org.koin.dsl.module

val settingsModule = module {
    factory { SettingsViewModel() }
}
