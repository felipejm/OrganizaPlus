package com.joffer.organizeplus.di

import com.joffer.organizeplus.core.di.platformSecureStorageModule
import com.joffer.organizeplus.features.dashboard.di.dashboardModule
import com.joffer.organizeplus.features.duty.di.dutyModule
import com.joffer.organizeplus.features.duty.review.di.dutyReviewModule
import com.joffer.organizeplus.features.onboarding.di.onboardingModule
import com.joffer.organizeplus.features.settings.di.settingsModule
import com.joffer.organizeplus.navigation.config.navigationModule
import org.koin.dsl.module

val appModule = module {
    // Core modules
    includes(networkModule)
    includes(databaseModule)
    includes(platformSecureStorageModule)

    // Navigation
    includes(navigationModule)

    // Feature modules
    includes(onboardingModule)
    includes(settingsModule)
    includes(dashboardModule)
    includes(dutyModule)
    includes(dutyReviewModule)
}
