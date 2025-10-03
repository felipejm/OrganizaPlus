package com.joffer.organizeplus.designsystem.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.confirmation_dialog_cancel
import organizeplus.composeapp.generated.resources.confirmation_dialog_confirm
import organizeplus.composeapp.generated.resources.confirmation_dialog_delete_duty_message
import organizeplus.composeapp.generated.resources.confirmation_dialog_delete_duty_title
import organizeplus.composeapp.generated.resources.confirmation_dialog_delete_occurrence_message
import organizeplus.composeapp.generated.resources.confirmation_dialog_delete_occurrence_title
import organizeplus.composeapp.generated.resources.confirmation_dialog_title

@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    confirmText: String = stringResource(Res.string.confirmation_dialog_confirm),
    dismissText: String = stringResource(Res.string.confirmation_dialog_cancel),
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = typography.titleMedium,
                color = SemanticColors.Foreground.primary
            )
        },
        text = {
            Text(
                text = message,
                style = typography.bodyMedium,
                color = SemanticColors.Foreground.secondary
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = SemanticColors.Foreground.error
                )
            ) {
                Text(
                    text = confirmText,
                    style = typography.labelLarge
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = SemanticColors.Foreground.secondary
                )
            ) {
                Text(
                    text = dismissText,
                    style = typography.labelLarge
                )
            }
        },
        containerColor = SemanticColors.Background.surface,
        modifier = modifier
    )
}

@Composable
fun DeleteDutyConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConfirmationDialog(
        title = stringResource(Res.string.confirmation_dialog_delete_duty_title),
        message = stringResource(Res.string.confirmation_dialog_delete_duty_message),
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        modifier = modifier
    )
}

@Composable
fun DeleteOccurrenceConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConfirmationDialog(
        title = stringResource(Res.string.confirmation_dialog_delete_occurrence_title),
        message = stringResource(Res.string.confirmation_dialog_delete_occurrence_message),
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        modifier = modifier
    )
}
