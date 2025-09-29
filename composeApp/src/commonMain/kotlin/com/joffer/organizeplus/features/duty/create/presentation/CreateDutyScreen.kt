package com.joffer.organizeplus.features.duty.create.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyForm
import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyFormField
import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyValidationError
import com.joffer.organizeplus.features.duty.create.presentation.CreateDutyIntent
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.utils.DateFormatter
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.create_duty_title
import organizeplus.composeapp.generated.resources.create_duty_due_date
import organizeplus.composeapp.generated.resources.create_duty_category
import organizeplus.composeapp.generated.resources.create_duty_reminders
import organizeplus.composeapp.generated.resources.create_duty_save
import organizeplus.composeapp.generated.resources.create_duty_cancel
import organizeplus.composeapp.generated.resources.navigation_create_duty_new
import organizeplus.composeapp.generated.resources.navigation_edit_duty_new
import organizeplus.composeapp.generated.resources.placeholder_title
import organizeplus.composeapp.generated.resources.placeholder_category
import organizeplus.composeapp.generated.resources.create_duty_start_date
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
import organizeplus.composeapp.generated.resources.duty_type_label
import organizeplus.composeapp.generated.resources.duty_type_payable
import organizeplus.composeapp.generated.resources.duty_type_actionable
import organizeplus.composeapp.generated.resources.error_saving
import organizeplus.composeapp.generated.resources.duty_saved_success
import organizeplus.composeapp.generated.resources.back_arrow
import organizeplus.composeapp.generated.resources.placeholder_date
import organizeplus.composeapp.generated.resources.close

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDutyScreen(
    viewModel: CreateDutyViewModel,
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
    
    Scaffold(
        topBar = {
            AppTopAppBarWithBackButton(
                title = if (formState.id == null) stringResource(Res.string.navigation_create_duty_new) else stringResource(Res.string.navigation_edit_duty_new),
                onBackClick = { viewModel.onIntent(CreateDutyIntent.CancelForm) }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = remember { SnackbarHostState() }) {
                when {
                    uiState.showErrorSnackbar -> {
                        LaunchedEffect(Unit) {
                            viewModel.onIntent(CreateDutyIntent.ClearErrorSnackbar)
                        }
                        ErrorSnackbar(
                            message = uiState.errorMessage ?: stringResource(Res.string.error_saving),
                            actionLabel = stringResource(Res.string.close),
                            onActionClick = { viewModel.onIntent(CreateDutyIntent.ClearErrorSnackbar) }
                        )
                    }
                    uiState.showSuccessSnackbar -> {
                        LaunchedEffect(Unit) {
                            viewModel.onIntent(CreateDutyIntent.ClearSuccessSnackbar)
                        }
                        SuccessSnackbar(
                            message = stringResource(Res.string.duty_saved_success),
                            actionLabel = stringResource(Res.string.close),
                            onActionClick = { viewModel.onIntent(CreateDutyIntent.ClearSuccessSnackbar) }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(Spacing.md)
        ) {
            // Title Field
            FormField(
                label = stringResource(Res.string.create_duty_title),
                value = formState.title,
                onValueChange = { viewModel.onIntent(CreateDutyIntent.UpdateFormField(CreateDutyFormField.Title, it)) },
                placeholder = stringResource(Res.string.placeholder_title),
                isRequired = true,
                isError = uiState.errors.containsKey(CreateDutyFormField.Title),
                errorMessage = getErrorMessage(viewModel.getFieldError(CreateDutyFormField.Title))
            )
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            // Duty Type Field
            DropdownField(
                label = stringResource(Res.string.duty_type_label),
                value = formState.dutyType.name,
                onValueChange = { selectedType ->
                    val dutyType = when (selectedType) {
                        "PAYABLE" -> DutyType.PAYABLE
                        "ACTIONABLE" -> DutyType.ACTIONABLE
                        else -> DutyType.ACTIONABLE
                    }
                    viewModel.onIntent(CreateDutyIntent.UpdateFormField(CreateDutyFormField.DutyType, dutyType))
                },
                options = getDutyTypeOptions(),
                isRequired = true,
                isError = uiState.errors.containsKey(CreateDutyFormField.DutyType),
                errorMessage = getErrorMessage(viewModel.getFieldError(CreateDutyFormField.DutyType))
            )
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            // Category Field
            DropdownField(
                label = stringResource(Res.string.create_duty_category),
                value = formState.categoryName,
                onValueChange = { viewModel.onIntent(CreateDutyIntent.UpdateFormField(CreateDutyFormField.CategoryName, it)) },
                options = getCategoryOptions(),
                isRequired = true,
                isError = uiState.errors.containsKey(CreateDutyFormField.CategoryName),
                errorMessage = getErrorMessage(viewModel.getFieldError(CreateDutyFormField.CategoryName))
            )
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            // Start Date Field
            DateInputField(
                label = stringResource(Res.string.create_duty_start_date),
                value = formState.startDate,
                onValueChange = { viewModel.onIntent(CreateDutyIntent.UpdateFormField(CreateDutyFormField.StartDate, it)) },
                placeholder = stringResource(Res.string.placeholder_date),
                isRequired = true,
                isError = uiState.errors.containsKey(CreateDutyFormField.StartDate),
                errorMessage = getErrorMessage(viewModel.getFieldError(CreateDutyFormField.StartDate))
            )
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            // Due Date Field
            DateInputField(
                label = stringResource(Res.string.create_duty_due_date),
                value = formState.dueDate,
                onValueChange = { viewModel.onIntent(CreateDutyIntent.UpdateFormField(CreateDutyFormField.DueDate, it)) },
                placeholder = stringResource(Res.string.placeholder_date),
                isRequired = true,
                isError = uiState.errors.containsKey(CreateDutyFormField.DueDate),
                errorMessage = getErrorMessage(viewModel.getFieldError(CreateDutyFormField.DueDate))
            )
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            
            // Reminders Section
            Text(
                text = stringResource(Res.string.create_duty_reminders),
                style = Typography.titleMedium,
                color = AppColorScheme.formText,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            // Start Date Reminders
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = formState.hasStartDateReminder,
                    onCheckedChange = { viewModel.onIntent(CreateDutyIntent.UpdateFormField(CreateDutyFormField.HasStartDateReminder, it)) }
                )
                Spacer(modifier = Modifier.width(Spacing.sm))
                Text(
                    text = stringResource(Res.string.label_start_date_reminders),
                    style = Typography.bodyMedium,
                    color = AppColorScheme.formText
                )
            }
            
            if (formState.hasStartDateReminder) {
                Spacer(modifier = Modifier.height(Spacing.sm))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    FormField(
                        label = stringResource(Res.string.label_days_before),
                        value = formState.startDateReminderDaysBefore.toString(),
                        onValueChange = { 
                            val days = it.toIntOrNull() ?: 0
                            viewModel.onIntent(CreateDutyIntent.UpdateFormField(CreateDutyFormField.StartDateReminderDays, days))
                        },
                        placeholder = stringResource(Res.string.placeholder_reminder_days),
                        isRequired = true,
                        isError = uiState.errors.containsKey(CreateDutyFormField.StartDateReminderDays),
                        errorMessage = getErrorMessage(viewModel.getFieldError(CreateDutyFormField.StartDateReminderDays)),
                        modifier = Modifier.weight(1f)
                    )
                    
                    FormField(
                        label = stringResource(Res.string.label_time),
                        value = formState.startDateReminderTime,
                        onValueChange = { viewModel.onIntent(CreateDutyIntent.UpdateFormField(CreateDutyFormField.StartDateReminderTime, it)) },
                        placeholder = stringResource(Res.string.placeholder_reminder_time),
                        isRequired = true,
                        isError = uiState.errors.containsKey(CreateDutyFormField.StartDateReminderTime),
                        errorMessage = getErrorMessage(viewModel.getFieldError(CreateDutyFormField.StartDateReminderTime)),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            // Due Date Reminders
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = formState.hasDueDateReminder,
                    onCheckedChange = { viewModel.onIntent(CreateDutyIntent.UpdateFormField(CreateDutyFormField.HasDueDateReminder, it)) }
                )
                Spacer(modifier = Modifier.width(Spacing.sm))
                Text(
                    text = stringResource(Res.string.label_due_date_reminders),
                    style = Typography.bodyMedium,
                    color = AppColorScheme.formText
                )
            }
            
            if (formState.hasDueDateReminder) {
                Spacer(modifier = Modifier.height(Spacing.sm))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    FormField(
                        label = stringResource(Res.string.label_days_before),
                        value = formState.dueDateReminderDaysBefore.toString(),
                        onValueChange = { 
                            val days = it.toIntOrNull() ?: 0
                            viewModel.onIntent(CreateDutyIntent.UpdateFormField(CreateDutyFormField.DueDateReminderDays, days))
                        },
                        placeholder = stringResource(Res.string.placeholder_reminder_days),
                        isRequired = true,
                        isError = uiState.errors.containsKey(CreateDutyFormField.DueDateReminderDays),
                        errorMessage = getErrorMessage(viewModel.getFieldError(CreateDutyFormField.DueDateReminderDays)),
                        modifier = Modifier.weight(1f)
                    )
                    
                    FormField(
                        label = stringResource(Res.string.label_time),
                        value = formState.dueDateReminderTime,
                        onValueChange = { viewModel.onIntent(CreateDutyIntent.UpdateFormField(CreateDutyFormField.DueDateReminderTime, it)) },
                        placeholder = stringResource(Res.string.placeholder_reminder_time),
                        isRequired = true,
                        isError = uiState.errors.containsKey(CreateDutyFormField.DueDateReminderTime),
                        errorMessage = getErrorMessage(viewModel.getFieldError(CreateDutyFormField.DueDateReminderTime)),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(Spacing.xl))
            
            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.md)
            ) {
                Button(
                    onClick = { viewModel.onIntent(CreateDutyIntent.CancelForm) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColorScheme.surface,
                        contentColor = AppColorScheme.formText
                    )
                ) {
                    Text(stringResource(Res.string.create_duty_cancel))
                }
                
                Button(
                    onClick = { viewModel.onIntent(CreateDutyIntent.SaveCreateDuty) },
                    modifier = Modifier.weight(1f),
                    enabled = !uiState.isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColorScheme.primary,
                        contentColor = AppColorScheme.onPrimary
                    )
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = AppColorScheme.onPrimary
                        )
                    } else {
                        Text(
                            text = stringResource(Res.string.create_duty_save),
                            color = AppColorScheme.onPrimary
                        )
                    }
                }
            }
        }
        }
    }
}


@Composable
private fun getCategoryOptions(): List<Pair<String, String>> {
    return listOf(
        stringResource(Res.string.category_electricity) to stringResource(Res.string.category_electricity),
        stringResource(Res.string.category_condo) to stringResource(Res.string.category_condo),
        stringResource(Res.string.category_loan) to stringResource(Res.string.category_loan),
        stringResource(Res.string.category_das) to stringResource(Res.string.category_das),
        stringResource(Res.string.category_internet) to stringResource(Res.string.category_internet)
    )
}

@Composable
private fun getDutyTypeOptions(): List<Pair<String, String>> {
    return listOf(
        DutyType.PAYABLE.name to stringResource(Res.string.duty_type_payable),
        DutyType.ACTIONABLE.name to stringResource(Res.string.duty_type_actionable)
    )
}


@Composable
private fun getErrorMessage(error: CreateDutyValidationError?): String? {
    return when (error) {
        CreateDutyValidationError.EmptyTitle -> stringResource(Res.string.error_title_required)
        CreateDutyValidationError.EmptyStartDate -> stringResource(Res.string.error_start_date_required)
        CreateDutyValidationError.EmptyDueDate -> stringResource(Res.string.error_due_date_required)
        CreateDutyValidationError.EmptyCategory -> stringResource(Res.string.error_category_required)
        CreateDutyValidationError.InvalidReminderDays -> stringResource(Res.string.error_reminder_days_range)
        else -> null
    }
}