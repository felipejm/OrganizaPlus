package com.joffer.organizeplus.di

import com.joffer.organizeplus.features.dashboard.di.dashboardModule
import com.joffer.organizeplus.features.duty.di.dutyModule
import com.joffer.organizeplus.features.settings.di.settingsModule
import org.koin.dsl.module

val appModule = module {
    // Database module
    includes(databaseModule)

    // Feature modules
    includes(dashboardModule)
    includes(dutyModule)
    includes(settingsModule)
}
