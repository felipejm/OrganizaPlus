package com.joffer.organizeplus.features.duty.create.presentation

import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyForm
import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyFormField
import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyValidationError

data class CreateDutyUiState(
    val isLoading: Boolean = false,
    val errors: Map<CreateDutyFormField, CreateDutyValidationError> = emptyMap(),
    val showCustomRule: Boolean = false,
    val showStartDateReminderOptions: Boolean = false,
    val showDueDateReminderOptions: Boolean = false,
    val hasUnsavedChanges: Boolean = false,
    val showSuccessMessage: Boolean = false,
    val errorMessage: String? = null,
    val showTimePicker: Boolean = false,
    val showErrorSnackbar: Boolean = false,
    val showSuccessSnackbar: Boolean = false
)
