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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography

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

    val (height, padding, typography) = when (size) {
        SelectSize.LARGE -> Triple(48.dp, Spacing.md, Typography.bodyLarge)
        SelectSize.MEDIUM -> Triple(40.dp, Spacing.sm, Typography.body)
        SelectSize.SMALL -> Triple(32.dp, Spacing.xs, Typography.bodySmall)
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
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        color = when {
                            isDisabled -> Color(0xFFFAFAFA) // Neutral 100
                            else -> Color.White
                        }
                    )
                    .border(
                        width = if (expanded) 2.dp else 1.dp,
                        color = when {
                            hasError -> Color(0xFFFB612F) // Danger 500
                            expanded -> Color(0xFF42D9E4) // Primary 500
                            isDisabled -> Color(0xFFF5F5F5) // Neutral 200
                            else -> Color(0xFFC2C2C2) // Neutral 400
                        },
                        shape = RoundedCornerShape(4.dp)
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
                    style = typography,
                    color = when {
                        isDisabled -> Color(0xFF8F8F8F) // Neutral 500
                        selectedOption == null -> Color(0xFF8F8F8F) // Neutral 500
                        else -> Color(0xFF1F1F1F) // Neutral 700
                    },
                    fontWeight = if (selectedOption != null) FontWeight.Medium else FontWeight.Normal
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Dropdown",
                    tint = if (isDisabled) Color(0xFF8F8F8F) else Color(0xFF1F1F1F),
                    modifier = Modifier.size(16.dp)
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
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
                                    style = typography,
                                    color = if (option.disabled) Color(0xFF8F8F8F) else Color(0xFF1F1F1F),
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
                                        tint = Color(0xFF42D9E4), // Primary 500
                                        modifier = Modifier.size(12.dp)
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
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = error ?: helper ?: "",
                style = Typography.caption,
                color = if (error != null) Color(0xFFFB612F) else Color(0xFF8F8F8F),
                modifier = Modifier.padding(horizontal = Spacing.xs)
            )
        }
    }
}
