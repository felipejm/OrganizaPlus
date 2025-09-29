package com.joffer.organizeplus.features.duty.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.features.duty.domain.entities.DutyForm
import com.joffer.organizeplus.features.duty.domain.entities.DutyFormField
import com.joffer.organizeplus.features.duty.domain.entities.ValidationError
import com.joffer.organizeplus.features.duty.domain.validation.DutyValidator
import com.joffer.organizeplus.features.duty.domain.usecases.SaveDutyUseCase
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import io.github.aakira.napier.Napier

class DutyViewModel(
    private val saveDutyUseCase: SaveDutyUseCase,
    private val dutyId: String? = null
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DutyUiState())
    val uiState: StateFlow<DutyUiState> = _uiState.asStateFlow()
    
    private val _formState = MutableStateFlow(DutyForm())
    val formState: StateFlow<DutyForm> = _formState.asStateFlow()
    
    fun onIntent(intent: DutyIntent) {
        when (intent) {
            DutyIntent.SaveDuty -> saveDuty()
            DutyIntent.CancelForm -> cancelForm()
            DutyIntent.ClearError -> clearError()
            DutyIntent.ClearSuccess -> clearSuccess()
            DutyIntent.ShowCustomRule -> showCustomRule()
            DutyIntent.HideCustomRule -> hideCustomRule()
            DutyIntent.ShowStartDateReminderOptions -> showStartDateReminderOptions()
            DutyIntent.HideStartDateReminderOptions -> hideStartDateReminderOptions()
            DutyIntent.ShowDueDateReminderOptions -> showDueDateReminderOptions()
            DutyIntent.HideDueDateReminderOptions -> hideDueDateReminderOptions()
            DutyIntent.ShowTimePicker -> showTimePicker()
            DutyIntent.HideTimePicker -> hideTimePicker()
            DutyIntent.ConfirmExit -> confirmExit()
            DutyIntent.CancelExit -> cancelExit()
            
            is DutyIntent.UpdateFormField -> updateFormField(intent.field, intent.value)
            is DutyIntent.SelectTime -> selectTime(intent.time)
        }
    }
    
    private fun updateFormField(field: DutyFormField, value: Any) {
        _formState.value = when (field) {
            DutyFormField.Title -> updateTitle(value)
            DutyFormField.StartDate -> updateStartDate(value)
            DutyFormField.DueDate -> updateDueDate(value)
            DutyFormField.DutyType -> updateDutyType(value)
            DutyFormField.Category -> updateCategory(value)
            DutyFormField.Priority -> updatePriority(value)
            DutyFormField.HasStartDateReminder -> updateStartDateReminder(value)
            DutyFormField.StartDateReminderDays -> updateStartDateReminderDays(value)
            DutyFormField.StartDateReminderTime -> updateStartDateReminderTime(value)
            DutyFormField.HasDueDateReminder -> updateDueDateReminder(value)
            DutyFormField.DueDateReminderDays -> updateDueDateReminderDays(value)
            DutyFormField.DueDateReminderTime -> updateDueDateReminderTime(value)
        }
        
        _uiState.value = _uiState.value.copy(hasUnsavedChanges = true)
    }
    
    private fun updateTitle(value: Any) = _formState.value.copy(title = value as String)
    private fun updateStartDate(value: Any) = _formState.value.copy(startDate = value as String)
    private fun updateDueDate(value: Any) = _formState.value.copy(dueDate = value as String)
    private fun updateDutyType(value: Any) = _formState.value.copy(dutyType = value as DutyType)
    private fun updateCategory(value: Any) = _formState.value.copy(categoryId = value as String)
    private fun updatePriority(value: Any) = _formState.value.copy(priority = value as DutyForm.Priority)
    private fun updateStartDateReminder(value: Any) = _formState.value.copy(hasStartDateReminder = value as Boolean)
    private fun updateStartDateReminderDays(value: Any) = _formState.value.copy(startDateReminderDaysBefore = value as Int)
    private fun updateStartDateReminderTime(value: Any) = _formState.value.copy(startDateReminderTime = value as String)
    private fun updateDueDateReminder(value: Any) = _formState.value.copy(hasDueDateReminder = value as Boolean)
    private fun updateDueDateReminderDays(value: Any) = _formState.value.copy(dueDateReminderDaysBefore = value as Int)
    private fun updateDueDateReminderTime(value: Any) = _formState.value.copy(dueDateReminderTime = value as String)
    
    fun getFieldError(field: DutyFormField): ValidationError? {
        return _uiState.value.errors[field]
    }
    
    private fun saveDuty() {
        viewModelScope.launch {
            val form = _formState.value
            val errors = DutyValidator().validate(form)

            if (errors.isEmpty()) {
                _uiState.value = _uiState.value.copy(isLoading = true)

                saveDutyUseCase(form)
                    .catch { exception ->
                        Napier.e("Error saving obligation", exception)
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
                                Napier.e("Failed to save obligation", exception)
                                _uiState.value = _uiState.value.copy(
                                    isLoading = false,
                                    errorMessage = exception.message
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
        if (_uiState.value.hasUnsavedChanges) {
            _uiState.value = _uiState.value.copy(showExitDialog = true)
        } else {
            _uiState.value = _uiState.value.copy(showSuccessMessage = true)
        }
    }
    
    private fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
    
    private fun clearSuccess() {
        _uiState.value = _uiState.value.copy(showSuccessMessage = false)
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
    
    private fun confirmExit() {
        _uiState.value = _uiState.value.copy(showExitDialog = false)
        _uiState.value = _uiState.value.copy(showSuccessMessage = true)
    }
    
    private fun cancelExit() {
        _uiState.value = _uiState.value.copy(showExitDialog = false)
    }
    
    
    private fun selectTime(time: String) {
        _uiState.value = _uiState.value.copy(showTimePicker = false)
        _formState.value = _formState.value.copy(startDateReminderTime = time)
        _uiState.value = _uiState.value.copy(hasUnsavedChanges = true)
    }
}