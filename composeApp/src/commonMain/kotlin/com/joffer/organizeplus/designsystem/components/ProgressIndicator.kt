package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.spacing.Spacing

/**
 * Design system progress indicator component
 */
@Composable
fun OrganizeProgressIndicator(
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 40.dp,
    strokeWidth: androidx.compose.ui.unit.Dp = 4.dp,
    color: androidx.compose.ui.graphics.Color = AppColorScheme.primary
) {
    CircularProgressIndicator(
        modifier = modifier.size(size),
        color = color,
        strokeWidth = strokeWidth
    )
}

/**
 * Full screen progress indicator with centered alignment
 */
@Composable
fun OrganizeProgressIndicatorFullScreen(
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 40.dp,
    strokeWidth: androidx.compose.ui.unit.Dp = 4.dp,
    color: androidx.compose.ui.graphics.Color = AppColorScheme.primary
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        OrganizeProgressIndicator(
            size = size,
            strokeWidth = strokeWidth,
            color = color
        )
    }
}

/**
 * Inline progress indicator for smaller spaces
 */
@Composable
fun OrganizeProgressIndicatorInline(
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 20.dp,
    strokeWidth: androidx.compose.ui.unit.Dp = 2.dp,
    color: androidx.compose.ui.graphics.Color = AppColorScheme.primary
) {
    OrganizeProgressIndicator(
        modifier = modifier,
        size = size,
        strokeWidth = strokeWidth,
        color = color
    )
}
