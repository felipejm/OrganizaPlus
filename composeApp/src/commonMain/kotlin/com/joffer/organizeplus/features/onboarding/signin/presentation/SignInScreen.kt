package com.joffer.organizeplus.features.onboarding.signin.presentation

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
fun SignInScreen(
    viewModel: SignInViewModel,
    onNavigateToSignUp: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    HandleNavigation(
        viewModel = viewModel,
        onNavigateToSignUp = onNavigateToSignUp,
        onNavigateToDashboard = onNavigateToDashboard
    )

    SignInContent(
        uiState = uiState,
        onIntent = viewModel::onIntent,
        modifier = modifier
    )
}

@Composable
private fun HandleNavigation(
    viewModel: SignInViewModel,
    onNavigateToSignUp: () -> Unit,
    onNavigateToDashboard: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collectLatest { event ->
            when (event) {
                SignInNavigationEvent.NavigateToDashboard -> onNavigateToDashboard()
                SignInNavigationEvent.NavigateToSignUp -> onNavigateToSignUp()
            }
        }
    }
}

@Composable
private fun SignInContent(
    uiState: SignInUiState,
    onIntent: (SignInIntent) -> Unit,
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

                OnboardingHeader(subtitle = stringResource(Res.string.onboarding_signin_subtitle))

                ErrorSection(
                    errorResId = uiState.errorResId,
                    onDismiss = { onIntent(SignInIntent.ClearError) },
                    onRetry = { onIntent(SignInIntent.SignIn) }
                )

                FormSection(
                    uiState = uiState,
                    onIntent = onIntent
                )

                ActionButtons(
                    isLoading = uiState.isLoading,
                    onSignIn = { onIntent(SignInIntent.SignIn) },
                    onNavigateToSignUp = { onIntent(SignInIntent.NavigateToSignUp) }
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
    uiState: SignInUiState,
    onIntent: (SignInIntent) -> Unit
) {
    EmailField(
        value = uiState.email,
        error = uiState.emailError,
        onChange = { onIntent(SignInIntent.EmailChanged(it)) }
    )

    Spacer(modifier = Modifier.height(Spacing.md))

    PasswordField(
        value = uiState.password,
        error = uiState.passwordError,
        isVisible = uiState.isPasswordVisible,
        onChange = { onIntent(SignInIntent.PasswordChanged(it)) },
        onToggleVisibility = { onIntent(SignInIntent.TogglePasswordVisibility) }
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
        errorMessage = error,
        singleLine = true,
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
    onSignIn: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    val typography = DesignSystemTypography()

    OrganizePrimaryButton(
        text = stringResource(Res.string.onboarding_signin_button),
        onClick = onSignIn,
        enabled = !isLoading,
        modifier = Modifier.fillMaxWidth()
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(Res.string.onboarding_dont_have_account),
            style = typography.bodyMedium,
            color = SemanticColors.Foreground.secondary
        )

        OrganizeTextButton(
            text = stringResource(Res.string.onboarding_signup_button),
            onClick = onNavigateToSignUp,
            enabled = !isLoading
        )
    }
}
