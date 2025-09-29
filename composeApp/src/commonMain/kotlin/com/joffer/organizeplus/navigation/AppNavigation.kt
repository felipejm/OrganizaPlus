package com.joffer.organizeplus.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joffer.organizeplus.features.dashboard.presentation.DashboardScreen
import com.joffer.organizeplus.features.dashboard.presentation.DashboardViewModel
import com.joffer.organizeplus.features.duty.create.presentation.CreateDutyScreen
import com.joffer.organizeplus.features.duty.create.presentation.CreateDutyViewModel
import com.joffer.organizeplus.features.duty.list.presentation.DutyListScreen
import com.joffer.organizeplus.features.duty.list.presentation.DutyListViewModel
import com.joffer.organizeplus.features.duty.detail.presentation.DutyDetailsListScreen
import com.joffer.organizeplus.features.duty.detail.presentation.DutyDetailsListViewModel
import com.joffer.organizeplus.features.settings.presentation.SettingsScreen
import com.joffer.organizeplus.features.settings.presentation.SettingsViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.navigation_create_duty
import organizeplus.composeapp.generated.resources.navigation_duties
import organizeplus.composeapp.generated.resources.navigation_edit_duty

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    onNavHostReady: suspend (NavController) -> Unit = {}
) {
    val dashboardViewModel: DashboardViewModel = koinInject()
    
    LaunchedEffect(navController) {
        onNavHostReady(navController)
    }
    
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.DASHBOARD
    ) {
        composable(NavigationRoutes.DASHBOARD) {
            DashboardScreen(
                viewModel = dashboardViewModel,
                onNavigateToCreateDuty = {
                    navController.navigate(NavigationRoutes.CREATE_DUTY)
                },
                onNavigateToDuties = {
                    navController.navigate(NavigationRoutes.DUTIES)
                },
                onNavigateToEditDuty = { dutyId ->
                    navController.navigate(NavigationRoutes.editDuty(dutyId))
                },
                onNavigateToSettings = {
                    navController.navigate(NavigationRoutes.SETTINGS)
                }
            )
        }
        
        composable(NavigationRoutes.CREATE_DUTY) {
            val createDutyViewModel: CreateDutyViewModel = koinInject()
            CreateDutyScreen(
                viewModel = createDutyViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(NavigationRoutes.DUTIES) {
            val dutyListViewModel: DutyListViewModel = koinInject()
            DutyListScreen(
                viewModel = dutyListViewModel,
                onNavigateToCreateDuty = {
                    navController.navigate(NavigationRoutes.CREATE_DUTY)
                },
                onNavigateToEditDuty = { dutyId ->
                    navController.navigate(NavigationRoutes.editDuty(dutyId))
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToOccurrences = { dutyId ->
                    navController.navigate(NavigationRoutes.dutyOccurrences(dutyId))
                }
            )
        }
        
        composable(NavigationRoutes.EDIT_DUTY) {
            val createDutyViewModel: CreateDutyViewModel = koinInject()
            CreateDutyScreen(
                viewModel = createDutyViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(NavigationRoutes.DUTY_OCCURRENCES) { backStackEntry ->
            val dutyId = backStackEntry.arguments?.getString("dutyId") ?: ""
            val dutyDetailsListViewModel: DutyDetailsListViewModel = koinInject { parametersOf(dutyId) }
            DutyDetailsListScreen(
                viewModel = dutyDetailsListViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(NavigationRoutes.SETTINGS) {
            val settingsViewModel: SettingsViewModel = koinInject()
            SettingsScreen(
                viewModel = settingsViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToDesignSystem = {
                    navController.navigate("design_system_catalog")
                }
            )
        }
        
        composable("design_system_catalog") {
            com.joffer.organizeplus.designsystem.catalog.DesignSystemCatalogScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToComponent = { route ->
                    navController.navigate(route)
                }
            )
        }
        
        // Design System Component Showcases
        composable("buttons") {
            com.joffer.organizeplus.designsystem.catalog.ButtonShowcaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("cards") {
            com.joffer.organizeplus.designsystem.catalog.CardShowcaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("forms") {
            com.joffer.organizeplus.designsystem.catalog.FormShowcaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("inputs") {
            com.joffer.organizeplus.designsystem.catalog.InputShowcaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("chips") {
            com.joffer.organizeplus.designsystem.catalog.ChipShowcaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("badges") {
            com.joffer.organizeplus.designsystem.catalog.BadgeShowcaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("simple_tags") {
            com.joffer.organizeplus.designsystem.catalog.SimpleTagShowcaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("banners") {
            com.joffer.organizeplus.designsystem.catalog.BannerShowcaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("layouts") {
            com.joffer.organizeplus.designsystem.catalog.LayoutShowcaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("colors") {
            com.joffer.organizeplus.designsystem.catalog.ColorShowcaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("typography") {
            com.joffer.organizeplus.designsystem.catalog.TypographyShowcaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
