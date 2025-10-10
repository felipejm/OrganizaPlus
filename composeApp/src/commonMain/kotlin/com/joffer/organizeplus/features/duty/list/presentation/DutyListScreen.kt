package com.joffer.organizeplus.features.duty.list.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Black
import com.joffer.organizeplus.common.utils.DateUtils
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.components.ResultType
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import com.joffer.organizeplus.designsystem.typography.ProvideSfProTypography
import com.joffer.organizeplus.features.duty.list.components.DutyCategoryGaugeChart
import com.joffer.organizeplus.features.duty.list.components.DutyListItem
import com.joffer.organizeplus.features.duty.list.domain.DutyCategoryFilter
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.add_duty
import organizeplus.composeapp.generated.resources.dashboard_company_duties
import organizeplus.composeapp.generated.resources.dashboard_personal_duties
import organizeplus.composeapp.generated.resources.duty_list_empty_subtitle
import organizeplus.composeapp.generated.resources.duty_list_empty_title
import organizeplus.composeapp.generated.resources.duty_list_error_subtitle
import organizeplus.composeapp.generated.resources.duty_list_error_title
import organizeplus.composeapp.generated.resources.duty_list_retry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DutyListScreen(
    viewModel: DutyListViewModel,
    categoryFilter: DutyCategoryFilter,
    onNavigateToCreateDuty: () -> Unit,
    onNavigateToOccurrences: (Long) -> Unit,
) {
    ProvideSfProTypography {
        val uiState by viewModel.uiState.collectAsState()
        val typography = DesignSystemTypography()

        // Get current month and year for header
        val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val currentMonth = DateUtils.getMonthName(currentDateTime.monthNumber)
        val currentYear = currentDateTime.year

        Scaffold(
            contentColor = SemanticColors.Background.primary,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onNavigateToCreateDuty,
                    containerColor = SemanticColors.Background.brand,
                    contentColor = SemanticColors.OnBackground.onBrand
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Duty"
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
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(Res.string.duty_list_error_title),
                                style = typography.titleMedium,
                                color = SemanticColors.Foreground.error
                            )
                            Spacer(modifier = Modifier.height(Spacing.sm))
                            Text(
                                text = uiState.error
                                    ?: stringResource(Res.string.duty_list_error_subtitle),
                                style = typography.bodyMedium,
                                color = SemanticColors.Foreground.secondary
                            )
                            Spacer(modifier = Modifier.height(Spacing.md))
                            Button(
                                onClick = { viewModel.onIntent(DutyListIntent.Retry) }
                            ) {
                                Text(stringResource(Res.string.duty_list_retry))
                            }
                        }
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
                                    text = stringResource(Res.string.add_duty),
                                    onClick = onNavigateToCreateDuty
                                )
                            }
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(Spacing.md),
                        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                    ) {
                        // Month/Year Header
                        item {
                            DutyListHeader(
                                categoryFilter = categoryFilter,
                                currentMonth = currentMonth,
                                currentYear = currentYear,
                                typography = typography
                            )
                        }

                        // Category Completion Gauge Chart
                        item {
                            DutyCategoryGaugeChart(
                                duties = uiState.duties,
                                categoryFilter = categoryFilter,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        items(uiState.duties) { dutyWithOccurrence ->
                            DutyListItem(
                                dutyWithOccurrence = dutyWithOccurrence,
                                onViewOccurrences = onNavigateToOccurrences,
                                onDelete = { dutyId ->
                                    viewModel.onIntent(DutyListIntent.ShowDeleteConfirmation(dutyId))
                                }
                            )
                        }
                    }
                }
            }
        }

        // Confirmation dialog
        if (uiState.showDeleteConfirmation) {
            DeleteDutyConfirmationDialog(
                onConfirm = {
                    uiState.dutyToDelete?.let { dutyId ->
                        viewModel.onIntent(DutyListIntent.ConfirmDeleteDuty(dutyId))
                    }
                },
                onDismiss = {
                    viewModel.onIntent(DutyListIntent.HideDeleteConfirmation)
                }
            )
        }
    }
}

@Composable
private fun DutyListHeader(
    categoryFilter: DutyCategoryFilter,
    currentMonth: String,
    currentYear: Int,
    typography: com.joffer.organizeplus.designsystem.typography.Typography
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Spacing.md),
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
                fontWeight = Black
            )
            Spacer(modifier = Modifier.height(Spacing.xs))
            Text(
                text = "$currentMonth $currentYear",
                style = typography.titleLarge,
                color = SemanticColors.Foreground.primary,
            )
        }
    }
    Spacer(modifier = Modifier.height(Spacing.sm))
}
