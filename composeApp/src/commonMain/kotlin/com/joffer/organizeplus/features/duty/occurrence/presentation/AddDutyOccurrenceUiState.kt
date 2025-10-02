package com.joffer.organizeplus.features.duty.occurrence.presentation

import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrenceFormField
import com.joffer.organizeplus.features.duty.occurrence.domain.validation.ValidationError

data class AddDutyOccurrenceUiState(
    val isLoading: Boolean = false,
    val hasUnsavedChanges: Boolean = false,
    val showSuccessMessage: Boolean = false,
    val errorMessage: String? = null,
    val errors: Map<DutyOccurrenceFormField, ValidationError> = emptyMap()
)
