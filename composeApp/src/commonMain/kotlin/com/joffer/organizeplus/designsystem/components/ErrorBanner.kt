package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography

@Composable
fun ErrorBanner(
    message: String,
    onRetry: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = SemanticColors.Background.error
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Spacing.Elevation.none)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = message,
                style = typography.bodyMedium,
                color = SemanticColors.OnBackground.onError,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(Spacing.sm))

            OrganizeIconButton(
                onClick = onRetry
            ) {
                Text(
                    text = "↻",
                    style = typography.bodyLarge,
                    color = SemanticColors.OnBackground.onError
                )
            }

            OrganizeIconButton(
                onClick = onDismiss
            ) {
                Text(
                    text = "×",
                    style = typography.bodyLarge,
                    color = SemanticColors.OnBackground.onError
                )
            }
        }
    }
}
