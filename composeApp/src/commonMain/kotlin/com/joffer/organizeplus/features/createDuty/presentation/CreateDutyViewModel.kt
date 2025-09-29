package com.joffer.organizeplus.features.createDuty.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.features.createDuty.domain.entities.CreateDutyForm
import com.joffer.organizeplus.features.createDuty.domain.entities.CreateDutyFormField
import com.joffer.organizeplus.features.createDuty.domain.entities.CreateDutyValidationError
import com.joffer.organizeplus.features.createDuty.domain.validation.CreateDutyValidator
import com.joffer.organizeplus.features.createDuty.domain.usecases.SaveCreateDutyUseCase
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import io.github.aakira.napier.Napier

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
            CreateDutyIntent.CancelForm -> cancelForm()
            CreateDutyIntent.ClearError -> clearError()
            CreateDutyIntent.ClearSuccess -> clearSuccess()
            CreateDutyIntent.ClearErrorSnackbar -> clearErrorSnackbar()
            CreateDutyIntent.ClearSuccessSnackbar -> clearSuccessSnackbar()
            CreateDutyIntent.ShowCustomRule -> showCustomRule()
            CreateDutyIntent.HideCustomRule -> hideCustomRule()
            CreateDutyIntent.ShowStartDateReminderOptions -> showStartDateReminderOptions()
            CreateDutyIntent.HideStartDateReminderOptions -> hideStartDateReminderOptions()
            CreateDutyIntent.ShowDueDateReminderOptions -> showDueDateReminderOptions()
            CreateDutyIntent.HideDueDateReminderOptions -> hideDueDateReminderOptions()
            CreateDutyIntent.ShowTimePicker -> showTimePicker()
            CreateDutyIntent.HideTimePicker -> hideTimePicker()
            
            is CreateDutyIntent.UpdateFormField -> updateFormField(intent.field, intent.value)
            is CreateDutyIntent.SelectTime -> selectTime(intent.time)
        }
    }
    
    private fun updateFormField(field: CreateDutyFormField, value: Any) {
        _formState.value = when (field) {
            CreateDutyFormField.Title -> updateTitle(value)
            CreateDutyFormField.StartDate -> updateStartDate(value)
            CreateDutyFormField.DueDate -> updateDueDate(value)
            CreateDutyFormField.DutyType -> updateDutyType(value)
            CreateDutyFormField.CategoryName -> updateCategoryName(value)
            CreateDutyFormField.HasStartDateReminder -> updateStartDateReminder(value)
            CreateDutyFormField.StartDateReminderDays -> updateStartDateReminderDays(value)
            CreateDutyFormField.StartDateReminderTime -> updateStartDateReminderTime(value)
            CreateDutyFormField.HasDueDateReminder -> updateDueDateReminder(value)
            CreateDutyFormField.DueDateReminderDays -> updateDueDateReminderDays(value)
            CreateDutyFormField.DueDateReminderTime -> updateDueDateReminderTime(value)
        }
        
        _uiState.value = _uiState.value.copy(hasUnsavedChanges = true)
    }
    
    private fun updateTitle(value: Any) = _formState.value.copy(title = value as String)
    private fun updateStartDate(value: Any) = _formState.value.copy(startDate = value as String)
    private fun updateDueDate(value: Any) = _formState.value.copy(dueDate = value as String)
    private fun updateDutyType(value: Any) = _formState.value.copy(dutyType = value as DutyType)
    private fun updateCategoryName(value: Any) = _formState.value.copy(categoryName = value as String)
    private fun updateStartDateReminder(value: Any) = _formState.value.copy(hasStartDateReminder = value as Boolean)
    private fun updateStartDateReminderDays(value: Any) = _formState.value.copy(startDateReminderDaysBefore = value as Int)
    private fun updateStartDateReminderTime(value: Any) = _formState.value.copy(startDateReminderTime = value as String)
    private fun updateDueDateReminder(value: Any) = _formState.value.copy(hasDueDateReminder = value as Boolean)
    private fun updateDueDateReminderDays(value: Any) = _formState.value.copy(dueDateReminderDaysBefore = value as Int)
    private fun updateDueDateReminderTime(value: Any) = _formState.value.copy(dueDateReminderTime = value as String)
    
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
    
    private fun cancelForm() {
        _uiState.value = _uiState.value.copy(showSuccessMessage = true)
    }
    
    private fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
    
    private fun clearSuccess() {
        _uiState.value = _uiState.value.copy(showSuccessMessage = false)
    }
    
    private fun clearErrorSnackbar() {
        _uiState.value = _uiState.value.copy(showErrorSnackbar = false, errorMessage = null)
    }
    
    private fun clearSuccessSnackbar() {
        _uiState.value = _uiState.value.copy(showSuccessSnackbar = false)
    }
    
    private fun showCustomRule() {
        _uiState.value = _uiState.value.copy(showCustomRule = true)
    }
    
    private fun hideCustomRule() {
        _uiState.value = _uiState.value.copy(showCustomRule = false)
    }
    
    private fun showStartDateReminderOptions() {
        _uiState.value = _uiState.value.copy(showStartDateReminderOptions = true)
    }
    
    private fun hideStartDateReminderOptions() {
        _uiState.value = _uiState.value.copy(showStartDateReminderOptions = false)
    }
    
    private fun showDueDateReminderOptions() {
        _uiState.value = _uiState.value.copy(showDueDateReminderOptions = true)
    }
    
    private fun hideDueDateReminderOptions() {
        _uiState.value = _uiState.value.copy(showDueDateReminderOptions = false)
    }
    
    
    private fun showTimePicker() {
        _uiState.value = _uiState.value.copy(showTimePicker = true)
    }
    
    private fun hideTimePicker() {
        _uiState.value = _uiState.value.copy(showTimePicker = false)
    }
    
    
    
    private fun selectTime(time: String) {
        _uiState.value = _uiState.value.copy(showTimePicker = false)
        _formState.value = _formState.value.copy(startDateReminderTime = time)
        _uiState.value = _uiState.value.copy(hasUnsavedChanges = true)
    }
}
