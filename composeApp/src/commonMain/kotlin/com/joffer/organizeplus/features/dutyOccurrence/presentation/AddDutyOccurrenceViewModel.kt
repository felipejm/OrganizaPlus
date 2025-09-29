package com.joffer.organizeplus.features.dutyOccurrence.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.features.dutyOccurrence.domain.entities.DutyOccurrenceForm
import com.joffer.organizeplus.features.dutyOccurrence.domain.entities.DutyOccurrenceFormField
import com.joffer.organizeplus.features.dutyOccurrence.domain.validation.ValidationError
import com.joffer.organizeplus.features.dutyOccurrence.domain.usecases.SaveDutyOccurrenceUseCase
import com.joffer.organizeplus.features.dutyOccurrence.domain.validation.DutyOccurrenceValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import io.github.aakira.napier.Napier

class AddDutyOccurrenceViewModel(
    private val saveRecordUseCase: SaveDutyOccurrenceUseCase,
    private val dutyId: String
) : ViewModel() {
    
    private val _formState = MutableStateFlow(DutyOccurrenceForm(dutyId = dutyId))
    val formState: StateFlow<DutyOccurrenceForm> = _formState.asStateFlow()
    
    private val _uiState = MutableStateFlow(AddDutyOccurrenceUiState())
    val uiState: StateFlow<AddDutyOccurrenceUiState> = _uiState.asStateFlow()
    
    fun onIntent(intent: AddDutyOccurrenceIntent) {
        when (intent) {
            is AddDutyOccurrenceIntent.UpdateFormField -> updateFormField(intent.field, intent.value)
            AddDutyOccurrenceIntent.SaveRecord -> saveRecord()
            AddDutyOccurrenceIntent.ClearError -> clearError()
            AddDutyOccurrenceIntent.Retry -> saveRecord()
        }
    }
    
    private fun updateFormField(field: DutyOccurrenceFormField, value: Any) {
        _formState.value = when (field) {
            DutyOccurrenceFormField.PaidAmount -> updatePaidAmount(value)
            DutyOccurrenceFormField.CompletedDate -> updateCompletedDate(value)
        }
        
        _uiState.value = _uiState.value.copy(hasUnsavedChanges = true)
    }
    
    private fun updatePaidAmount(value: Any) = _formState.value.copy(paidAmount = value as Double)
    private fun updateCompletedDate(value: Any) = _formState.value.copy(completedDate = value as kotlinx.datetime.LocalDate)
    
    private fun saveRecord() {
        val form = _formState.value
        
        // Validate form
        val validator = DutyOccurrenceValidator()
        val errors = validator.validate(form)
        
        if (errors.isNotEmpty()) {
            _uiState.value = _uiState.value.copy(
                errors = errors,
                isLoading = false
            )
            return
        }
        
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            errors = emptyMap(),
            errorMessage = null
        )
        
        viewModelScope.launch {
            try {
                val result = saveRecordUseCase(form.toDutyOccurrence())
                result.fold(
                    onSuccess = { savedRecord ->
                        Napier.d("Duty occurrence saved successfully: ${savedRecord.id}")
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            showSuccessMessage = true,
                            hasUnsavedChanges = false
                        )
                    },
                    onFailure = { exception ->
                        Napier.e("Error saving duty occurrence", throwable = exception)
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "Failed to save duty occurrence"
                        )
                    }
                )
            } catch (exception: Exception) {
                Napier.e("Error saving duty occurrence", throwable = exception)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = exception.message ?: "Unknown error occurred"
                )
            }
        }
    }
    
    private fun clearError() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null,
            errors = emptyMap()
        )
    }
    
    fun getFieldError(field: DutyOccurrenceFormField): ValidationError? {
        return _uiState.value.errors[field]
    }
}

data class AddDutyOccurrenceUiState(
    val isLoading: Boolean = false,
    val hasUnsavedChanges: Boolean = false,
    val showSuccessMessage: Boolean = false,
    val errorMessage: String? = null,
    val errors: Map<DutyOccurrenceFormField, ValidationError> = emptyMap()
)

sealed class AddDutyOccurrenceIntent {
    data class UpdateFormField(val field: DutyOccurrenceFormField, val value: Any) : AddDutyOccurrenceIntent()
    object SaveRecord : AddDutyOccurrenceIntent()
    object ClearError : AddDutyOccurrenceIntent()
    object Retry : AddDutyOccurrenceIntent()
}
