package com.joffer.organizeplus.features.duty.create.presentation

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
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
    private val dutyId: Long? = null,
    private val categoryName: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateDutyUiState())
    val uiState: StateFlow<CreateDutyUiState> = _uiState.asStateFlow()

    private val _formState = MutableStateFlow(CreateDutyForm())
    val formState: StateFlow<CreateDutyForm> = _formState.asStateFlow()

    init {
        _formState.value = _formState.value.copy(categoryName = categoryName)

        // Automatically load existing duty if dutyId is provided
        dutyId?.let { id ->
            loadExistingDuty(dutyId = id)
        }
    }

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
            CreateDutyFormField.DutyType -> _formState.value = updateDutyType(value)
        }
    }

    private fun updateTitle(value: Any) = _formState.value.copy(
        title = (value as String).capitalize(Locale.current)
    )

    private fun updateDutyType(value: Any) = _formState.value.copy(dutyType = value as DutyType)

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

    private fun loadExistingDuty(dutyId: Long) {
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
                            duty?.let {
                                _formState.value = CreateDutyForm(
                                    id = it.id,
                                    title = it.title,
                                    dutyType = it.type,
                                    categoryName = it.categoryName
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
