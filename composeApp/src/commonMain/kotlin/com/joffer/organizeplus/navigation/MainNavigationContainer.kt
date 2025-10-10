package com.joffer.organizeplus.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.joffer.organizeplus.common.constants.CategoryConstants
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.OrganizeBottomNavigationBar
import com.joffer.organizeplus.designsystem.components.BottomNavItem
import com.joffer.organizeplus.features.dashboard.presentation.DashboardScreen
import com.joffer.organizeplus.features.dashboard.presentation.DashboardViewModel
import com.joffer.organizeplus.features.duty.review.presentation.DutyReviewScreen
import com.joffer.organizeplus.features.duty.review.presentation.DutyReviewViewModel
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

    // Define bottom navigation items
    val bottomNavItems = listOf(
        BottomNavItem(
            label = stringResource(Res.string.nav_home),
            icon = Icons.Outlined.Home,
            selectedIcon = Icons.Filled.Home,
            route = Dashboard
        ),
        BottomNavItem(
            label = stringResource(Res.string.nav_personal_review),
            icon = Icons.Outlined.Person,
            selectedIcon = Icons.Filled.Person,
            route = PersonalReview
        ),
        BottomNavItem(
            label = stringResource(Res.string.nav_company_review),
            icon = Icons.Outlined.Business,
            selectedIcon = Icons.Filled.Business,
            route = CompanyReview
        ),
        BottomNavItem(
            label = stringResource(Res.string.nav_profile),
            icon = Icons.Outlined.AccountCircle,
            selectedIcon = Icons.Filled.AccountCircle,
            route = Settings
        )
    )

    // Determine current route for bottom nav selection
    val currentRoute = when {
        currentDestination?.hasRoute(Dashboard::class) == true -> Dashboard
        currentDestination?.hasRoute(PersonalReview::class) == true -> PersonalReview
        currentDestination?.hasRoute(CompanyReview::class) == true -> CompanyReview
        currentDestination?.hasRoute(Settings::class) == true -> Settings
        else -> Dashboard
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = SemanticColors.Background.primary,
        bottomBar = {
            OrganizeBottomNavigationBar(
                items = bottomNavItems,
                currentRoute = currentRoute,
                onItemClick = { item ->
                    navController.navigate(item.route) {
                        // Pop up to the start destination to avoid building up a large stack
                        popUpTo(Dashboard) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
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
                        mainNavController.navigate(Duties(CategoryConstants.PERSONAL))
                    },
                    onNavigateToCompanyDuties = {
                        mainNavController.navigate(Duties(CategoryConstants.COMPANY))
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

            composable<PersonalReview> {
                val dutyReviewViewModel: DutyReviewViewModel = koinInject {
                    parametersOf(CategoryConstants.PERSONAL)
                }
                DutyReviewScreen(
                    viewModel = dutyReviewViewModel,
                    onNavigateBack = {
                        navController.navigate(Dashboard)
                    }
                )
            }

            composable<CompanyReview> {
                val dutyReviewViewModel: DutyReviewViewModel = koinInject {
                    parametersOf(CategoryConstants.COMPANY)
                }
                DutyReviewScreen(
                    viewModel = dutyReviewViewModel,
                    onNavigateBack = {
                        navController.navigate(Dashboard)
                    }
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

