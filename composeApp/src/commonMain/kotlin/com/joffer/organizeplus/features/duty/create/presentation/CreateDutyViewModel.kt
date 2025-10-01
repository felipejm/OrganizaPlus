package com.joffer.organizeplus.features.duty.create.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyForm
import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyFormField
import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyValidationError
import com.joffer.organizeplus.features.duty.create.domain.usecases.SaveCreateDutyUseCase
import com.joffer.organizeplus.features.duty.create.domain.validation.CreateDutyValidator
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CreateDutyViewModel(
    private val saveCreateDutyUseCase: SaveCreateDutyUseCase,
    private val dutyRepository: DutyRepository,
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
        when (field) {
            CreateDutyFormField.Title -> _formState.value = updateTitle(value)
            CreateDutyFormField.StartDay -> updateStartDay(value)
            CreateDutyFormField.DueDay -> updateDueDay(value)
            CreateDutyFormField.DutyType -> _formState.value = updateDutyType(value)
            CreateDutyFormField.CategoryName -> _formState.value = updateCategoryName(value)
        }

        _uiState.value = _uiState.value.copy(hasUnsavedChanges = true)
    }

    private fun updateTitle(value: Any) = _formState.value.copy(
        title = (value as String).trim().split(" ").joinToString(" ") { word ->
            word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
    )
    private fun updateStartDay(value: Any) {
        val dayString = value as String
        val day = dayString.toIntOrNull()
        if (day != null && day in 1..31) {
            _formState.value = _formState.value.copy(startDay = day)
        } else if (dayString.isEmpty()) {
            _formState.value = _formState.value.copy(startDay = 0)
        }
    }

    private fun updateDueDay(value: Any) {
        val dayString = value as String
        val day = dayString.toIntOrNull()
        if (day != null && day in 1..31) {
            _formState.value = _formState.value.copy(dueDay = day)
        } else if (dayString.isEmpty()) {
            _formState.value = _formState.value.copy(dueDay = 0)
        }
    }
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

    fun loadExistingDuty(dutyId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            dutyRepository.getDutyById(dutyId)
                .catch { exception ->
                    Napier.e("Error loading duty", exception)
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message,
                        showErrorSnackbar = true
                    )
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { duty ->
                            duty?.let { d ->
                                _formState.value = CreateDutyForm(
                                    id = d.id,
                                    title = d.title,
                                    startDay = d.startDay,
                                    dueDay = d.dueDay,
                                    dutyType = d.type,
                                    categoryName = d.categoryName
                                )
                            }
                            _uiState.value = _uiState.value.copy(isLoading = false)
                        },
                        onFailure = { exception ->
                            Napier.e("Failed to load duty", exception)
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMessage = exception.message,
                                showErrorSnackbar = true
                            )
                        }
                    )
                }
        }
    }
}
