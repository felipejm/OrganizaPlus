package com.joffer.organizeplus.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = AppColorScheme.primary,
            onPrimary = AppColorScheme.onPrimary,
            primaryContainer = AppColorScheme.primaryContainer,
            onPrimaryContainer = AppColorScheme.onPrimaryContainer,
            secondary = AppColorScheme.secondary,
            onSecondary = AppColorScheme.onSecondary,
            secondaryContainer = AppColorScheme.secondaryContainer,
            onSecondaryContainer = AppColorScheme.onSecondaryContainer,
            tertiary = AppColorScheme.tertiary,
            onTertiary = AppColorScheme.onTertiary,
            tertiaryContainer = AppColorScheme.tertiaryContainer,
            onTertiaryContainer = AppColorScheme.onTertiaryContainer,
            error = AppColorScheme.error,
            onError = AppColorScheme.onError,
            errorContainer = AppColorScheme.errorContainer,
            onErrorContainer = AppColorScheme.onErrorContainer,
            background = Color(0xFF121212),
            onBackground = Color(0xFFFFFFFF),
            surface = Color(0xFF1E1E1E),
            onSurface = Color(0xFFFFFFFF),
            surfaceVariant = Color(0xFF2C2C2C),
            onSurfaceVariant = Color(0xFFBDBDBD),
            outline = AppColorScheme.outline,
            outlineVariant = AppColorScheme.outlineVariant,
            scrim = AppColorScheme.scrim,
            inverseSurface = AppColorScheme.inverseSurface,
            inverseOnSurface = AppColorScheme.inverseOnSurface,
            inversePrimary = AppColorScheme.inversePrimary
        )
    } else {
        lightColorScheme(
            primary = AppColorScheme.primary,
            onPrimary = AppColorScheme.onPrimary,
            primaryContainer = AppColorScheme.primaryContainer,
            onPrimaryContainer = AppColorScheme.onPrimaryContainer,
            secondary = AppColorScheme.secondary,
            onSecondary = AppColorScheme.onSecondary,
            secondaryContainer = AppColorScheme.secondaryContainer,
            onSecondaryContainer = AppColorScheme.onSecondaryContainer,
            tertiary = AppColorScheme.tertiary,
            onTertiary = AppColorScheme.onTertiary,
            tertiaryContainer = AppColorScheme.tertiaryContainer,
            onTertiaryContainer = AppColorScheme.onTertiaryContainer,
            error = AppColorScheme.error,
            onError = AppColorScheme.onError,
            errorContainer = AppColorScheme.errorContainer,
            onErrorContainer = AppColorScheme.onErrorContainer,
            background = AppColorScheme.background,
            onBackground = AppColorScheme.onBackground,
            surface = AppColorScheme.surface,
            onSurface = AppColorScheme.onSurface,
            surfaceVariant = AppColorScheme.surfaceVariant,
            onSurfaceVariant = AppColorScheme.onSurfaceVariant,
            outline = AppColorScheme.outline,
            outlineVariant = AppColorScheme.outlineVariant,
            scrim = AppColorScheme.scrim,
            inverseSurface = AppColorScheme.inverseSurface,
            inverseOnSurface = AppColorScheme.inverseOnSurface,
            inversePrimary = AppColorScheme.inversePrimary
        )
    }

    MaterialTheme(
        colorScheme = colorScheme.copy(
            // Adicionar as cores do HTML ao MaterialTheme
            primary = AppColorScheme.htmlPrimary,
            tertiary = AppColorScheme.htmlAmber
        ),
        content = content
    )
}
