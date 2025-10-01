package com.joffer.organizeplus.features.duty.create.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import com.joffer.organizeplus.common.constants.CategoryConstants
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyFormField
import com.joffer.organizeplus.features.duty.create.domain.entities.CreateDutyValidationError
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.category_enterprise
import organizeplus.composeapp.generated.resources.category_personal
import organizeplus.composeapp.generated.resources.close
import organizeplus.composeapp.generated.resources.create_duty_cancel
import organizeplus.composeapp.generated.resources.create_duty_category
import organizeplus.composeapp.generated.resources.create_duty_due_day
import organizeplus.composeapp.generated.resources.create_duty_save
import organizeplus.composeapp.generated.resources.create_duty_start_day
import organizeplus.composeapp.generated.resources.create_duty_title
import organizeplus.composeapp.generated.resources.duty_saved_success
import organizeplus.composeapp.generated.resources.duty_type_actionable
import organizeplus.composeapp.generated.resources.duty_type_label
import organizeplus.composeapp.generated.resources.duty_type_payable
import organizeplus.composeapp.generated.resources.error_category_required
import organizeplus.composeapp.generated.resources.error_due_day_invalid
import organizeplus.composeapp.generated.resources.error_saving
import organizeplus.composeapp.generated.resources.error_start_day_invalid
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
    val successMessage = stringResource(Res.string.duty_saved_success)
    val closeLabel = stringResource(Res.string.close)
    val errorMessage = stringResource(Res.string.error_saving)

    // Handle success snackbar
    LaunchedEffect(uiState.showSuccessSnackbar) {
        if (uiState.showSuccessSnackbar) {
            snackbarHostState.showSnackbar(
                message = successMessage,
                actionLabel = closeLabel,
                duration = SnackbarDuration.Short
            )
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
        topBar = {
            AppTopAppBarWithBackButton(
                title = if (formState.id == null) {
                    stringResource(
                        Res.string.navigation_create_duty_new
                    )
                } else {
                    stringResource(Res.string.navigation_edit_duty_new)
                },
                onBackClick = onNavigateBack,
                backIcon = Icons.Default.ArrowBack,
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
            // Title Field
            FormField(
                label = stringResource(Res.string.create_duty_title),
                value = formState.title,
                onValueChange = { viewModel.onIntent(CreateDutyIntent.UpdateFormField(CreateDutyFormField.Title, it)) },
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
                    viewModel.onIntent(CreateDutyIntent.UpdateFormField(CreateDutyFormField.DutyType, dutyType))
                },
                options = getDutyTypeOptions(),
                isRequired = true,
                isError = uiState.errors.containsKey(CreateDutyFormField.DutyType),
                errorMessage = getErrorMessage(viewModel.getFieldError(CreateDutyFormField.DutyType))
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            // Category Field
            DropdownField(
                label = stringResource(Res.string.create_duty_category),
                value = formState.categoryName,
                onValueChange = {
                    viewModel.onIntent(
                        CreateDutyIntent.UpdateFormField(CreateDutyFormField.CategoryName, it)
                    )
                },
                options = getCategoryOptions(),
                isRequired = true,
                isError = uiState.errors.containsKey(CreateDutyFormField.CategoryName),
                errorMessage = getErrorMessage(viewModel.getFieldError(CreateDutyFormField.CategoryName))
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            // Start Day Field
            OutlinedTextField(
                value = if (formState.startDay == 0) "" else formState.startDay.toString(),
                onValueChange = { value ->
                    viewModel.onIntent(CreateDutyIntent.UpdateFormField(CreateDutyFormField.StartDay, value))
                },
                label = { Text(stringResource(Res.string.create_duty_start_day)) },
                isError = uiState.errors.containsKey(CreateDutyFormField.StartDay),
                supportingText = if (uiState.errors.containsKey(CreateDutyFormField.StartDay)) {
                    { Text(getErrorMessage(viewModel.getFieldError(CreateDutyFormField.StartDay)) ?: "") }
                } else {
                    null
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            // Due Day Field
            OutlinedTextField(
                value = if (formState.dueDay == 0) "" else formState.dueDay.toString(),
                onValueChange = { value ->
                    viewModel.onIntent(CreateDutyIntent.UpdateFormField(CreateDutyFormField.DueDay, value))
                },
                label = { Text(stringResource(Res.string.create_duty_due_day)) },
                isError = uiState.errors.containsKey(CreateDutyFormField.DueDay),
                supportingText = if (uiState.errors.containsKey(CreateDutyFormField.DueDay)) {
                    { Text(getErrorMessage(viewModel.getFieldError(CreateDutyFormField.DueDay)) ?: "") }
                } else {
                    null
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
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
private fun getCategoryOptions(): List<Pair<String, String>> {
    return listOf(
        CategoryConstants.PERSONAL to stringResource(Res.string.category_personal),
        CategoryConstants.COMPANY to stringResource(Res.string.category_enterprise)
    )
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
        CreateDutyValidationError.InvalidStartDay -> stringResource(Res.string.error_start_day_invalid)
        CreateDutyValidationError.InvalidDueDay -> stringResource(Res.string.error_due_day_invalid)
        CreateDutyValidationError.EmptyCategory -> stringResource(Res.string.error_category_required)
        else -> null
    }
}
