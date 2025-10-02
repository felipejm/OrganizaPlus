package com.joffer.organizeplus.features.dashboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val typography = localTypography()

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
        }
    ) { paddingValues ->
        LazyColumn(
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
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$currentMonth $currentYear",
                            style = typography.headlineLarge,
                            color = AppColorScheme.black,
                            fontWeight = FontWeight.Black
                        )
                    }
                }

                // Personal Duties Section
                item {
                    DutyCategorySection(
                        duties = uiState.personalDuties,
                        onViewAll = onNavigateToPersonalDuties,
                        onDutyClick = onNavigateToDutyDetails,
                        categoryName = CategoryConstants.PERSONAL,
                        monthlySummary = uiState.personalSummary
                    )
                }

                // Company Duties Section
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
