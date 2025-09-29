package com.joffer.organizeplus.designsystem.spacing

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
object Spacing {
    
    val xs: Dp = 4.dp
    val sm: Dp = 8.dp
    val md: Dp = 12.dp
    val lg: Dp = 16.dp
    val xl: Dp = 20.dp
    val xxl: Dp = 24.dp
    
    val cardMargin: Dp = 24.dp
    val cardPadding: Dp = 16.dp
    val buttonHeight: Dp = 40.dp
    val buttonPadding: Dp = 16.dp
    val borderRadius: Dp = 16.dp
    val chipBorderRadius: Dp = 12.dp
    val iconSize: Dp = 32.dp
    val listItemHeight: Dp = 64.dp
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
