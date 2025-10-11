package com.joffer.organizeplus.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.BottomNavItem
import com.joffer.organizeplus.designsystem.components.OrganizeBottomNavigationBar
import com.joffer.organizeplus.navigation.config.FeatureNavigationProvider
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainNavigationContainer(
    mainNavController: NavController,
    featureProviders: List<FeatureNavigationProvider>,
    modifier: Modifier = Modifier
) {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomNavItems = rememberBottomNavItems(featureProviders)

    val currentRoute = getCurrentRoute(
        currentDestination = currentDestination,
        navBackStackEntry = navBackStackEntry,
        bottomNavItems = bottomNavItems
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = SemanticColors.Background.surface,
        bottomBar = {
            OrganizeBottomNavigationBar(
                items = bottomNavItems,
                currentRoute = currentRoute,
                onItemClick = { item ->
                    navigateToBottomNavItem(
                        navController = navController,
                        route = item.route
                    )
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Dashboard,
            modifier = Modifier.padding(paddingValues),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            registerFeatureScreens(
                featureProviders = featureProviders,
                mainNavController = mainNavController,
                tabNavController = navController
            )
        }
    }
}

@Composable
private fun rememberBottomNavItems(
    featureProviders: List<FeatureNavigationProvider>
): List<BottomNavItem> {
    val configurations = featureProviders.mapNotNull { provider ->
        provider.getBottomNavConfiguration()
    }.sortedBy { it.order }

    return configurations.map { config ->
        val label = stringResource(config.labelRes)
        remember(config, label) {
            BottomNavItem(
                label = label,
                icon = config.icon,
                selectedIcon = config.selectedIcon,
                route = config.route
            )
        }
    }
}

@Composable
private fun getCurrentRoute(
    currentDestination: androidx.navigation.NavDestination?,
    navBackStackEntry: androidx.navigation.NavBackStackEntry?,
    bottomNavItems: List<BottomNavItem>
): Any {
    return when {
        currentDestination?.hasRoute(Dashboard::class) == true -> Dashboard
        currentDestination?.hasRoute(Duties::class) == true -> {
            val currentDuties = navBackStackEntry?.toRoute<Duties>()
            bottomNavItems.find { item ->
                (item.route as? Duties)?.category == currentDuties?.category
            }?.route ?: Dashboard
        }
        currentDestination?.hasRoute(Settings::class) == true -> Settings
        else -> Dashboard
    }
}

private fun navigateToBottomNavItem(
    navController: NavController,
    route: Any
) {
    navController.navigate(route) {
        popUpTo(Dashboard) {
            saveState = false
        }
        launchSingleTop = true
        restoreState = false
    }
}

private fun androidx.navigation.NavGraphBuilder.registerFeatureScreens(
    featureProviders: List<FeatureNavigationProvider>,
    mainNavController: NavController,
    tabNavController: NavHostController
) {
    featureProviders.forEach { provider ->
        with(provider) {
            registerScreens(mainNavController as NavHostController, tabNavController)
        }
    }
}
