package com.joffer.organizeplus.features.duty.create.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyForm
import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyFormField
import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyValidationError
import com.joffer.organizeplus.features.duty.create.domain.validation.CreateDutyValidator
import com.joffer.organizeplus.features.duty.create.domain.usecases.SaveCreateDutyUseCase
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import io.github.aakira.napier.Napier
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter

class CreateDutyViewModel(
    private val saveCreateDutyUseCase: SaveCreateDutyUseCase,
    private val dutyId: String? = null
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CreateDutyUiState())
    val uiState: StateFlow<CreateDutyUiState> = _uiState.asStateFlow()
    
    private val _formState = MutableStateFlow(CreateDutyForm())
    val formState: StateFlow<CreateDutyForm> = _formState.asStateFlow()
    
    fun onIntent(intent: CreateDutyIntent) {
        when (intent) {
            CreateDutyIntent.SaveCreateDuty -> saveCreateDuty()
            CreateDutyIntent.ClearError -> clearError()
            CreateDutyIntent.ClearSuccess -> clearSuccess()
            
            is CreateDutyIntent.UpdateFormField -> updateFormField(intent.field, intent.value)
        }
    }
    
    private fun updateFormField(field: CreateDutyFormField, value: Any) {
        _formState.value = when (field) {
            CreateDutyFormField.Title -> updateTitle(value)
            CreateDutyFormField.StartDate -> updateStartDate(value)
            CreateDutyFormField.DueDate -> updateDueDate(value)
            CreateDutyFormField.DutyType -> updateDutyType(value)
            CreateDutyFormField.CategoryName -> updateCategoryName(value)
        }
        
        _uiState.value = _uiState.value.copy(hasUnsavedChanges = true)
    }
    
    private fun updateTitle(value: Any) = _formState.value.copy(title = (value as String).trim().split(" ").joinToString(" ") { word -> 
        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    })
    private fun updateStartDate(value: Any) = _formState.value.copy(startDate = value as String)
    private fun updateDueDate(value: Any) = _formState.value.copy(dueDate = value as String)
    private fun updateDutyType(value: Any) = _formState.value.copy(dutyType = value as DutyType)
    private fun updateCategoryName(value: Any) = _formState.value.copy(categoryName = value as String)
    
    fun getFieldError(field: CreateDutyFormField): CreateDutyValidationError? {
        return _uiState.value.errors[field]
    }
    
    private fun saveCreateDuty() {
        viewModelScope.launch {
            val form = _formState.value
            val errors = CreateDutyValidator().validate(form)

            if (errors.isEmpty()) {
                _uiState.value = _uiState.value.copy(isLoading = true)

                saveCreateDutyUseCase(form)
                    .catch { exception ->
                        Napier.e("Error saving obligation", exception)
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = exception.message,
                            showErrorSnackbar = true
                        )
                    }
                    .collect { result ->
                        result.fold(
                            onSuccess = {
                                _uiState.value = _uiState.value.copy(
                                    isLoading = false,
                                    showSuccessSnackbar = true,
                                    hasUnsavedChanges = false
                                )
                            },
                            onFailure = { exception ->
                                Napier.e("Failed to save obligation", exception)
                                _uiState.value = _uiState.value.copy(
                                    isLoading = false,
                                    errorMessage = exception.message,
                                    showErrorSnackbar = true
                                )
                            }
                        )
                    }
            } else {
                _uiState.value = _uiState.value.copy(errors = errors)
            }
        }
    }
    
    
    private fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
    
    private fun clearSuccess() {
        _uiState.value = _uiState.value.copy(showSuccessMessage = false)
    }
    
    fun showStartDatePicker() {
        _uiState.value = _uiState.value.copy(showStartDatePicker = true)
    }
    
    fun showDueDatePicker() {
        _uiState.value = _uiState.value.copy(showDueDatePicker = true)
    }
    
    fun onStartDateSelected(date: LocalDate) {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formattedDate = date.toJavaLocalDate().format(formatter)
        updateFormField(CreateDutyFormField.StartDate, formattedDate)
        _uiState.value = _uiState.value.copy(showStartDatePicker = false)
    }
    
    fun onDueDateSelected(date: LocalDate) {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formattedDate = date.toJavaLocalDate().format(formatter)
        updateFormField(CreateDutyFormField.DueDate, formattedDate)
        _uiState.value = _uiState.value.copy(showDueDatePicker = false)
    }
    
    private fun parseDateString(dateString: String): LocalDate? {
        if (dateString.isEmpty()) return null
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val javaDate = java.time.LocalDate.parse(dateString, formatter)
            LocalDate(javaDate.year, javaDate.monthValue, javaDate.dayOfMonth)
        } catch (e: Exception) {
            null
        }
    }
    
}
