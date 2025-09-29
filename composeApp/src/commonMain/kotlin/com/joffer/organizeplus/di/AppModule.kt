package com.joffer.organizeplus.di

import com.joffer.organizeplus.features.dashboard.di.dashboardModule
import com.joffer.organizeplus.features.duty.di.dutyModule
import com.joffer.organizeplus.features.dutyList.di.dutyListModule
import com.joffer.organizeplus.features.dutyDetails.di.dutyDetailsModule
import com.joffer.organizeplus.features.dutyOccurrence.di.dutyOccurrenceModule
import com.joffer.organizeplus.features.settings.di.settingsModule
import org.koin.dsl.module

val appModule = module {
    // Database module
    includes(databaseModule)
    
    // Feature modules
    includes(dashboardModule)
    includes(dutyModule)
    includes(dutyListModule)
    includes(dutyDetailsModule)
    includes(dutyOccurrenceModule)
    includes(settingsModule)
}
