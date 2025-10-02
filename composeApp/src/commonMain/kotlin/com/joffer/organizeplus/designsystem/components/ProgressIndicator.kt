package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.spacing.Spacing

/**
 * Design system progress indicator component
 */
@Composable
fun OrganizeProgressIndicator(
    modifier: Modifier = Modifier,
    size: Dp = Spacing.Icon.xl,
    strokeWidth: Dp = Spacing.xs,
    color: Color = SemanticColors.Background.brand
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
    size: Dp = Spacing.Icon.xl,
    strokeWidth: Dp = Spacing.xs,
    color: Color = SemanticColors.Background.brand
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
    size: Dp = Spacing.Icon.sm,
    strokeWidth: Dp = Spacing.Divider.medium,
    color: Color = SemanticColors.Background.brand
) {
    OrganizeProgressIndicator(
        modifier = modifier,
        size = size,
        strokeWidth = strokeWidth,
        color = color
    )
}
