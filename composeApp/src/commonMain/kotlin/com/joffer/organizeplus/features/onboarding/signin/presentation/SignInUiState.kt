package com.joffer.organizeplus.features.onboarding.signin.presentation

import org.jetbrains.compose.resources.StringResource

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorResId: StringResource? = null,
    val isPasswordVisible: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null
)

sealed class SignInIntent {
    data class EmailChanged(val email: String) : SignInIntent()
    data class PasswordChanged(val password: String) : SignInIntent()
    object TogglePasswordVisibility : SignInIntent()
    object SignIn : SignInIntent()
    object NavigateToSignUp : SignInIntent()
    object ClearError : SignInIntent()
}
