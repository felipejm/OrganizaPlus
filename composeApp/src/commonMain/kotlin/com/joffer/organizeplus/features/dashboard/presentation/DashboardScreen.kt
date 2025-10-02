package com.joffer.organizeplus.features.dashboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
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
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.components.ErrorBanner
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.localTypography
import com.joffer.organizeplus.features.dashboard.DashboardIntent
import com.joffer.organizeplus.features.dashboard.components.DutyCategorySection
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.settings_button_description
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onNavigateToPersonalDuties: () -> Unit,
    onNavigateToCompanyDuties: () -> Unit,
    onNavigateToDutyDetails: (Long) -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToCreateDuty: () -> Unit,
    onNavigateToAllDuties: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val typography = localTypography()
    val listState = rememberLazyListState()

    // Get current month and year for header
    val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val currentMonth = DateUtils.getMonthName(currentDateTime.monthNumber)
    val currentYear = currentDateTime.year

    LaunchedEffect(Unit) {
        viewModel.onIntent(DashboardIntent.LoadDashboard)
    }

    Scaffold(
        contentColor = AppColorScheme.background,
        topBar = {
            AppTopAppBarWithActions(
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(Res.string.settings_button_description),
                            tint = AppColorScheme.black
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToCreateDuty,
                containerColor = AppColorScheme.primary,
                contentColor = AppColorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Duty"
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(AppColorScheme.background)
                .padding(paddingValues),
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
                                color = AppColorScheme.black,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = "Dashboard",
                                style = typography.titleMedium,
                                color = AppColorScheme.formSecondaryText
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
                    AppColorScheme.surfaceVariant,
                    androidx.compose.foundation.shape.CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = AppColorScheme.formSecondaryText
            )
        }

        Spacer(modifier = Modifier.height(Spacing.lg))

        Text(
            text = "Welcome to OrganizePlus",
            style = localTypography().headlineMedium,
            color = AppColorScheme.formText,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(Spacing.sm))

        Text(
            text = "Start organizing your duties by creating your first task. You can add personal or company-related duties to keep track of everything.",
            style = localTypography().bodyMedium,
            color = AppColorScheme.formSecondaryText,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Spacing.xl))

        OrganizePrimaryButton(
            text = "Create Your First Duty",
            onClick = onNavigateToCreateDuty,
            icon = Icons.Default.Add
        )
    }
}
