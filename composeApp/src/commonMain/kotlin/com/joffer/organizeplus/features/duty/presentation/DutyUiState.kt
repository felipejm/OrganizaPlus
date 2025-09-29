package com.joffer.organizeplus.features.duty.presentation

import com.joffer.organizeplus.features.duty.domain.entities.DutyForm
import com.joffer.organizeplus.features.duty.domain.entities.DutyFormField
import com.joffer.organizeplus.features.duty.domain.entities.ValidationError

data class DutyUiState(
    val isLoading: Boolean = false,
    val errors: Map<DutyFormField, ValidationError> = emptyMap(),
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
