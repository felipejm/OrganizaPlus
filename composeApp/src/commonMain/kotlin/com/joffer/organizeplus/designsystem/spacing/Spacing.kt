package com.joffer.organizeplus.designsystem.spacing

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
object Spacing {

    // Base spacing scale
    val xs: Dp = 4.dp
    val sm: Dp = 8.dp
    val md: Dp = 12.dp
    val lg: Dp = 16.dp
    val xl: Dp = 20.dp
    val xxl: Dp = 24.dp
    val xxxl: Dp = 32.dp

    // Border radius scale
    object Radius {
        val xs: Dp = 4.dp
        val sm: Dp = 8.dp
        val md: Dp = 12.dp
        val lg: Dp = 16.dp
        val xl: Dp = 20.dp
        val xxl: Dp = 24.dp
        val xxxl: Dp = 32.dp
    }

    // Legacy radius (for backward compatibility)
    val borderRadius: Dp = Radius.lg
    val chipBorderRadius: Dp = Radius.md

    // Component dimensions
    val buttonHeight: Dp = 40.dp
    val iconSize: Dp = 32.dp
    val listItemHeight: Dp = 64.dp

    // Icon sizes
    object Icon {
        val xs: Dp = 16.dp
        val sm: Dp = 20.dp
        val md: Dp = 24.dp
        val lg: Dp = 32.dp
        val xl: Dp = 40.dp
    }

    // Elevation
    object Elevation {
        val none: Dp = 0.dp
        val xs: Dp = 2.dp
        val sm: Dp = 4.dp
        val md: Dp = 8.dp
        val lg: Dp = 12.dp
        val xl: Dp = 16.dp
    }

    // Divider thickness
    object Divider {
        val thin: Dp = 1.dp
        val medium: Dp = 2.dp
        val thick: Dp = 4.dp
    }

    // Chart dimensions
    object Chart {
        val height: Dp = 250.dp
        val emptyStateHeight: Dp = 200.dp
        val barWidth: Dp = 15.dp
        val stepSize: Dp = xl
        val strokeWidth: Dp = 14.dp
        const val animationDuration: Int = 1200
    }

    object Card {
        val padding: Dp = lg
        val margin: Dp = md
        val contentSpacing: Dp = md
    }

    object Button {
        val padding: Dp = lg
        val margin: Dp = sm
        val iconSpacing: Dp = sm
    }

    object List {
        val itemSpacing: Dp = sm
        val sectionSpacing: Dp = lg
        val padding: Dp = lg
    }

    object Screen {
        val padding: Dp = lg
        val contentSpacing: Dp = lg
    }

    // Navigation components
    object NavigationBar {
        val height: Dp = 80.dp
        val iconSize: Dp = Icon.md
        val labelSpacing: Dp = xs
    }
}
