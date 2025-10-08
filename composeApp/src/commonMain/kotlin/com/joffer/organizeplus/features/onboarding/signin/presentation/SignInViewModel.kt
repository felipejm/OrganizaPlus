package com.joffer.organizeplus.features.onboarding.signin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.features.onboarding.domain.entities.SignInError
import com.joffer.organizeplus.features.onboarding.domain.usecases.SignInException
import com.joffer.organizeplus.features.onboarding.domain.usecases.SignInUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import organizeplus.composeapp.generated.resources.*

class SignInViewModel(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<SignInNavigationEvent>()
    val navigationEvent: SharedFlow<SignInNavigationEvent> = _navigationEvent.asSharedFlow()

    fun onIntent(intent: SignInIntent) {
        when (intent) {
            is SignInIntent.EmailChanged -> updateEmail(intent.email)
            is SignInIntent.PasswordChanged -> updatePassword(intent.password)
            is SignInIntent.TogglePasswordVisibility -> togglePasswordVisibility()
            is SignInIntent.SignIn -> signIn()
            is SignInIntent.NavigateToSignUp -> navigateToSignUp()
            is SignInIntent.ClearError -> clearError()
        }
    }

    private fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email, emailError = null)
    }

    private fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password, passwordError = null)
    }

    private fun togglePasswordVisibility() {
        _uiState.value = _uiState.value.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
    }

    private fun signIn() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorResId = null)

            val result = signInUseCase(
                email = _uiState.value.email,
                password = _uiState.value.password
            )

            result.fold(
                onSuccess = {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    _navigationEvent.emit(SignInNavigationEvent.NavigateToDashboard)
                },
                onFailure = { exception ->
                    val errorResId = when ((exception as? SignInException)?.error) {
                        SignInError.EMAIL_OR_PASSWORD_EMPTY -> Res.string.error_signin_email_or_password_empty
                        SignInError.EMAIL_NOT_REGISTERED -> Res.string.error_signin_email_not_registered
                        SignInError.INVALID_PASSWORD -> Res.string.error_signin_invalid_password
                        else -> Res.string.error_signin_unknown
                    }

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorResId = errorResId
                    )
                }
            )
        }
    }

    private fun navigateToSignUp() {
        viewModelScope.launch {
            _navigationEvent.emit(SignInNavigationEvent.NavigateToSignUp)
        }
    }

    private fun clearError() {
        _uiState.value = _uiState.value.copy(errorResId = null)
    }
}

sealed class SignInNavigationEvent {
    object NavigateToDashboard : SignInNavigationEvent()
    object NavigateToSignUp : SignInNavigationEvent()
}
