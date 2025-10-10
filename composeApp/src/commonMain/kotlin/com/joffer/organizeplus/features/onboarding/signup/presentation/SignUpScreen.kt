package com.joffer.organizeplus.features.onboarding.signup.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.icons.OrganizeIcons
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.*

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    onNavigateToSignIn: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    HandleNavigation(
        viewModel = viewModel,
        onNavigateToSignIn = onNavigateToSignIn,
        onNavigateToDashboard = onNavigateToDashboard
    )

    SignUpContent(
        uiState = uiState,
        onIntent = viewModel::onIntent,
        modifier = modifier
    )
}

@Composable
private fun HandleNavigation(
    viewModel: SignUpViewModel,
    onNavigateToSignIn: () -> Unit,
    onNavigateToDashboard: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collectLatest { event ->
            when (event) {
                SignUpNavigationEvent.NavigateToDashboard -> onNavigateToDashboard()
                SignUpNavigationEvent.NavigateToSignIn -> onNavigateToSignIn()
            }
        }
    }
}

@Composable
private fun SignUpContent(
    uiState: SignUpUiState,
    onIntent: (SignUpIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = SemanticColors.Background.primary
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Spacing.md)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    focusManager.clearFocus()
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Spacing.lg, Alignment.CenterVertically)
            ) {
                Spacer(modifier = Modifier.weight(1f))

                OnboardingHeader(subtitle = stringResource(Res.string.onboarding_signup_subtitle))

                ErrorSection(
                    errorResId = uiState.errorResId,
                    onDismiss = { onIntent(SignUpIntent.ClearError) },
                    onRetry = { onIntent(SignUpIntent.SignUp) }
                )

                FormSection(
                    uiState = uiState,
                    onIntent = onIntent
                )

                ActionButtons(
                    isLoading = uiState.isLoading,
                    onSignUp = { onIntent(SignUpIntent.SignUp) },
                    onNavigateToSignIn = { onIntent(SignUpIntent.NavigateToSignIn) }
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = SemanticColors.Background.brand
                )
            }
        }
    }
}

@Composable
private fun ErrorSection(
    errorResId: org.jetbrains.compose.resources.StringResource?,
    onDismiss: () -> Unit,
    onRetry: () -> Unit
) {
    if (errorResId != null) {
        ErrorBanner(
            message = stringResource(errorResId),
            onRetry = onRetry,
            onDismiss = onDismiss
        )
        Spacer(modifier = Modifier.height(Spacing.md))
    }
}

@Composable
private fun FormSection(
    uiState: SignUpUiState,
    onIntent: (SignUpIntent) -> Unit
) {
    EmailField(
        value = uiState.email,
        error = uiState.emailError,
        onChange = { onIntent(SignUpIntent.EmailChanged(it)) }
    )

    PasswordField(
        value = uiState.password,
        error = uiState.passwordError,
        isVisible = uiState.isPasswordVisible,
        onChange = { onIntent(SignUpIntent.PasswordChanged(it)) },
        onToggleVisibility = { onIntent(SignUpIntent.TogglePasswordVisibility) }
    )

    ConfirmPasswordField(
        value = uiState.confirmPassword,
        error = uiState.confirmPasswordError,
        isVisible = uiState.isConfirmPasswordVisible,
        onChange = { onIntent(SignUpIntent.ConfirmPasswordChanged(it)) },
        onToggleVisibility = { onIntent(SignUpIntent.ToggleConfirmPasswordVisibility) }
    )
}

@Composable
private fun EmailField(
    value: String,
    error: String?,
    onChange: (String) -> Unit
) {
    FormField(
        label = stringResource(Res.string.onboarding_email_label),
        value = value,
        onValueChange = onChange,
        placeholder = stringResource(Res.string.onboarding_email_placeholder),
        isRequired = true,
        isError = error != null,
        singleLine = true,
        errorMessage = error,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun PasswordField(
    value: String,
    error: String?,
    isVisible: Boolean,
    onChange: (String) -> Unit,
    onToggleVisibility: () -> Unit
) {
    FormField(
        label = stringResource(Res.string.onboarding_password_label),
        value = value,
        onValueChange = onChange,
        placeholder = stringResource(Res.string.onboarding_password_placeholder),
        isRequired = true,
        isError = error != null,
        errorMessage = error,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            PasswordVisibilityToggle(
                isVisible = isVisible,
                onToggle = onToggleVisibility
            )
        }
    )
}

@Composable
private fun ConfirmPasswordField(
    value: String,
    error: String?,
    isVisible: Boolean,
    onChange: (String) -> Unit,
    onToggleVisibility: () -> Unit
) {
    FormField(
        label = stringResource(Res.string.onboarding_confirm_password_label),
        value = value,
        onValueChange = onChange,
        singleLine = true,
        placeholder = stringResource(Res.string.onboarding_confirm_password_placeholder),
        isRequired = true,
        isError = error != null,
        errorMessage = error,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            PasswordVisibilityToggle(
                isVisible = isVisible,
                onToggle = onToggleVisibility
            )
        }
    )
}

@Composable
private fun PasswordVisibilityToggle(
    isVisible: Boolean,
    onToggle: () -> Unit
) {
    IconButton(onClick = onToggle) {
        Icon(
            imageVector = if (isVisible) OrganizeIcons.Actions.Visibility else OrganizeIcons.Actions.VisibilityOff,
            contentDescription = if (isVisible) {
                stringResource(Res.string.onboarding_hide_password)
            } else {
                stringResource(Res.string.onboarding_show_password)
            },
            tint = SemanticColors.Foreground.secondary
        )
    }
}

@Composable
private fun ActionButtons(
    isLoading: Boolean,
    onSignUp: () -> Unit,
    onNavigateToSignIn: () -> Unit
) {
    val typography = DesignSystemTypography()

    OrganizePrimaryButton(
        text = stringResource(Res.string.onboarding_signup_button),
        onClick = onSignUp,
        enabled = !isLoading,
        modifier = Modifier.fillMaxWidth()
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(Res.string.onboarding_already_have_account),
            style = typography.bodyMedium,
            color = SemanticColors.Foreground.secondary
        )

        OrganizeTextButton(
            text = stringResource(Res.string.onboarding_signin_button),
            onClick = onNavigateToSignIn,
            enabled = !isLoading
        )
    }
}
