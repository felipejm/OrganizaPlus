package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography

@Composable
fun DateInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "dd/MM/yyyy",
    isRequired: Boolean = false,
    isError: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = if (isRequired) "$label *" else label,
            style = Typography.labelMedium,
            color = AppColorScheme.formLabel
        )
        
        Spacer(modifier = Modifier.height(Spacing.xs))
        
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.length <= 10) {
                    onValueChange(newValue)
                }
            },
            placeholder = {
                Text(
                    text = placeholder,
                    color = AppColorScheme.formPlaceholder
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isError) AppColorScheme.error else AppColorScheme.primary,
                unfocusedBorderColor = if (isError) AppColorScheme.error else AppColorScheme.formBorder,
                focusedTextColor = AppColorScheme.formText,
                unfocusedTextColor = AppColorScheme.formText,
                errorBorderColor = AppColorScheme.error,
                errorTextColor = AppColorScheme.error
            ),
            shape = RoundedCornerShape(Spacing.borderRadius),
            isError = isError,
            singleLine = true
        )
        
        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(Spacing.xs))
            Text(
                text = errorMessage,
                style = Typography.bodySmall,
                color = AppColorScheme.error
            )
        }
    }
}
