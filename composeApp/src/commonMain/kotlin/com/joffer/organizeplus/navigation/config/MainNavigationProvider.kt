package com.joffer.organizeplus.navigation.config

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * Interface for feature modules to provide their main app navigation configuration.
 * Each feature should implement this to register its screens in the main navigation graph.
 */
interface MainNavigationProvider {
    /**
     * Registers all composable screens for this feature in the main navigation.
     * @param builder The NavGraphBuilder to register screens with
     * @param navController The main navigation controller
     */
    fun NavGraphBuilder.registerMainScreens(
        navController: NavHostController
    )
}

