package com.joffer.organizeplus.features.duty.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.features.duty.domain.entities.DutyForm
import com.joffer.organizeplus.features.duty.domain.entities.DutyFormField
import com.joffer.organizeplus.features.duty.domain.entities.ValidationError
import com.joffer.organizeplus.features.duty.presentation.DutyIntent
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.utils.DateFormatter
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.duty_title
import organizeplus.composeapp.generated.resources.duty_due_date
import organizeplus.composeapp.generated.resources.duty_category
import organizeplus.composeapp.generated.resources.duty_reminders
import organizeplus.composeapp.generated.resources.duty_save
import organizeplus.composeapp.generated.resources.duty_cancel
import organizeplus.composeapp.generated.resources.navigation_create_duty
import organizeplus.composeapp.generated.resources.navigation_edit_duty
import organizeplus.composeapp.generated.resources.placeholder_title
import organizeplus.composeapp.generated.resources.placeholder_category
import organizeplus.composeapp.generated.resources.duty_start_date
import organizeplus.composeapp.generated.resources.label_start_date_reminders
import organizeplus.composeapp.generated.resources.label_due_date_reminders
import organizeplus.composeapp.generated.resources.label_days_before
import organizeplus.composeapp.generated.resources.label_time
import organizeplus.composeapp.generated.resources.placeholder_reminder_days
import organizeplus.composeapp.generated.resources.placeholder_reminder_time
import organizeplus.composeapp.generated.resources.category_electricity
import organizeplus.composeapp.generated.resources.category_condo
import organizeplus.composeapp.generated.resources.category_loan
import organizeplus.composeapp.generated.resources.category_das
import organizeplus.composeapp.generated.resources.category_internet
import organizeplus.composeapp.generated.resources.error_title_required
import organizeplus.composeapp.generated.resources.error_start_date_required
import organizeplus.composeapp.generated.resources.error_due_date_required
import organizeplus.composeapp.generated.resources.error_category_required
import organizeplus.composeapp.generated.resources.error_reminder_days_range

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DutyScreen(
    viewModel: DutyViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val formState by viewModel.formState.collectAsState()
    
    LaunchedEffect(uiState.showSuccessMessage) {
        if (uiState.showSuccessMessage) {
            onNavigateBack()
        }
    }
    
    if (uiState.showExitDialog) {
        ExitDialog(
            onConfirm = { onNavigateBack() },
            onDismiss = { viewModel.onIntent(DutyIntent.ClearError) }
        )
    }
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = if (formState.id == null) stringResource(Res.string.navigation_create_duty) else stringResource(Res.string.navigation_edit_duty),
                    style = Typography.titleMedium,
                    color = AppColorScheme.formText
                )
            },
            navigationIcon = {
                IconButton(onClick = { viewModel.onIntent(DutyIntent.CancelForm) }) {
                    Text(
                        text = "←",
                        style = Typography.titleLarge,
                        color = AppColorScheme.formIcon
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = AppColorScheme.surface,
                titleContentColor = AppColorScheme.formText
            )
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(Spacing.md)
        ) {
            
            FormSection {
                    FormField(
                        label = stringResource(Res.string.duty_title),
                        value = formState.title,
                        onValueChange = { viewModel.onIntent(DutyIntent.UpdateFormField(DutyFormField.Title, it)) },
                        placeholder = stringResource(Res.string.placeholder_title),
                        isRequired = true,
                        isError = uiState.errors.containsKey(DutyFormField.Title),
                        errorMessage = getErrorMessage(viewModel.getFieldError(DutyFormField.Title))
                    )
            }
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            FormSection {
                DropdownField(
                    label = "Tipo",
                    value = formState.dutyType.name,
                    options = getDutyTypeOptions(),
                    onValueChange = { viewModel.onIntent(DutyIntent.UpdateFormField(DutyFormField.DutyType, DutyType.valueOf(it))) },
                    placeholder = "Selecione o tipo de tarefa",
                    isRequired = true,
                    isError = uiState.errors.containsKey(DutyFormField.DutyType),
                    errorMessage = getErrorMessage(viewModel.getFieldError(DutyFormField.DutyType))
                )
            }
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            FormSection {
                DropdownField(
                    label = stringResource(Res.string.duty_category),
                    value = formState.categoryId.takeIf { it.isNotBlank() },
                    options = getCategoryOptions(),
                    onValueChange = { viewModel.onIntent(DutyIntent.UpdateFormField(DutyFormField.Category, it)) },
                    placeholder = stringResource(Res.string.placeholder_category),
                    isRequired = true,
                    isError = uiState.errors.containsKey(DutyFormField.Category),
                    errorMessage = getErrorMessage(viewModel.getFieldError(DutyFormField.Category))
                )
            }
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            FormSection {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    DateInputField(
                        label = stringResource(Res.string.duty_start_date),
                        value = formState.startDate,
                        onValueChange = { viewModel.onIntent(DutyIntent.UpdateFormField(DutyFormField.StartDate, it)) },
                        isRequired = true,
                        isError = uiState.errors.containsKey(DutyFormField.StartDate),
                        errorMessage = getErrorMessage(viewModel.getFieldError(DutyFormField.StartDate)),
                        modifier = Modifier.weight(1f)
                    )
                    
                    DateInputField(
                        label = stringResource(Res.string.duty_due_date),
                        value = formState.dueDate,
                        onValueChange = { viewModel.onIntent(DutyIntent.UpdateFormField(DutyFormField.DueDate, it)) },
                        isRequired = true,
                        isError = uiState.errors.containsKey(DutyFormField.DueDate),
                        errorMessage = getErrorMessage(viewModel.getFieldError(DutyFormField.DueDate)),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            FormSection {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.label_start_date_reminders),
                        style = Typography.bodyMedium,
                        color = AppColorScheme.formLabel,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Switch(
                        checked = formState.hasStartDateReminder,
                        onCheckedChange = { viewModel.onIntent(DutyIntent.UpdateFormField(DutyFormField.HasStartDateReminder, it)) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = AppColorScheme.primary,
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = AppColorScheme.formBorder
                        )
                    )
                }
                
                if (uiState.showStartDateReminderOptions) {
                    Spacer(modifier = Modifier.height(Spacing.sm))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                    ) {
                        FormField(
                            label = stringResource(Res.string.label_days_before),
                            value = formState.startDateReminderDaysBefore.toString(),
                            onValueChange = { 
                                it.toIntOrNull()?.let { days ->
                                    viewModel.onIntent(DutyIntent.UpdateFormField(DutyFormField.StartDateReminderDays, days))
                                }
                            },
                            placeholder = stringResource(Res.string.placeholder_reminder_days),
                            isError = uiState.errors.containsKey(DutyFormField.StartDateReminderDays),
                            errorMessage = getErrorMessage(viewModel.getFieldError(DutyFormField.StartDateReminderDays)),
                            modifier = Modifier.weight(1f)
                        )
                        
                        FormField(
                            label = stringResource(Res.string.label_time),
                            value = formState.startDateReminderTime,
                            onValueChange = { viewModel.onIntent(DutyIntent.UpdateFormField(DutyFormField.StartDateReminderTime, it)) },
                            placeholder = stringResource(Res.string.placeholder_reminder_time),
                            isError = uiState.errors.containsKey(DutyFormField.StartDateReminderTime),
                            errorMessage = getErrorMessage(viewModel.getFieldError(DutyFormField.StartDateReminderTime)),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            FormSection {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.label_due_date_reminders),
                        style = Typography.bodyMedium,
                        color = AppColorScheme.formLabel,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Switch(
                        checked = formState.hasDueDateReminder,
                        onCheckedChange = { viewModel.onIntent(DutyIntent.UpdateFormField(DutyFormField.HasDueDateReminder, it)) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = AppColorScheme.primary,
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = AppColorScheme.formBorder
                        )
                    )
                }
                
                if (uiState.showDueDateReminderOptions) {
                    Spacer(modifier = Modifier.height(Spacing.sm))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                    ) {
                        FormField(
                            label = stringResource(Res.string.label_days_before),
                            value = formState.dueDateReminderDaysBefore.toString(),
                            onValueChange = { 
                                it.toIntOrNull()?.let { days ->
                                    viewModel.onIntent(DutyIntent.UpdateFormField(DutyFormField.DueDateReminderDays, days))
                                }
                            },
                            placeholder = stringResource(Res.string.placeholder_reminder_days),
                            isError = uiState.errors.containsKey(DutyFormField.DueDateReminderDays),
                            errorMessage = getErrorMessage(viewModel.getFieldError(DutyFormField.DueDateReminderDays)),
                            modifier = Modifier.weight(1f)
                        )
                        
                        FormField(
                            label = stringResource(Res.string.label_time),
                            value = formState.dueDateReminderTime,
                            onValueChange = { viewModel.onIntent(DutyIntent.UpdateFormField(DutyFormField.DueDateReminderTime, it)) },
                            placeholder = stringResource(Res.string.placeholder_reminder_time),
                            isError = uiState.errors.containsKey(DutyFormField.DueDateReminderTime),
                            errorMessage = getErrorMessage(viewModel.getFieldError(DutyFormField.DueDateReminderTime)),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            
            
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.md),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                Button(
                    onClick = { viewModel.onIntent(DutyIntent.CancelForm) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColorScheme.formBackground
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = stringResource(Res.string.duty_cancel),
                        color = AppColorScheme.formText
                    )
                }
                
                Button(
                    onClick = { viewModel.onIntent(DutyIntent.SaveDuty) },
                    modifier = Modifier.weight(1f),
                    enabled = !uiState.isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColorScheme.primary
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = Color.White
                        )
                    } else {
                        Text(
                            text = "Salvar",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ExitDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Descartar alterações?")
        },
        text = {
            Text("Você tem alterações não salvas. Deseja realmente sair?")
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Sair")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
private fun getCategoryOptions(): List<Pair<String, String>> {
    return listOf(
        "1" to stringResource(Res.string.category_electricity),
        "2" to stringResource(Res.string.category_condo),
        "3" to stringResource(Res.string.category_loan),
        "4" to stringResource(Res.string.category_das),
        "5" to stringResource(Res.string.category_internet)
    )
}

@Composable
private fun getDutyTypeOptions(): List<Pair<String, String>> {
    return listOf(
        DutyType.PAYABLE.name to "Pagável",
        DutyType.ACTIONABLE.name to "Executável"
    )
}


@Composable
private fun getErrorMessage(error: ValidationError?): String? {
    return when (error) {
        ValidationError.EmptyTitle -> stringResource(Res.string.error_title_required)
        ValidationError.EmptyStartDate -> stringResource(Res.string.error_start_date_required)
        ValidationError.EmptyDueDate -> stringResource(Res.string.error_due_date_required)
        ValidationError.EmptyCategory -> stringResource(Res.string.error_category_required)
        ValidationError.InvalidReminderDays -> stringResource(Res.string.error_reminder_days_range)
        null -> null
    }
}
