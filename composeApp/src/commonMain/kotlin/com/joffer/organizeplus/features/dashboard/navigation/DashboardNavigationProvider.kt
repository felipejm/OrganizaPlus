package com.joffer.organizeplus.features.dashboard.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.joffer.organizeplus.common.constants.CategoryConstants
import com.joffer.organizeplus.designsystem.icons.OrganizeIcons
import com.joffer.organizeplus.features.dashboard.presentation.DashboardScreen
import com.joffer.organizeplus.features.dashboard.presentation.DashboardViewModel
import com.joffer.organizeplus.navigation.CreateDuty
import com.joffer.organizeplus.navigation.Dashboard
import com.joffer.organizeplus.navigation.Duties
import com.joffer.organizeplus.navigation.DutyOccurrences
import com.joffer.organizeplus.navigation.Settings
import com.joffer.organizeplus.navigation.config.BottomNavConfiguration
import com.joffer.organizeplus.navigation.config.FeatureNavigationProvider
import org.koin.compose.koinInject
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.nav_home

class DashboardNavigationProvider : FeatureNavigationProvider {
    
    @Composable
    override fun getBottomNavConfiguration(): BottomNavConfiguration {
        return BottomNavConfiguration(
            route = Dashboard,
            labelRes = Res.string.nav_home,
            icon = OrganizeIcons.Navigation.Home,
            selectedIcon = OrganizeIcons.Navigation.HomeFilled,
            order = 0
        )
    }
    
    override fun NavGraphBuilder.registerScreens(
        mainNavController: NavHostController,
        tabNavController: NavHostController
    ) {
        composable<Dashboard> {
            val viewModel: DashboardViewModel = koinInject()
            
            DashboardScreen(
                viewModel = viewModel,
                onNavigateToPersonalDuties = {
                    tabNavController.navigate(Duties(CategoryConstants.PERSONAL)) {
                        popUpTo(Dashboard) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onNavigateToCompanyDuties = {
                    tabNavController.navigate(Duties(CategoryConstants.COMPANY)) {
                        popUpTo(Dashboard) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onNavigateToDutyDetails = { dutyId ->
                    mainNavController.navigate(DutyOccurrences(dutyId))
                },
                onNavigateToSettings = {
                    tabNavController.navigate(Settings)
                },
                onNavigateToCreateDuty = {
                    mainNavController.navigate(CreateDuty)
                }
            )
        }
    }
}

