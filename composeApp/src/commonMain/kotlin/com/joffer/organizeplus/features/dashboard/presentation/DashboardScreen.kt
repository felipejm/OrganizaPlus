package com.joffer.organizeplus.features.dashboard.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.features.dashboard.components.UpcomingSection
import com.joffer.organizeplus.features.dashboard.components.LatestDutiesSection
import com.joffer.organizeplus.designsystem.components.ErrorBanner
import com.joffer.organizeplus.features.dashboard.presentation.DashboardViewModel
import com.joffer.organizeplus.features.dashboard.DashboardIntent
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.settings_button_description
import organizeplus.composeapp.generated.resources.app_name

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onNavigateToDuties: () -> Unit,
    onNavigateToEditDuty: (String) -> Unit,
    onNavigateToCreateDuty: () -> Unit,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.onIntent(DashboardIntent.LoadDashboard)
    }
    
    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopAppBarWithActions(
                title = stringResource(Res.string.app_name),
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(Res.string.settings_button_description),
                            tint = AppColorScheme.formIcon
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(Spacing.Screen.padding),
            verticalArrangement = Arrangement.spacedBy(Spacing.Screen.contentSpacing)
        ) {
            if (uiState.error != null) {
                item {
                    ErrorBanner(
                        message = uiState.error!!,
                        onRetry = { viewModel.onIntent(DashboardIntent.Retry) },
                        onDismiss = { viewModel.onIntent(DashboardIntent.ClearError) }
                    )
                }
            }
            
            if (uiState.isLoading) {
                item {
                    OrganizeProgressIndicatorFullScreen()
                }
            } else {
                item {
                    LatestDutiesSection(
                        duties = uiState.latestDuties,
                        onViewAll = onNavigateToDuties,
                        onAddDuty = onNavigateToCreateDuty,
                    )
                }
                
            }
        }
    }
}

