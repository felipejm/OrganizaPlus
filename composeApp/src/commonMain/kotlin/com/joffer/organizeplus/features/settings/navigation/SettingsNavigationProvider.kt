package com.joffer.organizeplus.features.settings.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.joffer.organizeplus.designsystem.icons.OrganizeIcons
import com.joffer.organizeplus.features.settings.presentation.SettingsScreen
import com.joffer.organizeplus.features.settings.presentation.SettingsViewModel
import com.joffer.organizeplus.navigation.Dashboard
import com.joffer.organizeplus.navigation.DesignSystemCatalog
import com.joffer.organizeplus.navigation.Settings
import com.joffer.organizeplus.navigation.SignIn
import com.joffer.organizeplus.navigation.config.BottomNavConfiguration
import com.joffer.organizeplus.navigation.config.FeatureNavigationProvider
import org.koin.compose.koinInject
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.nav_profile

class SettingsNavigationProvider : FeatureNavigationProvider {
    
    @Composable
    override fun getBottomNavConfiguration(): BottomNavConfiguration {
        return BottomNavConfiguration(
            route = Settings,
            labelRes = Res.string.nav_profile,
            icon = OrganizeIcons.Navigation.UserCircle,
            selectedIcon = OrganizeIcons.Navigation.UserCircleFilled,
            order = 3
        )
    }
    
    override fun NavGraphBuilder.registerScreens(
        mainNavController: NavHostController,
        tabNavController: NavHostController
    ) {
        composable<Settings> {
            val viewModel: SettingsViewModel = koinInject()
            
            SettingsScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    tabNavController.navigate(Dashboard)
                },
                onNavigateToDesignSystem = {
                    mainNavController.navigate(DesignSystemCatalog)
                },
                onNavigateToSignIn = {
                    mainNavController.navigate(SignIn) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}

