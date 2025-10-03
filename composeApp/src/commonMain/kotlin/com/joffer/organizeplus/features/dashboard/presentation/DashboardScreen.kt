package com.joffer.organizeplus.features.dashboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.common.constants.CategoryConstants
import com.joffer.organizeplus.common.utils.DateUtils
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.components.ErrorBanner
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import com.joffer.organizeplus.features.dashboard.DashboardIntent
import com.joffer.organizeplus.features.dashboard.components.DutyCategorySection
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.dashboard_create_first_duty
import organizeplus.composeapp.generated.resources.dashboard_title
import organizeplus.composeapp.generated.resources.dashboard_welcome_subtitle
import organizeplus.composeapp.generated.resources.dashboard_welcome_title
import organizeplus.composeapp.generated.resources.settings_button_description

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onNavigateToPersonalDuties: () -> Unit,
    onNavigateToCompanyDuties: () -> Unit,
    onNavigateToDutyDetails: (Long) -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToCreateDuty: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val typography = DesignSystemTypography()
    val listState = rememberLazyListState()

    // Get current month and year for header
    val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val currentMonth = DateUtils.getMonthName(currentDateTime.monthNumber)
    val currentYear = currentDateTime.year

    LaunchedEffect(Unit) {
        viewModel.onIntent(DashboardIntent.LoadDashboard)
    }

    Scaffold(
        contentColor = SemanticColors.Background.primary,
        topBar = {
            AppTopAppBarWithActions(
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(Res.string.settings_button_description),
                            tint = SemanticColors.Foreground.primary
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(horizontal = Spacing.Screen.padding),
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
                // Month/Year Header
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "$currentMonth $currentYear",
                                style = typography.headlineLarge,
                                color = SemanticColors.Foreground.primary,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = stringResource(Res.string.dashboard_title),
                                style = typography.titleMedium,
                                color = SemanticColors.Foreground.secondary
                            )
                        }
                    }
                }

                // Check if there are any duties at all
                if (uiState.personalDuties.isEmpty() && uiState.companyDuties.isEmpty()) {
                    item {
                        EmptyDashboardState(
                            onNavigateToCreateDuty = onNavigateToCreateDuty
                        )
                    }
                } else {
                    // Personal Duties Section
                    if (uiState.personalDuties.isNotEmpty()) {
                        item {
                            DutyCategorySection(
                                duties = uiState.personalDuties,
                                onViewAll = onNavigateToPersonalDuties,
                                onDutyClick = onNavigateToDutyDetails,
                                categoryName = CategoryConstants.PERSONAL,
                                monthlySummary = uiState.personalSummary
                            )
                        }
                    }

                    // Company Duties Section
                    if (uiState.companyDuties.isNotEmpty()) {
                        item {
                            DutyCategorySection(
                                duties = uiState.companyDuties,
                                onViewAll = onNavigateToCompanyDuties,
                                onDutyClick = onNavigateToDutyDetails,
                                categoryName = CategoryConstants.COMPANY,
                                monthlySummary = uiState.companySummary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyDashboardState(
    onNavigateToCreateDuty: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Spacing.xxxl),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Empty state illustration
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    SemanticColors.Background.surfaceVariant,
                    androidx.compose.foundation.shape.CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = SemanticColors.Foreground.secondary
            )
        }

        Spacer(modifier = Modifier.height(Spacing.lg))

        Text(
            text = stringResource(Res.string.dashboard_welcome_title),
            style = DesignSystemTypography().headlineMedium,
            color = SemanticColors.Foreground.primary,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(Spacing.sm))

        Text(
            text = stringResource(Res.string.dashboard_welcome_subtitle),
            style = DesignSystemTypography().bodyMedium,
            color = SemanticColors.Foreground.secondary,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Spacing.xl))

        OrganizePrimaryButton(
            text = stringResource(Res.string.dashboard_create_first_duty),
            onClick = onNavigateToCreateDuty,
            icon = Icons.Default.Add
        )
    }
}
