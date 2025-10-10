package com.joffer.organizeplus.features.duty.detail.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.joffer.organizeplus.designsystem.icons.OrganizeIcons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.components.ErrorBanner
import com.joffer.organizeplus.designsystem.components.ResultType
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import com.joffer.organizeplus.features.duty.detail.components.DutyBarChart
import com.joffer.organizeplus.features.duty.detail.components.DutyHeaderCard
import com.joffer.organizeplus.features.duty.detail.components.DutyOccurrenceListItem
import com.joffer.organizeplus.features.duty.occurrence.presentation.AddDutyOccurrenceBottomSheet
import com.joffer.organizeplus.features.duty.occurrence.presentation.AddDutyOccurrenceViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.duty_detail_edit
import organizeplus.composeapp.generated.resources.duty_occurrence_list_add_occurrence
import organizeplus.composeapp.generated.resources.duty_occurrence_list_empty_subtitle
import organizeplus.composeapp.generated.resources.duty_occurrence_list_empty_title
import organizeplus.composeapp.generated.resources.duty_occurrence_list_title

private val EMPTY_STATE_HEIGHT = 400.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DutyDetailsScreen(
    viewModel: DutyDetailsListViewModel,
    onNavigateBack: () -> Unit,
    onEditDuty: (Long, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    var showAddOccurrenceBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.onIntent(DutyDetailsListIntent.LoadRecords)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentColor = SemanticColors.Background.primary,
        topBar = {
            AppTopAppBarWithBackButton(
                onBackClick = onNavigateBack,
                actions = {
                    IconButton(onClick = {
                        uiState.duty?.let { duty ->
                            onEditDuty(duty.id, duty.categoryName)
                        }
                    }) {
                        Icon(
                            imageVector = OrganizeIcons.Actions.Edit,
                            contentDescription = stringResource(Res.string.duty_detail_edit),
                            tint = SemanticColors.Foreground.brand
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        DutyDetailsContent(
            uiState = uiState,
            viewModel = viewModel,
            onShowAddOccurrence = { showAddOccurrenceBottomSheet = true },
            onDeleteRecord = { recordId ->
                viewModel.onIntent(DutyDetailsListIntent.ShowDeleteConfirmation(recordId))
            },
            modifier = Modifier.padding(paddingValues)
        )

        // Add Occurrence Bottom Sheet
        if (showAddOccurrenceBottomSheet) {
            val addDutyOccurrenceViewModel = koinInject<AddDutyOccurrenceViewModel> {
                parametersOf(uiState.duty?.id)
            }

            AddDutyOccurrenceBottomSheet(
                viewModel = addDutyOccurrenceViewModel,
                onDismiss = {
                    showAddOccurrenceBottomSheet = false
                    viewModel.onIntent(DutyDetailsListIntent.LoadRecords)
                }
            )
        }

        // Confirmation dialog
        if (uiState.showDeleteConfirmation) {
            DeleteOccurrenceConfirmationDialog(
                onConfirm = {
                    uiState.occurrenceToDelete?.let { recordId ->
                        viewModel.onIntent(DutyDetailsListIntent.ConfirmDeleteRecord(recordId))
                    }
                },
                onDismiss = {
                    viewModel.onIntent(DutyDetailsListIntent.HideDeleteConfirmation)
                }
            )
        }
    }
}

@Composable
private fun DutyDetailsContent(
    uiState: DutyDetailsListUiState,
    viewModel: DutyDetailsListViewModel,
    onShowAddOccurrence: () -> Unit,
    onDeleteRecord: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        uiState.isLoading -> {
            OrganizeProgressIndicatorFullScreen(modifier = modifier)
        }

        uiState.error != null -> {
            DutyDetailsErrorContent(
                error = uiState.error,
                onRetry = { viewModel.onIntent(DutyDetailsListIntent.Retry) },
                onDismiss = { viewModel.onIntent(DutyDetailsListIntent.ClearError) },
                modifier = modifier
            )
        }

        else -> {
            DutyDetailsDataContent(
                uiState = uiState,
                onShowAddOccurrence = onShowAddOccurrence,
                onDeleteRecord = onDeleteRecord,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun DutyDetailsErrorContent(
    error: String?,
    onRetry: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    ErrorBanner(
        message = error ?: "",
        onRetry = onRetry,
        onDismiss = onDismiss,
        modifier = modifier
    )
}

@Composable
private fun DutyDetailsDataContent(
    uiState: DutyDetailsListUiState,
    onShowAddOccurrence: () -> Unit,
    onDeleteRecord: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        item {
            Text(
                text = uiState.duty?.title.orEmpty(),
                style = DesignSystemTypography().headlineLarge,
                color = SemanticColors.Foreground.primary,
                fontWeight = FontWeight.Black
            )
        }
        // Duty Header Information
        uiState.duty?.let { duty ->
            item {
                DutyHeaderCard(duty = duty)
            }
        }

        // Chart
        uiState.chartData?.let { chartData ->
            item {
                DutyBarChart(chartData = chartData)
            }
        }

        // Records Section
        if (uiState.records.isEmpty()) {
            item {
                DutyDetailsEmptyState(onShowAddOccurrence = onShowAddOccurrence)
            }
        } else {
            item {
                DutyDetailsRecordsHeader(onShowAddOccurrence = onShowAddOccurrence)
            }

            items(uiState.records) { occurrence ->
                DutyOccurrenceListItem(
                    occurrence = occurrence,
                    onDelete = onDeleteRecord
                )
            }
        }
    }
}

@Composable
private fun DutyDetailsEmptyState(
    onShowAddOccurrence: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(EMPTY_STATE_HEIGHT),
        contentAlignment = Alignment.Center
    ) {
        OrganizeResult(
            type = ResultType.INFO,
            title = stringResource(Res.string.duty_occurrence_list_empty_title),
            description = stringResource(Res.string.duty_occurrence_list_empty_subtitle),
            actions = {
                OrganizePrimaryButton(
                    text = stringResource(Res.string.duty_occurrence_list_add_occurrence),
                    onClick = onShowAddOccurrence
                )
            }
        )
    }
}

@Composable
private fun DutyDetailsRecordsHeader(
    onShowAddOccurrence: () -> Unit
) {
    val typography = DesignSystemTypography()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.sm),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(Res.string.duty_occurrence_list_title),
            style = typography.headlineSmall,
            color = SemanticColors.Foreground.primary,
            fontWeight = FontWeight.Bold
        )

        IconButton(onClick = onShowAddOccurrence) {
            Icon(
                imageVector = OrganizeIcons.Actions.Plus,
                contentDescription = stringResource(Res.string.duty_occurrence_list_add_occurrence),
                tint = SemanticColors.Foreground.brand
            )
        }
    }
}
