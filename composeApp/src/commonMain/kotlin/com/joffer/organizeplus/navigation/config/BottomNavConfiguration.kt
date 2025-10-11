package com.joffer.organizeplus.navigation.config

import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource

/**
 * Configuration for a bottom navigation item.
 * Features can implement this to add their screens to the bottom navigation.
 */
data class BottomNavConfiguration(
    val route: Any,
    val labelRes: StringResource,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val order: Int = 0
)
