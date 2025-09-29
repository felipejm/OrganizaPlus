package com.joffer.organizeplus.features.settings.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.settings_title
import organizeplus.composeapp.generated.resources.settings_user_name
import organizeplus.composeapp.generated.resources.settings_user_name_hint
import organizeplus.composeapp.generated.resources.settings_save
import organizeplus.composeapp.generated.resources.settings_saved
import organizeplus.composeapp.generated.resources.settings_general
import organizeplus.composeapp.generated.resources.settings_appearance
import organizeplus.composeapp.generated.resources.settings_notifications
import organizeplus.composeapp.generated.resources.settings_about

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToDesignSystem: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            // Show success message briefly
            kotlinx.coroutines.delay(2000)
            viewModel.clearSaveStatus()
        }
    }
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(Res.string.settings_title),
                    style = Typography.titleLarge,
                    color = AppColorScheme.onSurface
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = AppColorScheme.onSurface
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = AppColorScheme.surface
            )
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(Spacing.md)
        ) {
            // General Settings
            SettingsSection(
                title = stringResource(Res.string.settings_general)
            ) {
                FormField(
                    label = stringResource(Res.string.settings_user_name),
                    value = uiState.userName,
                    onValueChange = viewModel::updateUserName,
                    placeholder = stringResource(Res.string.settings_user_name_hint),
                    isRequired = true
                )
            }
            
            Spacer(modifier = Modifier.height(Spacing.lg))
            
            // Design System
            SettingsSection(
                title = "Design System"
            ) {
                OrganizeCard(
                    onClick = onNavigateToDesignSystem,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Spacing.md),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Design System Catalog",
                                style = Typography.title,
                                color = AppColorScheme.onSurface
                            )
                            Text(
                                text = "Explore components, colors, and typography",
                                style = Typography.body,
                                color = AppColorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            text = "→",
                            style = Typography.title,
                            color = AppColorScheme.primary
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(Spacing.xl))
            
            // Success Message
            if (uiState.isSaved) {
                SuccessBanner(
                    message = stringResource(Res.string.settings_saved)
                )
                Spacer(modifier = Modifier.height(Spacing.md))
            }
            
            // Save Button
            Button(
                onClick = viewModel::saveSettings,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColorScheme.primary
                )
            ) {
                Text(
                    text = stringResource(Res.string.settings_save),
                    color = AppColorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            style = Typography.titleMedium,
            color = AppColorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.height(Spacing.sm))
        
        OrganizeCard {
            Column(
                modifier = Modifier.padding(Spacing.md)
            ) {
                content()
            }
        }
    }
}
