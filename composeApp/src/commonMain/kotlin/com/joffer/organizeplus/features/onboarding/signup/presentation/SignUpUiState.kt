package com.joffer.organizeplus.features.onboarding.signup.presentation

import org.jetbrains.compose.resources.StringResource

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val errorResId: StringResource? = null,
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null
)

sealed class SignUpIntent {
    data class EmailChanged(val email: String) : SignUpIntent()
    data class PasswordChanged(val password: String) : SignUpIntent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : SignUpIntent()
    object TogglePasswordVisibility : SignUpIntent()
    object ToggleConfirmPasswordVisibility : SignUpIntent()
    object SignUp : SignUpIntent()
    object NavigateToSignIn : SignUpIntent()
    object ClearError : SignUpIntent()
}
