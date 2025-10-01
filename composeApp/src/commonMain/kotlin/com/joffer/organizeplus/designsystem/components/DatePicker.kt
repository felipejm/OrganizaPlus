package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import kotlinx.datetime.LocalDate
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@Composable
fun DatePickerField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    isRequired: Boolean = false,
    isError: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier,
    onDatePickerClick: () -> Unit
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
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onDatePickerClick() },
            readOnly = true,
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = AppColorScheme.onSurface,
                disabledBorderColor = if (isError) AppColorScheme.error else AppColorScheme.formBorder,
                disabledPlaceholderColor = AppColorScheme.formPlaceholder,
                disabledContainerColor = AppColorScheme.surface
            ),
            shape = RoundedCornerShape(Spacing.Radius.sm),
            textStyle = Typography.bodyMedium
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
fun TimePickerField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    isRequired: Boolean = false,
    isError: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier,
    onTimePickerClick: () -> Unit
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
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onTimePickerClick() },
            readOnly = true,
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = AppColorScheme.onSurface,
                disabledBorderColor = if (isError) AppColorScheme.error else AppColorScheme.formBorder,
                disabledPlaceholderColor = AppColorScheme.formPlaceholder,
                disabledContainerColor = AppColorScheme.surface
            ),
            shape = RoundedCornerShape(Spacing.Radius.sm),
            textStyle = Typography.bodyMedium
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

expect fun showDatePicker(
    initialDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
)

expect fun showTimePicker(
    initialHour: Int,
    initialMinute: Int,
    onTimeSelected: (Int, Int) -> Unit
)
