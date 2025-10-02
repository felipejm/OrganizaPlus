package com.joffer.organizeplus.features.settings.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import com.joffer.organizeplus.designsystem.typography.ProvideSfProTypography
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.settings_design_system
import organizeplus.composeapp.generated.resources.settings_design_system_catalog
import organizeplus.composeapp.generated.resources.settings_design_system_description
import organizeplus.composeapp.generated.resources.settings_title
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToDesignSystem: () -> Unit,
    modifier: Modifier = Modifier
) {
    ProvideSfProTypography {
        val uiState by viewModel.uiState.collectAsState()
        val typography = DesignSystemTypography()

        Column(
            modifier = modifier.fillMaxSize()
        ) {
            AppTopAppBarWithBackButton(
                title = stringResource(Res.string.settings_title),
                onBackClick = onNavigateBack
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(Spacing.md)
            ) {
                // Design System
                Column {
                    Text(
                        text = stringResource(Res.string.settings_design_system),
                        style = typography.titleMedium,
                        color = AppColorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(Spacing.sm))

                    OrganizeCard {
                        Column(
                            modifier = Modifier.padding(Spacing.md)
                        ) {
                            OrganizePrimaryButton(
                                onClick = onNavigateToDesignSystem,
                                text = stringResource(Res.string.settings_design_system_catalog),
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(Spacing.sm))
                            Text(
                                text = stringResource(Res.string.settings_design_system_description),
                                style = typography.bodyMedium,
                                color = AppColorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}
