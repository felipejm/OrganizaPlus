package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@Composable
fun DateInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isRequired: Boolean,
    isError: Boolean,
    errorMessage: String?,
    onDatePickerClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = if (isRequired) "$label *" else label,
            style = typography.labelMedium,
            color = AppColorScheme.formLabel
        )

        Spacer(modifier = Modifier.height(Spacing.xs))

        OutlinedTextField(
            value = value,
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                onDatePickerClick()
                            }
                        }
                    }
                },
            onValueChange = { onValueChange(it) },
            readOnly = true,
            placeholder = {
                Text(
                    text = placeholder,
                    color = AppColorScheme.formPlaceholder
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select Date",
                    tint = AppColorScheme.formPlaceholder
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onDatePickerClick()
                },
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
                style = typography.bodySmall,
                color = AppColorScheme.error
            )
        }
    }
}
