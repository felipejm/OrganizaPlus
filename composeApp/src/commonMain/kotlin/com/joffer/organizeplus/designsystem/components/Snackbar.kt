package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography

@Composable
fun AppSnackbar(
    message: String,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    isError: Boolean = false
) {
    val typography = DesignSystemTypography()
    Snackbar(
        modifier = modifier.padding(Spacing.lg),
        containerColor = if (isError) SemanticColors.Background.error else SemanticColors.Background.surface,
        contentColor = if (isError) SemanticColors.OnBackground.onError else SemanticColors.OnBackground.onSurface,
        action = if (actionLabel != null && onActionClick != null) {
            {
                TextButton(
                    onClick = onActionClick,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (isError) SemanticColors.OnBackground.onError else SemanticColors.Foreground.interactive
                    )
                ) {
                    Text(
                        text = actionLabel,
                        style = typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        } else {
            null
        }
    ) {
        Text(
            text = message,
            style = typography.bodyMedium,
            color = if (isError) SemanticColors.OnBackground.onError else SemanticColors.OnBackground.onSurface
        )
    }
}

@Composable
fun AppSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    snackbar: @Composable (SnackbarData) -> Unit = { data ->
        AppSnackbar(
            message = data.visuals.message,
            actionLabel = data.visuals.actionLabel,
            onActionClick = if (data.visuals.actionLabel != null) {
                { data.performAction() }
            } else {
                null
            },
            isError = false
        )
    }
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier,
        snackbar = snackbar
    )
}

@Composable
fun ErrorSnackbar(
    message: String,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    AppSnackbar(
        message = message,
        actionLabel = actionLabel,
        onActionClick = onActionClick,
        modifier = modifier,
        isError = true
    )
}

@Composable
fun SuccessSnackbar(
    message: String,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    AppSnackbar(
        message = message,
        actionLabel = actionLabel,
        onActionClick = onActionClick,
        modifier = modifier,
        isError = false
    )
}
