package com.joffer.organizeplus.features.dashboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.joffer.organizeplus.navigation.Dashboard
import com.joffer.organizeplus.navigation.MainNavigationContainer
import com.joffer.organizeplus.navigation.config.FeatureNavigationProvider
import com.joffer.organizeplus.navigation.config.MainNavigationProvider
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

class DashboardMainNavigationProvider : MainNavigationProvider {

    override fun NavGraphBuilder.registerMainScreens(navController: NavHostController) {
        composable<Dashboard> {
            val featureProviders: List<FeatureNavigationProvider> = koinInject(named("featureProviders"))
            MainNavigationContainer(
                mainNavController = navController,
                featureProviders = featureProviders
            )
        }
    }
}
