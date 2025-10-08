package com.joffer.organizeplus.features.onboarding.signup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.features.onboarding.domain.entities.SignUpError
import com.joffer.organizeplus.features.onboarding.domain.usecases.SignUpException
import com.joffer.organizeplus.features.onboarding.domain.usecases.SignUpUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import organizeplus.composeapp.generated.resources.*

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<SignUpNavigationEvent>()
    val navigationEvent: SharedFlow<SignUpNavigationEvent> = _navigationEvent.asSharedFlow()

    fun onIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.EmailChanged -> updateEmail(intent.email)
            is SignUpIntent.PasswordChanged -> updatePassword(intent.password)
            is SignUpIntent.ConfirmPasswordChanged -> updateConfirmPassword(intent.confirmPassword)
            is SignUpIntent.TogglePasswordVisibility -> togglePasswordVisibility()
            is SignUpIntent.ToggleConfirmPasswordVisibility -> toggleConfirmPasswordVisibility()
            is SignUpIntent.SignUp -> signUp()
            is SignUpIntent.NavigateToSignIn -> navigateToSignIn()
            is SignUpIntent.ClearError -> clearError()
        }
    }

    private fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email, emailError = null)
    }

    private fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password, passwordError = null)
    }

    private fun updateConfirmPassword(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword, confirmPasswordError = null)
    }

    private fun togglePasswordVisibility() {
        _uiState.value = _uiState.value.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
    }

    private fun toggleConfirmPasswordVisibility() {
        _uiState.value = _uiState.value.copy(isConfirmPasswordVisible = !_uiState.value.isConfirmPasswordVisible)
    }

    private fun signUp() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorResId = null)

            val result = signUpUseCase(
                email = _uiState.value.email,
                password = _uiState.value.password,
                confirmPassword = _uiState.value.confirmPassword
            )

            result.fold(
                onSuccess = {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    _navigationEvent.emit(SignUpNavigationEvent.NavigateToDashboard)
                },
                onFailure = { exception ->
                    val errorResId = when ((exception as? SignUpException)?.error) {
                        SignUpError.EMAIL_EMPTY -> Res.string.error_signup_email_empty
                        SignUpError.EMAIL_INVALID -> Res.string.error_signup_email_invalid
                        SignUpError.PASSWORD_EMPTY -> Res.string.error_signup_password_empty
                        SignUpError.PASSWORD_TOO_SHORT -> Res.string.error_signup_password_too_short
                        SignUpError.PASSWORDS_DO_NOT_MATCH -> Res.string.error_signup_passwords_do_not_match
                        SignUpError.EMAIL_ALREADY_REGISTERED -> Res.string.error_signup_email_already_registered
                        else -> Res.string.error_signup_unknown
                    }

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorResId = errorResId
                    )
                }
            )
        }
    }

    private fun navigateToSignIn() {
        viewModelScope.launch {
            _navigationEvent.emit(SignUpNavigationEvent.NavigateToSignIn)
        }
    }

    private fun clearError() {
        _uiState.value = _uiState.value.copy(errorResId = null)
    }
}

sealed class SignUpNavigationEvent {
    object NavigateToDashboard : SignUpNavigationEvent()
    object NavigateToSignIn : SignUpNavigationEvent()
}
