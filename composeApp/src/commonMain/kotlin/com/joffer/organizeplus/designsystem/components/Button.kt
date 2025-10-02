package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.joffer.organizeplus.designsystem.colors.ColorScheme
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.localTypography

@Composable
fun OrganizePrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null
) {
    val typography = localTypography()
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = ColorScheme.black,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(Spacing.Icon.sm)
            )
            Spacer(modifier = Modifier.width(Spacing.Button.iconSpacing))
        }
        Text(
            text = text,
            style = typography.button
        )
    }
}

/**
 * Secondary button with consistent styling
 */
@Composable
fun OrganizeSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null
) {
    val typography = localTypography()
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = ColorScheme.black
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(Spacing.Icon.sm)
            )
            Spacer(modifier = Modifier.width(Spacing.Button.iconSpacing))
        }
        Text(
            text = text,
            style = typography.button
        )
    }
}

/**
 * Text button with consistent styling
 */
@Composable
fun OrganizeTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val typography = localTypography()
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = ColorScheme.black
        )
    ) {
        Text(
            text = text,
            style = typography.button
        )
    }
}

/**
 * Icon button with consistent styling
 */
@Composable
fun OrganizeIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    ) {
        content()
    }
}

/**
 * Floating action button with consistent styling
 */
@Composable
fun OrganizeFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    contentDescription: String? = null
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = contentDescription
            )
        }
    }
}
