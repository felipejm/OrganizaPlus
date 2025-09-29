package com.joffer.organizeplus.features.createDuty.presentation

import com.joffer.organizeplus.features.createDuty.domain.entities.CreateDutyForm
import com.joffer.organizeplus.features.createDuty.domain.entities.CreateDutyFormField
import com.joffer.organizeplus.features.createDuty.domain.entities.CreateDutyValidationError

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
