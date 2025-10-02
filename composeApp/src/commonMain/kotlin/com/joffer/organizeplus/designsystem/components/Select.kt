package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.localTypography
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

enum class SelectSize {
    LARGE, MEDIUM, SMALL
}

data class SelectOption(
    val value: String,
    val label: String,
    val disabled: Boolean = false
)

@Composable
fun OrganizeSelect(
    options: List<SelectOption>,
    selectedValue: String? = null,
    placeholder: String = "Select an option",
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    size: SelectSize = SelectSize.MEDIUM,
    enabled: Boolean = true,
    error: String? = null,
    helper: String? = null
) {
    var expanded by remember { mutableStateOf(false) }
    val localTypography = localTypography()

    val (height, padding, textStyle) = when (size) {
        SelectSize.LARGE -> Triple(Spacing.iconSize + Spacing.lg, Spacing.md, localTypography.bodyLarge)
        SelectSize.MEDIUM -> Triple(Spacing.buttonHeight, Spacing.sm, localTypography.bodyMedium)
        SelectSize.SMALL -> Triple(Spacing.iconSize, Spacing.xs, localTypography.bodySmall)
    }

    val selectedOption = options.find { it.value == selectedValue }
    val hasError = error != null
    val isDisabled = !enabled

    Column(modifier = modifier) {
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .clip(RoundedCornerShape(Spacing.Radius.xs))
                    .background(
                        color = when {
                            isDisabled -> AppColorScheme.neutral100
                            else -> AppColorScheme.white
                        }
                    )
                    .border(
                        width = if (expanded) Spacing.Divider.medium else Spacing.Divider.thin,
                        color = when {
                            hasError -> AppColorScheme.danger500
                            expanded -> AppColorScheme.personalAccent
                            isDisabled -> AppColorScheme.neutral200
                            else -> AppColorScheme.neutral400
                        },
                        shape = RoundedCornerShape(Spacing.Radius.xs)
                    )
                    .clickable(enabled = enabled) { expanded = !expanded }
                    .padding(horizontal = padding, vertical = Spacing.sm)
                    .semantics {
                        role = androidx.compose.ui.semantics.Role.Button
                        contentDescription = selectedOption?.label ?: placeholder
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = selectedOption?.label ?: placeholder,
                    style = textStyle,
                    color = when {
                        isDisabled -> AppColorScheme.neutral500
                        selectedOption == null -> AppColorScheme.neutral500
                        else -> AppColorScheme.neutral700
                    },
                    fontWeight = if (selectedOption != null) FontWeight.Medium else FontWeight.Normal
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Dropdown",
                    tint = if (isDisabled) AppColorScheme.neutral500 else AppColorScheme.neutral700,
                    modifier = Modifier.size(Spacing.Icon.xs)
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = AppColorScheme.white,
                        shape = RoundedCornerShape(Spacing.Radius.sm)
                    )
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = option.label,
                                    style = textStyle,
                                    color = if (option.disabled) {
                                        AppColorScheme.neutral500
                                    } else {
                                        AppColorScheme.neutral700
                                    },
                                    fontWeight = if (option.value == selectedValue) {
                                        FontWeight.Medium
                                    } else {
                                        FontWeight.Normal
                                    }
                                )

                                if (option.value == selectedValue) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected",
                                        tint = AppColorScheme.personalAccent,
                                        modifier = Modifier.size(Spacing.md)
                                    )
                                }
                            }
                        },
                        onClick = {
                            if (!option.disabled) {
                                onValueChange(option.value)
                                expanded = false
                            }
                        },
                        enabled = !option.disabled
                    )
                }
            }
        }

        // Helper text or error message
        if (error != null || helper != null) {
            Spacer(modifier = Modifier.height(Spacing.xs))
            Text(
                text = error ?: helper ?: "",
                style = localTypography.caption,
                color = if (error != null) AppColorScheme.danger500 else AppColorScheme.neutral500,
                modifier = Modifier.padding(horizontal = Spacing.xs)
            )
        }
    }
}
