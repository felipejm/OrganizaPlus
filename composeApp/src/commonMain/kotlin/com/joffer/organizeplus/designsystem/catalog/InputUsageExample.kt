package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@Composable
fun InputUsageExample() {
    val typography = DesignSystemTypography()
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

    val isFormValid = firstName.isNotEmpty() &&
        lastName.isNotEmpty() &&
        email.isNotEmpty() &&
        password.isNotEmpty() &&
        confirmPassword.isNotEmpty()

    val passwordMatch = password == confirmPassword

    OrganizeCard {
        Column(
            modifier = Modifier.padding(Spacing.lg)
        ) {
            Text(
                text = "User Registration Form",
                style = typography.headlineMedium,
                color = AppColorScheme.onSurface
            )

            Text(
                text = "Example of the new input component in a real form",
                style = typography.bodyMedium,
                color = AppColorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(Spacing.lg))

            // Personal Information Section
            FormSection(
                title = "Personal Information"
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.md)
                ) {
                    OrganizeInput(
                        label = "First Name",
                        value = firstName,
                        onValueChange = { firstName = it },
                        placeholder = "Enter first name",
                        leadingIcon = Icons.Default.Person,
                        isRequired = true,
                        modifier = Modifier.weight(1f)
                    )

                    OrganizeInput(
                        label = "Last Name",
                        value = lastName,
                        onValueChange = { lastName = it },
                        placeholder = "Enter last name",
                        isRequired = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                OrganizeInput(
                    label = "Email Address",
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Enter email address",
                    leadingIcon = Icons.Default.Email,
                    keyboardType = KeyboardType.Email,
                    isRequired = true,
                    helperText = "We'll use this to send you updates"
                )

                OrganizeInput(
                    label = "Phone Number",
                    value = phone,
                    onValueChange = { phone = it },
                    placeholder = "Enter phone number",
                    leadingIcon = Icons.Default.Phone,
                    keyboardType = KeyboardType.Phone,
                    helperText = "Include country code (optional)"
                )
            }

            Spacer(modifier = Modifier.height(Spacing.lg))

            // Security Section
            FormSection(
                title = "Security"
            ) {
                OrganizeInput(
                    label = "Password",
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Create a strong password",
                    leadingIcon = Icons.Default.Lock,
                    trailingIcon = null, // Using text instead of icon
                    onTrailingIconClick = { showPassword = !showPassword },
                    visualTransformation = if (showPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    isRequired = true,
                    helperText = "Must be at least 8 characters"
                )

                OrganizeInput(
                    label = "Confirm Password",
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = "Confirm your password",
                    leadingIcon = Icons.Default.Lock,
                    trailingIcon = null, // Using text instead of icon
                    onTrailingIconClick = { showConfirmPassword = !showConfirmPassword },
                    visualTransformation = if (showConfirmPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    isRequired = true,
                    state = if (confirmPassword.isNotEmpty() && !passwordMatch) {
                        InputState.ERROR
                    } else {
                        InputState.DEFAULT
                    },
                    errorText = if (confirmPassword.isNotEmpty() && !passwordMatch) {
                        "Passwords don't match"
                    } else {
                        null
                    }
                )
            }

            Spacer(modifier = Modifier.height(Spacing.lg))

            // Additional Information Section
            FormSection(
                title = "Additional Information"
            ) {
                OrganizeInput(
                    label = "Bio",
                    value = bio,
                    onValueChange = { bio = it },
                    placeholder = "Tell us about yourself...",
                    singleLine = false,
                    minLines = 3,
                    maxLines = 6,
                    helperText = "Optional - Share something about yourself"
                )
            }

            Spacer(modifier = Modifier.height(Spacing.lg))

            // Form Actions
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.md)
            ) {
                OrganizeSecondaryButton(
                    text = "Cancel",
                    onClick = { /* Handle cancel */ },
                    modifier = Modifier.weight(1f)
                )

                OrganizePrimaryButton(
                    text = "Create Account",
                    onClick = { /* Handle submit */ },
                    enabled = isFormValid && passwordMatch,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun FormSection(
    title: String,
    content: @Composable () -> Unit
) {
    val typography = DesignSystemTypography()
    Column {
        Text(
            text = title,
            style = typography.titleSmall,
            color = AppColorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(Spacing.sm))

        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            content()
        }
    }
}
