package com.joffer.organizeplus.features.dashboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.joffer.organizeplus.common.constants.CategoryConstants
import com.joffer.organizeplus.common.utils.DateUtils
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.components.ErrorBanner
import com.joffer.organizeplus.designsystem.icons.OrganizeIcons
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onNavigateToPersonalDuties: () -> Unit,
    onNavigateToCompanyDuties: () -> Unit,
    onNavigateToDutyDetails: (Long) -> Unit,
    @Suppress("UNUSED_PARAMETER") onNavigateToSettings: () -> Unit,
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
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = modifier.fillMaxSize().padding(top = paddingValues.calculateTopPadding()),
            contentPadding = PaddingValues(
                start = Spacing.Screen.padding,
                end = Spacing.Screen.padding,
                bottom = Spacing.Screen.padding
            ),
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

                // Always show Personal Duties Section (even if empty, with 0 values)
                item {
                    DutyCategorySection(
                        duties = uiState.personalDuties,
                        onViewAll = onNavigateToPersonalDuties,
                        onDutyClick = onNavigateToDutyDetails,
                        categoryName = CategoryConstants.PERSONAL,
                        monthlySummary = uiState.personalSummary
                    )
                }

                // Always show Company Duties Section (even if empty, with 0 values)
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
                .size(Spacing.xxl * 2)
                .background(
                    SemanticColors.Background.surfaceVariant,
                    androidx.compose.foundation.shape.CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = OrganizeIcons.Actions.Plus,
                contentDescription = null,
                modifier = Modifier.size(Spacing.xl * 2),
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
            icon = OrganizeIcons.Actions.Plus
        )
    }
}
