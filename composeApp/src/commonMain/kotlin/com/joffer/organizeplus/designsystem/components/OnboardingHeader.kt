package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.onboarding_app_name

/**
 * Reusable onboarding header component
 *
 * Displays:
 * - App name "Organize+"
 * - Subtitle below the app name
 *
 * @param subtitle The subtitle text to display
 * @param modifier Modifier for the component
 */
@Composable
fun OnboardingHeader(
    subtitle: String,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App name
        Text(
            text = stringResource(Res.string.onboarding_app_name),
            style = typography.displaySmall,
            color = SemanticColors.Foreground.primary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Spacing.xs))

        // Subtitle
        Text(
            text = subtitle,
            style = typography.bodyLarge,
            color = SemanticColors.Foreground.secondary,
            textAlign = TextAlign.Center
        )
    }
}
