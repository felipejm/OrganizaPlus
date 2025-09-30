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

    // Chart dimensions
    object Chart {
        val height: Dp = 250.dp
        val emptyStateHeight: Dp = 200.dp
        val barWidth: Dp = 15.dp
        val stepSize: Dp = xl
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
}
