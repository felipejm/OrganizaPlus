package com.joffer.organizeplus.features.settings.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import com.joffer.organizeplus.features.settings.domain.StorageMode
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.settings_design_system
import organizeplus.composeapp.generated.resources.settings_design_system_catalog
import organizeplus.composeapp.generated.resources.settings_design_system_description
import organizeplus.composeapp.generated.resources.settings_storage_mode
import organizeplus.composeapp.generated.resources.settings_storage_mode_description
import organizeplus.composeapp.generated.resources.settings_storage_mode_local
import organizeplus.composeapp.generated.resources.settings_storage_mode_remote
import organizeplus.composeapp.generated.resources.settings_title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToDesignSystem: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val typography = DesignSystemTypography()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = SemanticColors.Background.primary,
        topBar = {
            AppTopAppBarWithBackButton(onBackClick = onNavigateBack)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    start = Spacing.md,
                    end = Spacing.md,
                    top = paddingValues.calculateTopPadding() + Spacing.md,
                    bottom = paddingValues.calculateBottomPadding() + Spacing.md
                )
        ) {
            Text(
                text = stringResource(Res.string.settings_title),
                style = typography.headlineLarge,
                color = SemanticColors.Foreground.primary,
                fontWeight = FontWeight.Black
            )
            Spacer(modifier = Modifier.height(Spacing.lg))

            // Storage Mode Section
            Column {
                Text(
                    text = stringResource(Res.string.settings_storage_mode),
                    style = typography.titleMedium,
                    color = SemanticColors.Foreground.primary,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(Spacing.sm))

                OrganizeCard {
                    Column(
                        modifier = Modifier.padding(Spacing.md)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(Res.string.settings_storage_mode_local),
                                style = typography.bodyLarge,
                                color = SemanticColors.Foreground.primary
                            )
                            RadioButton(
                                selected = uiState.storageMode == StorageMode.LOCAL,
                                onClick = { viewModel.updateStorageMode(StorageMode.LOCAL) }
                            )
                        }

                        Spacer(modifier = Modifier.height(Spacing.sm))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(Res.string.settings_storage_mode_remote),
                                style = typography.bodyLarge,
                                color = SemanticColors.Foreground.primary
                            )
                            RadioButton(
                                selected = uiState.storageMode == StorageMode.REMOTE,
                                onClick = { viewModel.updateStorageMode(StorageMode.REMOTE) }
                            )
                        }

                        Spacer(modifier = Modifier.height(Spacing.sm))

                        Text(
                            text = stringResource(Res.string.settings_storage_mode_description),
                            style = typography.bodyMedium,
                            color = SemanticColors.Foreground.secondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(Spacing.lg))

            // Design System
            Column {
                Text(
                    text = stringResource(Res.string.settings_design_system),
                    style = typography.titleMedium,
                    color = SemanticColors.Foreground.primary,
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
                            color = SemanticColors.Foreground.secondary
                        )
                    }
                }
            }
        }
    }
}
