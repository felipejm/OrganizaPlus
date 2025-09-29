package com.joffer.organizeplus.features.duty.detail.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.common.utils.DateUtils
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.components.ErrorBanner
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.features.duty.detail.domain.entities.DutyDetails
import com.joffer.organizeplus.features.duty.occurrence.presentation.AddDutyOccurrenceBottomSheet
import com.joffer.organizeplus.features.duty.occurrence.presentation.AddDutyOccurrenceViewModel
import com.joffer.organizeplus.features.duty.detail.presentation.AddDutyDetailsViewModel
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
import organizeplus.composeapp.generated.resources.error_loading_occurrences
import organizeplus.composeapp.generated.resources.error_deleting_occurrence

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DutyDetailsListScreen(
    viewModel: DutyDetailsListViewModel,
    onNavigateBack: () -> Unit,
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
        TopAppBar(
            title = {
                Text(
                    text = stringResource(Res.string.duty_occurrence_list_title),
                    style = Typography.titleLarge,
                    color = AppColorScheme.onSurface
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = AppColorScheme.onSurface
                    )
                }
            },
            actions = {
                IconButton(onClick = { showAddOccurrenceBottomSheet = true }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Occurrence",
                        tint = AppColorScheme.primary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = AppColorScheme.surface
            )
        )
        
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = AppColorScheme.primary
                        )
                    }
                }
                
                uiState.error != null -> {
                    ErrorBanner(
                        message = uiState.error ?: "",
                        onRetry = { viewModel.onIntent(DutyDetailsListIntent.Retry) },
                        onDismiss = { viewModel.onIntent(DutyDetailsListIntent.ClearError) }
                    )
                }
                
                uiState.records.isEmpty() -> {
                    EmptyState(
                        title = stringResource(Res.string.duty_occurrence_list_empty_title),
                        subtitle = stringResource(Res.string.duty_occurrence_list_empty_subtitle),
                        onAction = { showAddOccurrenceBottomSheet = true },
                        actionText = stringResource(Res.string.duty_occurrence_list_add_occurrence)
                    )
                }
                
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(Spacing.md),
                        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                    ) {
                        items(uiState.records) { record ->
                            DutyDetailsListItem(
                                record = record,
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
private fun DutyDetailsListItem(
    record: DutyDetails,
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
                    Text(
                        text = stringResource(Res.string.duty_occurrence_list_amount, record.paidAmount),
                        style = Typography.headlineSmall,
                        color = AppColorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    
                    val paidDate = record.paidDate.toLocalDateTime(TimeZone.currentSystemDefault()).date
                    val dateText = "${paidDate.dayOfMonth} ${DateUtils.getMonthName(paidDate.monthNumber)} ${paidDate.year}"
                    
                    Text(
                        text = stringResource(Res.string.duty_occurrence_list_date, dateText),
                        style = Typography.bodyMedium,
                        color = AppColorScheme.formSecondaryText
                    )
                    
                    if (!record.notes.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(Spacing.xs))
                        Text(
                            text = stringResource(Res.string.duty_occurrence_list_notes, record.notes),
                            style = Typography.bodySmall,
                            color = AppColorScheme.formSecondaryText
                        )
                    }
                }
                
                TextButton(
                    onClick = { onDelete(record.id) },
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
private fun EmptyState(
    title: String,
    subtitle: String,
    onAction: () -> Unit,
    actionText: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Spacing.xl),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = Typography.titleMedium,
            color = AppColorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(Spacing.sm))
        Text(
            text = subtitle,
            style = Typography.bodyMedium,
            color = AppColorScheme.formSecondaryText
        )
        Spacer(modifier = Modifier.height(Spacing.md))
        Button(onClick = onAction) {
            Text(actionText)
        }
    }
}
