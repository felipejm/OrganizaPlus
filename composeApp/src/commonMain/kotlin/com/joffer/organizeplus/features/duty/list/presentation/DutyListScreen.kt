package com.joffer.organizeplus.features.duty.list.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.joffer.organizeplus.common.utils.DateUtils
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.icons.OrganizeIcons
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyWithLastOccurrence
import com.joffer.organizeplus.features.duty.list.components.DutyCategoryGaugeChart
import com.joffer.organizeplus.features.duty.list.components.DutyListItem
import com.joffer.organizeplus.features.duty.list.domain.DutyCategoryFilter
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.add_duty_description
import organizeplus.composeapp.generated.resources.dashboard_company_duties
import organizeplus.composeapp.generated.resources.dashboard_personal_duties
import organizeplus.composeapp.generated.resources.duty_list_empty_subtitle
import organizeplus.composeapp.generated.resources.duty_list_empty_title
import organizeplus.composeapp.generated.resources.duty_list_error_subtitle
import organizeplus.composeapp.generated.resources.duty_list_error_title
import organizeplus.composeapp.generated.resources.duty_list_retry
import organizeplus.composeapp.generated.resources.duty_review_title

@Composable
fun DutyListScreen(
    viewModel: DutyListViewModel,
    categoryFilter: DutyCategoryFilter,
    onNavigateToCreateDuty: () -> Unit,
    onNavigateToOccurrences: (Long) -> Unit,
    onNavigateToReview: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val typography = DesignSystemTypography()

    // Get current month and year for header
    val currentDateTime = remember {
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }
    val currentMonth = DateUtils.getMonthName(currentDateTime.monthNumber)
    val currentYear = currentDateTime.year

    Scaffold(
        contentColor = SemanticColors.Background.primary,
        topBar = {
            AppTopAppBarWithActions(
                actions = {
                    IconButton(onClick = onNavigateToReview) {
                        Icon(
                            imageVector = OrganizeIcons.Actions.History,
                            contentDescription = stringResource(Res.string.duty_review_title),
                            tint = SemanticColors.Foreground.primary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToCreateDuty,
                containerColor = SemanticColors.Background.brand,
                contentColor = SemanticColors.OnBackground.onBrand
            ) {
                Icon(
                    imageVector = OrganizeIcons.Actions.Plus,
                    contentDescription = stringResource(Res.string.add_duty_description)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                OrganizeProgressIndicatorFullScreen()
            } else if (uiState.error != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    OrganizeResult(
                        type = ResultType.ERROR,
                        title = stringResource(Res.string.duty_list_error_title),
                        description = uiState.error
                            ?: stringResource(Res.string.duty_list_error_subtitle),
                        actions = {
                            OrganizePrimaryButton(
                                text = stringResource(Res.string.duty_list_retry),
                                onClick = { viewModel.onIntent(DutyListIntent.Retry) }
                            )
                        }
                    )
                }
            } else if (uiState.duties.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    OrganizeResult(
                        type = ResultType.INFO,
                        title = stringResource(Res.string.duty_list_empty_title),
                        description = stringResource(Res.string.duty_list_empty_subtitle),
                        actions = {
                            OrganizePrimaryButton(
                                text = stringResource(Res.string.add_duty_description),
                                onClick = onNavigateToCreateDuty
                            )
                        }
                    )
                }
            } else {
                DutyListContent(
                    duties = uiState.duties,
                    categoryFilter = categoryFilter,
                    currentMonth = currentMonth,
                    currentYear = currentYear,
                    typography = typography,
                    onNavigateToOccurrences = onNavigateToOccurrences,
                    onDeleteDuty = { dutyId ->
                        viewModel.onIntent(DutyListIntent.ShowDeleteConfirmation(dutyId))
                    }
                )
            }
        }
    }

    // Confirmation dialog
    if (uiState.showDeleteConfirmation && uiState.dutyToDelete != null) {
        DeleteDutyConfirmationDialog(
            onConfirm = {
                viewModel.onIntent(DutyListIntent.ConfirmDeleteDuty(uiState.dutyToDelete!!))
            },
            onDismiss = {
                viewModel.onIntent(DutyListIntent.HideDeleteConfirmation)
            }
        )
    }
}

@Composable
private fun DutyListContent(
    duties: List<DutyWithLastOccurrence>,
    categoryFilter: DutyCategoryFilter,
    currentMonth: String,
    currentYear: Int,
    typography: Typography,
    onNavigateToOccurrences: (Long) -> Unit,
    onDeleteDuty: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        // Month/Year Header
        item(key = "header") {
            DutyListHeader(
                categoryFilter = categoryFilter,
                currentMonth = currentMonth,
                currentYear = currentYear,
                typography = typography
            )
        }

        // Category Completion Gauge Chart
        item(key = "gauge_chart") {
            DutyCategoryGaugeChart(
                duties = duties,
                categoryFilter = categoryFilter,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Duty Items
        items(
            items = duties,
            key = { it.duty.id },
            contentType = { "duty_item" }
        ) { dutyWithOccurrence ->
            DutyListItem(
                dutyWithOccurrence = dutyWithOccurrence,
                onViewOccurrences = onNavigateToOccurrences,
                onDelete = onDeleteDuty
            )
        }
    }
}

@Composable
private fun DutyListHeader(
    categoryFilter: DutyCategoryFilter,
    currentMonth: String,
    currentYear: Int,
    typography: Typography
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val categoryName = when (categoryFilter) {
            DutyCategoryFilter.Personal -> stringResource(Res.string.dashboard_personal_duties)
            DutyCategoryFilter.Company -> stringResource(Res.string.dashboard_company_duties)
        }

        Column {
            Text(
                text = categoryName,
                style = typography.headlineMedium,
                color = SemanticColors.Foreground.primary,
                fontWeight = FontWeight.Black
            )
            Spacer(modifier = Modifier.height(Spacing.xs))
            Text(
                text = "$currentMonth $currentYear",
                style = typography.titleLarge,
                color = SemanticColors.Foreground.primary,
            )
        }
    }
}
