package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@Composable
fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    isRequired: Boolean = false,
    isError: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    val typography = DesignSystemTypography()
    Column(modifier = modifier) {
        Text(
            text = if (isRequired) "$label *" else label,
            style = typography.bodyLarge,
            color = AppColorScheme.formLabel,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(Spacing.xs))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            placeholder = {
                Text(
                    text = placeholder,
                    color = AppColorScheme.formPlaceholder
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppColorScheme.primary,
                unfocusedBorderColor = if (isError) AppColorScheme.error else AppColorScheme.formBorder,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorBorderColor = AppColorScheme.error
            ),
            shape = RoundedCornerShape(Spacing.borderRadius),
            trailingIcon = trailingIcon,
            isError = isError
        )

        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(Spacing.xs))
            Text(
                text = errorMessage,
                style = typography.bodySmall,
                color = AppColorScheme.error
            )
        }
    }
}
