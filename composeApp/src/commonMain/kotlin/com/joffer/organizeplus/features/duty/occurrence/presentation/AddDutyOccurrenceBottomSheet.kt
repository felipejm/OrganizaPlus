package com.joffer.organizeplus.features.duty.occurrence.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrenceFormField
import com.joffer.organizeplus.features.duty.occurrence.domain.validation.ValidationError
import com.joffer.organizeplus.utils.formatDateForDisplay
import com.joffer.organizeplus.utils.parseDateFromString
import com.joffer.organizeplus.utils.showDatePickerDialog
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.add_duty_occurrence_title
import organizeplus.composeapp.generated.resources.close
import organizeplus.composeapp.generated.resources.date_placeholder
import organizeplus.composeapp.generated.resources.duty_occurrence_completed_date
import organizeplus.composeapp.generated.resources.duty_occurrence_paid_amount
import organizeplus.composeapp.generated.resources.duty_occurrence_paid_amount_hint
import organizeplus.composeapp.generated.resources.duty_occurrence_save
import organizeplus.composeapp.generated.resources.duty_occurrence_saved
import organizeplus.composeapp.generated.resources.validation_date_required
import organizeplus.composeapp.generated.resources.validation_field_required
import organizeplus.composeapp.generated.resources.validation_invalid_amount

private const val SUCCESS_MESSAGE_DELAY_MS = 2000L

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDutyOccurrenceBottomSheet(
    viewModel: AddDutyOccurrenceViewModel,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    val formState by viewModel.formState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(uiState.showSuccessMessage) {
        if (uiState.showSuccessMessage) {
            kotlinx.coroutines.delay(SUCCESS_MESSAGE_DELAY_MS)
            onDismiss()
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier.fillMaxWidth(),
        containerColor = SemanticColors.Background.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(Spacing.md)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.add_duty_occurrence_title),
                    style = typography.titleLarge,
                    color = SemanticColors.Foreground.primary
                )
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(Res.string.close),
                        tint = SemanticColors.Foreground.primary
                    )
                }
            }

            // Status Messages
            AddOccurrenceStatusMessages(
                uiState = uiState,
                onRetry = { viewModel.onIntent(AddDutyOccurrenceIntent.Retry) },
                onDismiss = { viewModel.onIntent(AddDutyOccurrenceIntent.ClearError) }
            )

            // Form Fields
            AddOccurrenceFormFields(
                formState = formState,
                uiState = uiState,
                viewModel = viewModel,
                onShowDatePicker = { showDatePicker = true }
            )

            Spacer(modifier = Modifier.height(Spacing.xl))

            // Save Button
            Button(
                onClick = { viewModel.onIntent(AddDutyOccurrenceIntent.SaveRecord) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = SemanticColors.Background.brand
                )
            ) {
                keyboardController?.hide()

                if (uiState.isLoading) {
                    OrganizeProgressIndicatorInline()
                    Spacer(modifier = Modifier.width(Spacing.sm))
                }

                Text(
                    text = if (uiState.showSuccessMessage) {
                        stringResource(Res.string.duty_occurrence_saved)
                    } else {
                        stringResource(Res.string.duty_occurrence_save)
                    },
                    color = SemanticColors.OnBackground.onBrand
                )
            }
        }
    }

    // Date Picker Dialog
    if (showDatePicker) {
        showDatePickerDialog(
            initialDate = formState.completedDate,
            onDateSelected = { selectedDate ->
                viewModel.onIntent(
                    AddDutyOccurrenceIntent.UpdateFormField(
                        DutyOccurrenceFormField.CompletedDate,
                        selectedDate
                    )
                )
                showDatePicker = false
            },
            title = "Select Date",
            doneText = "Done",
            cancelText = "Cancel"
        )
    }
}

@Composable
private fun AddOccurrenceStatusMessages(
    uiState: AddDutyOccurrenceUiState,
    onRetry: () -> Unit,
    onDismiss: () -> Unit
) {
    // Error Banner
    uiState.errorMessage?.let { error ->
        ErrorBanner(
            message = error,
            onRetry = onRetry,
            onDismiss = onDismiss
        )
        Spacer(modifier = Modifier.height(Spacing.md))
    }

    // Success Message
    if (uiState.showSuccessMessage) {
        SuccessBanner(
            message = stringResource(Res.string.duty_occurrence_saved)
        )
        Spacer(modifier = Modifier.height(Spacing.md))
    }
}

@Composable
private fun AddOccurrenceFormFields(
    formState: com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrenceForm,
    uiState: AddDutyOccurrenceUiState,
    viewModel: AddDutyOccurrenceViewModel,
    onShowDatePicker: () -> Unit
) {
    // Only show paid amount field for payable duties
    if (formState.dutyType == DutyType.PAYABLE) {
        AddOccurrencePaidAmountField(
            formState = formState,
            uiState = uiState,
            viewModel = viewModel
        )
        Spacer(modifier = Modifier.height(Spacing.md))
    }

    AddOccurrenceDateField(
        formState = formState,
        uiState = uiState,
        viewModel = viewModel,
        onShowDatePicker = onShowDatePicker
    )
}

@Composable
private fun AddOccurrencePaidAmountField(
    formState: com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrenceForm,
    uiState: AddDutyOccurrenceUiState,
    viewModel: AddDutyOccurrenceViewModel
) {
    FormField(
        label = stringResource(Res.string.duty_occurrence_paid_amount),
        value = formState.paidAmount ?: "",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        onValueChange = {
            viewModel.onIntent(
                AddDutyOccurrenceIntent.UpdateFormField(
                    DutyOccurrenceFormField.PaidAmount,
                    it
                )
            )
        },
        placeholder = stringResource(Res.string.duty_occurrence_paid_amount_hint),
        isRequired = true,
        isError = uiState.errors.containsKey(DutyOccurrenceFormField.PaidAmount),
        errorMessage = getErrorMessage(viewModel.getFieldError(DutyOccurrenceFormField.PaidAmount))
    )
}

@Composable
private fun AddOccurrenceDateField(
    formState: com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrenceForm,
    uiState: AddDutyOccurrenceUiState,
    viewModel: AddDutyOccurrenceViewModel,
    onShowDatePicker: () -> Unit
) {
    DateInputField(
        label = stringResource(Res.string.duty_occurrence_completed_date),
        value = formState.completedDate.formatDateForDisplay(),
        onValueChange = { dateString ->
            val parsedDate = dateString.parseDateFromString()
            if (parsedDate != null) {
                viewModel.onIntent(
                    AddDutyOccurrenceIntent.UpdateFormField(
                        DutyOccurrenceFormField.CompletedDate,
                        parsedDate
                    )
                )
            }
        },
        placeholder = stringResource(Res.string.date_placeholder),
        isRequired = true,
        isError = uiState.errors.containsKey(DutyOccurrenceFormField.CompletedDate),
        errorMessage = getErrorMessage(viewModel.getFieldError(DutyOccurrenceFormField.CompletedDate)),
        onDatePickerClick = onShowDatePicker
    )
}

@Composable
private fun getErrorMessage(error: ValidationError?): String? {
    return when (error) {
        ValidationError.BlankField -> stringResource(Res.string.validation_field_required)
        ValidationError.InvalidAmount -> stringResource(Res.string.validation_invalid_amount)
        ValidationError.InvalidDate -> stringResource(Res.string.validation_date_required)
        null -> null
    }
}
