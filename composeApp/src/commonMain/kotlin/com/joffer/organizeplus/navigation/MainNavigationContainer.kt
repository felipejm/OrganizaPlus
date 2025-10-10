package com.joffer.organizeplus.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.joffer.organizeplus.common.constants.CategoryConstants
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.OrganizeBottomNavigationBar
import com.joffer.organizeplus.designsystem.components.BottomNavItem
import com.joffer.organizeplus.designsystem.icons.OrganizeIcons
import com.joffer.organizeplus.features.dashboard.presentation.DashboardScreen
import com.joffer.organizeplus.features.dashboard.presentation.DashboardViewModel
import com.joffer.organizeplus.features.duty.list.domain.DutyCategoryFilter
import com.joffer.organizeplus.features.duty.list.presentation.DutyListScreen
import com.joffer.organizeplus.features.duty.list.presentation.DutyListViewModel
import com.joffer.organizeplus.features.settings.presentation.SettingsScreen
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.nav_company_review
import organizeplus.composeapp.generated.resources.nav_home
import organizeplus.composeapp.generated.resources.nav_personal_review
import organizeplus.composeapp.generated.resources.nav_profile

@Composable
fun MainNavigationContainer(
    mainNavController: NavController,
    modifier: Modifier = Modifier
) {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Define routes for bottom nav tabs
    val personalDutiesRoute = Duties(CategoryConstants.PERSONAL)
    val companyDutiesRoute = Duties(CategoryConstants.COMPANY)
    
    // Define bottom navigation items with custom icons
    val bottomNavItems = listOf(
        BottomNavItem(
            label = stringResource(Res.string.nav_home),
            icon = OrganizeIcons.Navigation.Home,
            selectedIcon = OrganizeIcons.Navigation.HomeFilled,
            route = Dashboard
        ),
        BottomNavItem(
            label = stringResource(Res.string.nav_personal_review),
            icon = OrganizeIcons.Navigation.User,
            selectedIcon = OrganizeIcons.Navigation.UserFilled,
            route = personalDutiesRoute
        ),
        BottomNavItem(
            label = stringResource(Res.string.nav_company_review),
            icon = OrganizeIcons.Navigation.Building,
            selectedIcon = OrganizeIcons.Navigation.BuildingFilled,
            route = companyDutiesRoute
        ),
        BottomNavItem(
            label = stringResource(Res.string.nav_profile),
            icon = OrganizeIcons.Navigation.UserCircle,
            selectedIcon = OrganizeIcons.Navigation.UserCircleFilled,
            route = Settings
        )
    )

    // Determine current route for bottom nav selection
    val currentRoute = when {
        currentDestination?.hasRoute(Dashboard::class) == true -> Dashboard
        currentDestination?.hasRoute(Duties::class) == true -> {
            val currentDuties = navBackStackEntry?.toRoute<Duties>()
            when (currentDuties?.category) {
                CategoryConstants.PERSONAL -> personalDutiesRoute
                CategoryConstants.COMPANY -> companyDutiesRoute
                else -> Dashboard
            }
        }
        currentDestination?.hasRoute(Settings::class) == true -> Settings
        else -> Dashboard
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = SemanticColors.Background.surface,
        bottomBar = {
            OrganizeBottomNavigationBar(
                items = bottomNavItems,
                currentRoute = currentRoute,
                onItemClick = { item ->
                    navController.navigate(item.route) {
                        // Pop up to the start destination to avoid building up a large stack
                        popUpTo(Dashboard) {
                            saveState = false
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Dashboard,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<Dashboard> {
                val dashboardViewModel: DashboardViewModel = koinInject()
                DashboardScreen(
                    viewModel = dashboardViewModel,
                    onNavigateToPersonalDuties = {
                        // Switch to Personal Duties tab
                        navController.navigate(Duties(CategoryConstants.PERSONAL)) {
                            popUpTo(Dashboard) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onNavigateToCompanyDuties = {
                        // Switch to Company Duties tab
                        navController.navigate(Duties(CategoryConstants.COMPANY)) {
                            popUpTo(Dashboard) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onNavigateToDutyDetails = { dutyId ->
                        mainNavController.navigate(DutyOccurrences(dutyId))
                    },
                    onNavigateToSettings = {
                        navController.navigate(Settings)
                    },
                    onNavigateToCreateDuty = {
                        mainNavController.navigate(CreateDuty)
                    }
                )
            }

            composable<Duties> { backStackEntry ->
                val duties = backStackEntry.toRoute<Duties>()
                val categoryFilter = when (duties.category) {
                    CategoryConstants.PERSONAL -> DutyCategoryFilter.Personal
                    CategoryConstants.COMPANY -> DutyCategoryFilter.Company
                    else -> DutyCategoryFilter.Personal // Default fallback
                }
                val dutyListViewModel: DutyListViewModel = koinInject { parametersOf(categoryFilter) }
                
                DutyListScreen(
                    viewModel = dutyListViewModel,
                    categoryFilter = categoryFilter,
                    onNavigateToCreateDuty = {
                        mainNavController.navigate(CreateDutyWithCategory(duties.category))
                    },
                    onNavigateToOccurrences = { dutyId ->
                        mainNavController.navigate(DutyOccurrences(dutyId))
                    },
                )
            }

            composable<Settings> {
                val settingsViewModel: com.joffer.organizeplus.features.settings.presentation.SettingsViewModel = koinInject()
                SettingsScreen(
                    viewModel = settingsViewModel,
                    onNavigateBack = {
                        navController.navigate(Dashboard)
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
}

