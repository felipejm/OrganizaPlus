package com.joffer.organizeplus.features.dashboard.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.common.constants.CategoryConstants
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.components.ErrorBanner
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.features.dashboard.DashboardIntent
import com.joffer.organizeplus.features.dashboard.components.DutyCategorySection
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.app_name
import organizeplus.composeapp.generated.resources.settings_button_description
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onNavigateToPersonalDuties: () -> Unit,
    onNavigateToCompanyDuties: () -> Unit,
    onNavigateToEditDuty: (String) -> Unit,
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
                // Personal Duties Section
                item {
                    DutyCategorySection(
                        duties = uiState.personalDuties,
                        onViewAll = onNavigateToPersonalDuties,
                        onDutyClick = onNavigateToEditDuty,
                        categoryName = CategoryConstants.PERSONAL,
                        monthlySummary = uiState.personalSummary
                    )
                }

                // Company Duties Section
                item {
                    DutyCategorySection(
                        duties = uiState.companyDuties,
                        onViewAll = onNavigateToCompanyDuties,
                        onDutyClick = onNavigateToEditDuty,
                        categoryName = CategoryConstants.COMPANY,
                        monthlySummary = uiState.companySummary
                    )
                }
            }
        }
    }
}
