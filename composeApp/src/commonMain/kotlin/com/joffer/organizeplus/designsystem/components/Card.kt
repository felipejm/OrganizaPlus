package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@Composable
fun OrganizeCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        shape = RoundedCornerShape(Spacing.Radius.md),
        modifier = modifier,
        onClick = onClick ?: {},
        colors = CardDefaults.cardColors(
            containerColor = AppColorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Spacing.Elevation.none),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            content = content
        )
    }
}
