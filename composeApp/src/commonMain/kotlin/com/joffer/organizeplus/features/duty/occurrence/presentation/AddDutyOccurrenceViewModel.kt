package com.joffer.organizeplus.features.duty.occurrence.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrenceForm
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrenceFormField
import com.joffer.organizeplus.features.duty.occurrence.domain.usecases.SaveDutyOccurrenceUseCase
import com.joffer.organizeplus.features.duty.occurrence.domain.validation.DutyOccurrenceValidator
import com.joffer.organizeplus.features.duty.occurrence.domain.validation.ValidationError
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class AddDutyOccurrenceViewModel(
    private val saveRecordUseCase: SaveDutyOccurrenceUseCase,
    private val dutyRepository: DutyRepository,
    private val dutyId: Long
) : ViewModel() {

    private val _formState = MutableStateFlow(DutyOccurrenceForm(dutyId = dutyId))
    val formState: StateFlow<DutyOccurrenceForm> = _formState.asStateFlow()

    private val _uiState = MutableStateFlow(AddDutyOccurrenceUiState())
    val uiState: StateFlow<AddDutyOccurrenceUiState> = _uiState.asStateFlow()

    init {
        loadDutyInfo()
    }

    private fun loadDutyInfo() {
        viewModelScope.launch {
            dutyRepository.getDutyById(dutyId)
                .catch { exception ->
                    handleError("Error loading duty", exception)
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { duty ->
                            duty?.let { updateDutyType(it.type) }
                        },
                        onFailure = { exception ->
                            handleError("Failed to load duty", exception)
                        }
                    )
                }
        }
    }

    private fun updateDutyType(dutyType: com.joffer.organizeplus.features.dashboard.domain.entities.DutyType) {
        _formState.value = _formState.value.copy(dutyType = dutyType)
    }

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
            DutyOccurrenceFormField.PaidAmount -> {
                _formState.value.copy(paidAmount = value as String?)
            }
            DutyOccurrenceFormField.CompletedDate -> {
                _formState.value.copy(completedDate = value as LocalDate)
            }
        }

        _uiState.value = _uiState.value.copy(hasUnsavedChanges = true)
    }

    private fun saveRecord() {
        val form = _formState.value
        val validationErrors = validateForm(form)

        if (validationErrors.isNotEmpty()) {
            _uiState.value = _uiState.value.copy(
                errors = validationErrors,
                isLoading = false
            )
            return
        }

        startSavingProcess()
        performSave(form)
    }

    private fun validateForm(form: DutyOccurrenceForm): Map<DutyOccurrenceFormField, ValidationError> {
        val validator = DutyOccurrenceValidator()
        return validator.validate(form)
    }

    private fun startSavingProcess() {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            errors = emptyMap(),
            errorMessage = null
        )
    }

    private fun performSave(form: DutyOccurrenceForm) {
        viewModelScope.launch {
            try {
                saveRecordUseCase(form.toDutyOccurrence()).fold(
                    onSuccess = { handleSaveSuccess() },
                    onFailure = { exception -> handleSaveError(exception) }
                )
            } catch (exception: Exception) {
                handleSaveError(exception)
            }
        }
    }

    private fun handleSaveSuccess() {
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            showSuccessMessage = true,
            hasUnsavedChanges = false
        )
    }

    private fun handleSaveError(exception: Throwable) {
        Napier.e("Error saving duty occurrence", throwable = exception)
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            errorMessage = exception.message ?: "Failed to save duty occurrence"
        )
    }

    private fun clearError() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null,
            errors = emptyMap()
        )
    }

    private fun handleError(message: String, exception: Throwable) {
        Napier.e(message, exception)
        // For duty loading errors, we don't show UI errors as the form can still work
    }

    fun getFieldError(field: DutyOccurrenceFormField): ValidationError? {
        return _uiState.value.errors[field]
    }
}
