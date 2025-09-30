package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography

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
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column(modifier = modifier) {
        Text(
            text = if (isRequired) "$label *" else label,
            style = Typography.bodyMedium,
            color = AppColorScheme.formLabel,
            fontWeight = FontWeight.Medium
        )
        
        Spacer(modifier = Modifier.height(Spacing.xs))
        
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
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
                style = Typography.bodySmall,
                color = AppColorScheme.error
            )
        }
    }
}

@Composable
fun FormTextArea(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    rows: Int = 3,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = Typography.bodyMedium,
            color = AppColorScheme.formLabel,
            fontWeight = FontWeight.Medium
        )
        
        Spacer(modifier = Modifier.height(Spacing.xs))
        
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = AppColorScheme.formPlaceholder
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppColorScheme.primary,
                unfocusedBorderColor = AppColorScheme.formBorder,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(Spacing.borderRadius),
            minLines = rows,
            maxLines = rows
        )
    }
}



