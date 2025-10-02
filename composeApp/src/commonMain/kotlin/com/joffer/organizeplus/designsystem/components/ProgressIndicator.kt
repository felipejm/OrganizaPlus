package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.colors.SemanticColors

/**
 * Design system progress indicator component
 */
@Composable
fun OrganizeProgressIndicator(
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = Spacing.Icon.xl,
    strokeWidth: androidx.compose.ui.unit.Dp = Spacing.xs,
    color: androidx.compose.ui.graphics.Color = SemanticColors.Foreground.interactive
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
    size: androidx.compose.ui.unit.Dp = Spacing.Icon.xl,
    strokeWidth: androidx.compose.ui.unit.Dp = Spacing.xs,
    color: androidx.compose.ui.graphics.Color = SemanticColors.Foreground.interactive
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
    size: androidx.compose.ui.unit.Dp = Spacing.Icon.sm,
    strokeWidth: androidx.compose.ui.unit.Dp = Spacing.Divider.medium,
    color: androidx.compose.ui.graphics.Color = SemanticColors.Foreground.interactive
) {
    OrganizeProgressIndicator(
        modifier = modifier,
        size = size,
        strokeWidth = strokeWidth,
        color = color
    )
}
