package com.joffer.organizeplus.features.duty.detail.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.components.ErrorBanner
import com.joffer.organizeplus.designsystem.components.ResultType
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
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
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

private val EMPTY_STATE_HEIGHT = 400.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DutyDetailsScreen(
    viewModel: DutyDetailsListViewModel,
    onNavigateBack: () -> Unit,
    onEditDuty: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddOccurrenceBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.onIntent(DutyDetailsListIntent.LoadRecords)
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        AppTopAppBarWithBackButton(
            title = uiState.duty?.title ?: stringResource(Res.string.duty_occurrence_list_title),
            onBackClick = onNavigateBack,
            actions = {
                IconButton(onClick = { uiState.duty?.id?.let(onEditDuty) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(Res.string.duty_detail_edit),
                        tint = AppColorScheme.primary
                    )
                }
            }
        )

        when {
            uiState.isLoading -> {
                OrganizeProgressIndicatorFullScreen()
            }

            uiState.error != null -> {
                ErrorBanner(
                    message = uiState.error ?: "",
                    onRetry = { viewModel.onIntent(DutyDetailsListIntent.Retry) },
                    onDismiss = { viewModel.onIntent(DutyDetailsListIntent.ClearError) }
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(Spacing.md),
                    verticalArrangement = Arrangement.spacedBy(Spacing.md)
                ) {
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
                                            onClick = { showAddOccurrenceBottomSheet = true }
                                        )
                                    }
                                )
                            }
                        }
                    } else {
                        // Header
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = Spacing.sm),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(Res.string.duty_occurrence_list_title),
                                    style = Typography.headlineSmall,
                                    color = AppColorScheme.onSurface,
                                    fontWeight = FontWeight.Bold
                                )

                                IconButton(onClick = { showAddOccurrenceBottomSheet = true }) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = stringResource(
                                            Res.string.duty_occurrence_list_add_occurrence
                                        ),
                                        tint = AppColorScheme.primary
                                    )
                                }
                            }
                        }

                        items(uiState.records) { occurrence ->
                            DutyOccurrenceListItem(
                                occurrence = occurrence,
                                onDelete = { viewModel.onIntent(DutyDetailsListIntent.DeleteRecord(it)) }
                            )
                        }
                    }
                }
            }
        }

        // Add Occurrence Bottom Sheet
        if (showAddOccurrenceBottomSheet) {
            val addDutyOccurrenceViewModel: AddDutyOccurrenceViewModel = koinInject {
                parametersOf(viewModel.getDutyId())
            }
            AddDutyOccurrenceBottomSheet(
                viewModel = addDutyOccurrenceViewModel,
                onDismiss = {
                    showAddOccurrenceBottomSheet = false
                    viewModel.onIntent(DutyDetailsListIntent.LoadRecords)
                }
            )
        }
    }
}
