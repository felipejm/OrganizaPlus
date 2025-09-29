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
import com.joffer.organizeplus.common.utils.DateUtils
import com.joffer.organizeplus.utils.formatDateForDisplay
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.components.ResultType
import com.joffer.organizeplus.designsystem.components.ErrorBanner
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrence
import com.joffer.organizeplus.features.duty.occurrence.presentation.AddDutyOccurrenceBottomSheet
import com.joffer.organizeplus.features.duty.occurrence.presentation.AddDutyOccurrenceViewModel
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.duty_occurrence_list_title
import organizeplus.composeapp.generated.resources.duty_occurrence_list_empty_title
import organizeplus.composeapp.generated.resources.duty_occurrence_list_empty_subtitle
import organizeplus.composeapp.generated.resources.duty_occurrence_list_add_occurrence
import organizeplus.composeapp.generated.resources.duty_occurrence_list_delete
import organizeplus.composeapp.generated.resources.duty_occurrence_list_amount
import organizeplus.composeapp.generated.resources.duty_occurrence_list_date
import organizeplus.composeapp.generated.resources.duty_occurrence_list_notes
import organizeplus.composeapp.generated.resources.duty_detail_edit
import organizeplus.composeapp.generated.resources.duty_detail_start_day
import organizeplus.composeapp.generated.resources.duty_detail_due_day
import organizeplus.composeapp.generated.resources.duty_detail_category
import organizeplus.composeapp.generated.resources.duty_detail_type
import organizeplus.composeapp.generated.resources.duty_detail_status
import organizeplus.composeapp.generated.resources.duty_type_payable
import organizeplus.composeapp.generated.resources.duty_type_actionable
import organizeplus.composeapp.generated.resources.status_pending
import organizeplus.composeapp.generated.resources.status_paid
import organizeplus.composeapp.generated.resources.status_overdue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DutyDetailsListScreen(
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
        
        // Duty Header Information
        uiState.duty?.let { duty ->
            DutyHeaderCard(
                duty = duty,
                modifier = Modifier.padding(Spacing.md)
            )
        }
        
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
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
                
                uiState.records.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
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
                
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(Spacing.md),
                        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                    ) {
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
                                        contentDescription = stringResource(Res.string.duty_occurrence_list_add_occurrence),
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

@Composable
private fun DutyOccurrenceListItem(
    occurrence: DutyOccurrence,
    onDelete: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OrganizeCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Show month and year label
                    val monthYearText = "${DateUtils.getMonthName(occurrence.completedDate.monthNumber)} ${occurrence.completedDate.year}"
                    
                    Text(
                        text = monthYearText,
                        style = Typography.labelLarge,
                        color = AppColorScheme.formSecondaryText,
                        fontWeight = FontWeight.Bold
                    )

                    // Show paid amount only if it exists (for payable duties)
                    if (occurrence.paidAmount != null && occurrence.paidAmount > 0) {
                        Spacer(modifier = Modifier.height(Spacing.xs))
                        Text(
                            text = "Amount: R$ ${String.format("%.2f", occurrence.paidAmount)}",
                            style = Typography.bodyLarge,
                            color = AppColorScheme.formSecondaryText,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                
                TextButton(
                    onClick = { onDelete(occurrence.id) },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = AppColorScheme.error
                    )
                ) {
                    Text(stringResource(Res.string.duty_occurrence_list_delete))
                }
            }
        }
    }
}

@Composable
private fun DutyHeaderCard(
    duty: Duty,
    modifier: Modifier = Modifier
) {
    OrganizeCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md)
        ) {
            // Title
            Text(
                text = duty.title,
                style = Typography.headlineSmall,
                color = AppColorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            // Duty Information Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.lg)
            ) {
                // Left Column
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    DutyInfoItem(
                        label = stringResource(Res.string.duty_detail_start_day),
                        value = duty.startDay.toString()
                    )
                    
                    DutyInfoItem(
                        label = stringResource(Res.string.duty_detail_due_day),
                        value = duty.dueDay.toString()
                    )
                }
                
                // Right Column
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    DutyInfoItem(
                        label = stringResource(Res.string.duty_detail_category),
                        value = duty.categoryName.ifEmpty { "N/A" }
                    )
                    
                    DutyInfoItem(
                        label = stringResource(Res.string.duty_detail_type),
                        value = when (duty.type) {
                            DutyType.PAYABLE -> stringResource(Res.string.duty_type_payable)
                            DutyType.ACTIONABLE -> stringResource(Res.string.duty_type_actionable)
                        }
                    )
                    
                    DutyInfoItem(
                        label = stringResource(Res.string.duty_detail_status),
                        value = when (duty.status) {
                            Duty.Status.PENDING -> stringResource(Res.string.status_pending)
                            Duty.Status.PAID -> stringResource(Res.string.status_paid)
                            Duty.Status.OVERDUE -> stringResource(Res.string.status_overdue)
                            Duty.Status.SNOOZED -> "Snoozed"
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun DutyInfoItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = Typography.labelMedium,
            color = AppColorScheme.formSecondaryText
        )
        Spacer(modifier = Modifier.height(Spacing.xs))
        Text(
            text = value,
            style = Typography.bodyRegular,
            color = AppColorScheme.onSurface,
            fontWeight = FontWeight.Medium
        )
    }
}


