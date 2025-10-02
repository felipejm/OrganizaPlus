package com.joffer.organizeplus.features.duty.create.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyFormField
import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyValidationError
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.close
import organizeplus.composeapp.generated.resources.create_duty_cancel
import organizeplus.composeapp.generated.resources.create_duty_save
import organizeplus.composeapp.generated.resources.create_duty_title
import organizeplus.composeapp.generated.resources.duty_type_actionable
import organizeplus.composeapp.generated.resources.duty_type_label
import organizeplus.composeapp.generated.resources.duty_type_payable
import organizeplus.composeapp.generated.resources.error_saving
import organizeplus.composeapp.generated.resources.error_title_required
import organizeplus.composeapp.generated.resources.navigation_create_duty_new
import organizeplus.composeapp.generated.resources.navigation_edit_duty_new
import organizeplus.composeapp.generated.resources.placeholder_title
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDutyScreen(
    viewModel: CreateDutyViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val formState by viewModel.formState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Get string resources
    val closeLabel = stringResource(Res.string.close)
    val errorMessage = stringResource(Res.string.error_saving)

    // Handle success - navigate back immediately
    LaunchedEffect(uiState.showSuccessSnackbar) {
        if (uiState.showSuccessSnackbar) {
            onNavigateBack()
        }
    }

    // Handle error snackbar
    LaunchedEffect(uiState.showErrorSnackbar) {
        if (uiState.showErrorSnackbar) {
            snackbarHostState.showSnackbar(
                message = uiState.errorMessage ?: errorMessage,
                actionLabel = closeLabel,
                duration = SnackbarDuration.Long
            )
        }
    }

    Scaffold(
        contentColor = SemanticColors.Background.primary,
        topBar = {
            AppTopAppBarWithBackButton(
                onBackClick = onNavigateBack,
                navigationIconContentColor = AppColorScheme.onSurface
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = Spacing.md)
        ) {

            Text(
                text = if (formState.id == 0L) {
                    stringResource(
                        Res.string.navigation_create_duty_new
                    )
                } else {
                    stringResource(Res.string.navigation_edit_duty_new)
                },
                style = DesignSystemTypography().headlineLarge,
                color = SemanticColors.Foreground.primary,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(Spacing.lg))

            // Title Field
            FormField(
                label = stringResource(Res.string.create_duty_title),
                value = formState.title,
                onValueChange = {
                    viewModel.onIntent(
                        CreateDutyIntent.UpdateFormField(
                            CreateDutyFormField.Title,
                            it
                        )
                    )
                },
                placeholder = stringResource(Res.string.placeholder_title),
                isRequired = true,
                isError = uiState.errors.containsKey(CreateDutyFormField.Title),
                errorMessage = getErrorMessage(viewModel.getFieldError(CreateDutyFormField.Title))
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            // Duty Type Field
            DropdownField(
                label = stringResource(Res.string.duty_type_label),
                value = formState.dutyType.name,
                onValueChange = { selectedType ->
                    val dutyType = when (selectedType) {
                        "PAYABLE" -> DutyType.PAYABLE
                        "ACTIONABLE" -> DutyType.ACTIONABLE
                        else -> DutyType.ACTIONABLE
                    }
                    viewModel.onIntent(
                        CreateDutyIntent.UpdateFormField(
                            CreateDutyFormField.DutyType,
                            dutyType
                        )
                    )
                },
                options = getDutyTypeOptions(),
                isRequired = true,
                isError = uiState.errors.containsKey(CreateDutyFormField.DutyType),
                errorMessage = getErrorMessage(viewModel.getFieldError(CreateDutyFormField.DutyType))
            )

            Spacer(modifier = Modifier.height(Spacing.xl))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.md)
            ) {
                OrganizeSecondaryButton(
                    text = stringResource(Res.string.create_duty_cancel),
                    onClick = {
                        keyboardController?.hide()
                        onNavigateBack()
                    },
                    modifier = Modifier.weight(1f)
                )

                OrganizePrimaryButton(
                    text = stringResource(Res.string.create_duty_save),
                    onClick = {
                        keyboardController?.hide()
                        viewModel.onIntent(CreateDutyIntent.SaveCreateDuty)
                    },
                    modifier = Modifier.weight(1f),
                    enabled = !uiState.isLoading
                )
            }
        }
    }
}

@Composable
private fun getDutyTypeOptions(): List<Pair<String, String>> {
    return listOf(
        DutyType.PAYABLE.name to stringResource(Res.string.duty_type_payable),
        DutyType.ACTIONABLE.name to stringResource(Res.string.duty_type_actionable)
    )
}

@Composable
private fun getErrorMessage(error: CreateDutyValidationError?): String? {
    return when (error) {
        CreateDutyValidationError.EmptyTitle -> stringResource(Res.string.error_title_required)
        else -> null
    }
}
