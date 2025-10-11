package com.joffer.organizeplus.navigation.config

import com.joffer.organizeplus.designsystem.navigation.DesignSystemNavigationProvider
import com.joffer.organizeplus.features.dashboard.navigation.DashboardMainNavigationProvider
import com.joffer.organizeplus.features.dashboard.navigation.DashboardNavigationProvider
import com.joffer.organizeplus.features.duty.navigation.CompanyDutyNavigationProvider
import com.joffer.organizeplus.features.duty.navigation.DutyMainNavigationProvider
import com.joffer.organizeplus.features.duty.navigation.PersonalDutyNavigationProvider
import com.joffer.organizeplus.features.onboarding.navigation.OnboardingNavigationProvider
import com.joffer.organizeplus.features.settings.navigation.SettingsNavigationProvider
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Provides the list of feature navigation providers.
 * This centralizes feature registration and makes it easy to add/remove features.
 */
val navigationModule = module {
    // Feature navigation providers for bottom nav tabs
    single(qualifier = named("featureProviders")) {
        listOf<FeatureNavigationProvider>(
            DashboardNavigationProvider(),
            PersonalDutyNavigationProvider(),
            CompanyDutyNavigationProvider(),
            SettingsNavigationProvider()
        )
    }
    
    // Main navigation providers for app-level screens
    single(qualifier = named("mainProviders")) {
        listOf<MainNavigationProvider>(
            OnboardingNavigationProvider(),
            DashboardMainNavigationProvider(),
            DutyMainNavigationProvider(),
            DesignSystemNavigationProvider()
        )
    }
}

