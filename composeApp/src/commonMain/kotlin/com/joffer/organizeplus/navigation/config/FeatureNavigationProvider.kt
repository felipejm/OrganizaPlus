package com.joffer.organizeplus.navigation.config

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * Interface for feature modules to provide their navigation configuration.
 * Each feature should implement this to register its screens and bottom nav items.
 */
interface FeatureNavigationProvider {
    /**
     * Returns the bottom navigation configuration for this feature.
     * Return null if this feature doesn't contribute to bottom navigation.
     */
    @Composable
    fun getBottomNavConfiguration(): BottomNavConfiguration?

    /**
     * Registers all composable screens for this feature.
     * @param builder The NavGraphBuilder to register screens with
     * @param mainNavController The main navigation controller for cross-feature navigation
     * @param tabNavController The tab navigation controller for within-feature navigation
     */
    fun NavGraphBuilder.registerScreens(
        mainNavController: NavHostController,
        tabNavController: NavHostController
    )
}
