package com.joffer.organizeplus.features.duty.occurrence.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrenceFormField
import com.joffer.organizeplus.features.duty.occurrence.domain.validation.ValidationError
import com.joffer.organizeplus.features.duty.occurrence.presentation.AddDutyOccurrenceViewModel
import com.joffer.organizeplus.features.duty.occurrence.presentation.AddDutyOccurrenceIntent
import androidx.compose.ui.Alignment
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.utils.formatDateForDisplay
import com.joffer.organizeplus.utils.parseDateFromString
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.add_duty_occurrence_title
import organizeplus.composeapp.generated.resources.duty_occurrence_paid_amount
import organizeplus.composeapp.generated.resources.duty_occurrence_paid_amount_hint
import organizeplus.composeapp.generated.resources.duty_occurrence_completed_date
import organizeplus.composeapp.generated.resources.duty_occurrence_save
import organizeplus.composeapp.generated.resources.duty_occurrence_saved
import organizeplus.composeapp.generated.resources.validation_invalid_amount
import organizeplus.composeapp.generated.resources.close
import organizeplus.composeapp.generated.resources.date_placeholder
import organizeplus.composeapp.generated.resources.validation_field_required
import organizeplus.composeapp.generated.resources.validation_date_required

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDutyOccurrenceBottomSheet(
    viewModel: AddDutyOccurrenceViewModel,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val formState by viewModel.formState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(uiState.showSuccessMessage) {
        if (uiState.showSuccessMessage) {
            kotlinx.coroutines.delay(2000)
            onDismiss()
        }
    }
    
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier.fillMaxWidth()
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
                    style = Typography.titleLarge,
                    color = AppColorScheme.onSurface
                )
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(Res.string.close),
                        tint = AppColorScheme.onSurface
                    )
                }
            }
            
            // Error Banner
            uiState.errorMessage?.let { error ->
                ErrorBanner(
                    message = error,
                    onRetry = { viewModel.onIntent(AddDutyOccurrenceIntent.Retry) },
                    onDismiss = { viewModel.onIntent(AddDutyOccurrenceIntent.ClearError) }
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
            
            // Form Fields
            FormSection {
                FormField(
                    label = stringResource(Res.string.duty_occurrence_paid_amount),
                    value = formState.paidAmount.toString(),
                    onValueChange = { 
                        val amount = it.toDoubleOrNull() ?: 0.0
                        viewModel.onIntent(AddDutyOccurrenceIntent.UpdateFormField(DutyOccurrenceFormField.PaidAmount, amount))
                    },
                    placeholder = stringResource(Res.string.duty_occurrence_paid_amount_hint),
                    isRequired = true,
                    isError = uiState.errors.containsKey(DutyOccurrenceFormField.PaidAmount),
                    errorMessage = getErrorMessage(viewModel.getFieldError(DutyOccurrenceFormField.PaidAmount))
                )
            }
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            FormSection {
                DateInputField(
                    label = stringResource(Res.string.duty_occurrence_completed_date),
                    value = formState.completedDate.formatDateForDisplay(),
                    onValueChange = { dateString ->
                        val parsedDate = dateString.parseDateFromString()
                        if (parsedDate != null) {
                            viewModel.onIntent(AddDutyOccurrenceIntent.UpdateFormField(DutyOccurrenceFormField.CompletedDate, parsedDate))
                        }
                    },
                    placeholder = stringResource(Res.string.date_placeholder),
                    isRequired = true,
                    isError = uiState.errors.containsKey(DutyOccurrenceFormField.CompletedDate),
                    errorMessage = getErrorMessage(viewModel.getFieldError(DutyOccurrenceFormField.CompletedDate))
                )
            }
            
            Spacer(modifier = Modifier.height(Spacing.xl))
            
            // Save Button
            Button(
                onClick = { viewModel.onIntent(AddDutyOccurrenceIntent.SaveRecord) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColorScheme.primary
                )
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = AppColorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(Spacing.sm))
                }
                Text(
                    text = if (uiState.showSuccessMessage) {
                        stringResource(Res.string.duty_occurrence_saved)
                    } else {
                        stringResource(Res.string.duty_occurrence_save)
                    },
                    color = AppColorScheme.onPrimary
                )
            }
        }
    }
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

