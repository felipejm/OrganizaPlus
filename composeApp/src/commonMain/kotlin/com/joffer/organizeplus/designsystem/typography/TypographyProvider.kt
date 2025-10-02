package com.joffer.organizeplus.designsystem.typography

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

/**
 * Local composition provider for typography.
 * This allows typography to be accessed throughout the composition tree.
 */
val LocalTypography = compositionLocalOf<Typography> {
    error("No RobustTypography provided")
}

/**
 * Provides typography to the composition tree.
 *
 * @param typography The typography system to provide
 * @param content The composable content that will have access to the typography
 */
@Composable
fun ProvideTypography(
    typography: Typography,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalTypography provides typography) {
        content()
    }
}

/**
 * Retrieves the current typography from the composition.
 *
 * @return The current RobustTypography instance
 */
@Composable
fun typography(): Typography = LocalTypography.current

/**
 * Convenience function to provide SF Pro Text typography.
 *
 * @param content The composable content that will have access to the typography
 */
@Composable
fun ProvideSfProTypography(
    content: @Composable () -> Unit
) {
    val typography = createTypography()
    ProvideTypography(typography, content)
}

/**
 * Extension function for easy access to typography in composables.
 *
 * Usage:
 * ```
 * @Composable
 * fun MyComponent() {
 *     val typography = localTypography()
 *     Text(
 *         text = "Hello",
 *         style = typography.headlineMedium
 *     )
 * }
 * ```
 */
@Composable
fun localTypography(): Typography = typography()
