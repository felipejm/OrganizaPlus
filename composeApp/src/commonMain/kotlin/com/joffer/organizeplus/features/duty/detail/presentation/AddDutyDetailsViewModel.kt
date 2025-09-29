package com.joffer.organizeplus.features.dutyDetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.features.dutyDetails.domain.entities.DutyDetailsForm
import com.joffer.organizeplus.features.dutyDetails.domain.entities.DutyDetailsFormField
import com.joffer.organizeplus.features.dutyDetails.domain.validation.ValidationError
import com.joffer.organizeplus.features.dutyDetails.domain.usecases.SaveDutyDetailsUseCase
import com.joffer.organizeplus.features.dutyDetails.domain.validation.DutyDetailsValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import io.github.aakira.napier.Napier

class AddDutyDetailsViewModel(
    private val saveRecordUseCase: SaveDutyDetailsUseCase,
    private val dutyId: String
) : ViewModel() {
    
    private val _formState = MutableStateFlow(DutyDetailsForm(dutyId = dutyId))
    val formState: StateFlow<DutyDetailsForm> = _formState.asStateFlow()
    
    private val _uiState = MutableStateFlow(AddDutyDetailsUiState())
    val uiState: StateFlow<AddDutyDetailsUiState> = _uiState.asStateFlow()
    
    fun onIntent(intent: AddDutyDetailsIntent) {
        when (intent) {
            is AddDutyDetailsIntent.UpdateFormField -> updateFormField(intent.field, intent.value)
            AddDutyDetailsIntent.SaveRecord -> saveRecord()
            AddDutyDetailsIntent.ClearError -> clearError()
            AddDutyDetailsIntent.Retry -> saveRecord()
        }
    }
    
    private fun updateFormField(field: DutyDetailsFormField, value: Any) {
        _formState.value = when (field) {
            DutyDetailsFormField.PaidAmount -> updatePaidAmount(value)
            DutyDetailsFormField.PaidDate -> updatePaidDate(value)
            DutyDetailsFormField.Notes -> updateNotes(value)
        }
        
        _uiState.value = _uiState.value.copy(hasUnsavedChanges = true)
    }
    
    private fun updatePaidAmount(value: Any) = _formState.value.copy(paidAmount = value as Double)
    private fun updatePaidDate(value: Any) = _formState.value.copy(paidDate = value as kotlinx.datetime.Instant)
    private fun updateNotes(value: Any) = _formState.value.copy(notes = value as String)
    
    private fun saveRecord() {
        val form = _formState.value
        val errors = DutyDetailsValidator.validate(form)
        
        if (errors.isNotEmpty()) {
            _uiState.value = _uiState.value.copy(errors = errors)
            return
        }
        
        _uiState.value = _uiState.value.copy(isLoading = true, errors = emptyMap())
        
        viewModelScope.launch {
            saveRecordUseCase(form)
                .catch { exception ->
                    Napier.e("Error saving record", exception)
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message
                    )
                }
                .collect { result ->
                    result.fold(
                        onSuccess = {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                showSuccessMessage = true,
                                hasUnsavedChanges = false
                            )
                        },
                        onFailure = { exception ->
                            Napier.e("Failed to save record", exception)
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMessage = exception.message
                            )
                        }
                    )
                }
        }
    }
    
    private fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
    
    fun getFieldError(field: DutyDetailsFormField): ValidationError? {
        return _uiState.value.errors[field]
    }
}

data class AddDutyDetailsUiState(
    val isLoading: Boolean = false,
    val hasUnsavedChanges: Boolean = false,
    val showSuccessMessage: Boolean = false,
    val errorMessage: String? = null,
    val errors: Map<DutyDetailsFormField, ValidationError> = emptyMap()
)

sealed class AddDutyDetailsIntent {
    data class UpdateFormField(val field: DutyDetailsFormField, val value: Any) : AddDutyDetailsIntent()
    object SaveRecord : AddDutyDetailsIntent()
    object ClearError : AddDutyDetailsIntent()
    object Retry : AddDutyDetailsIntent()
}
