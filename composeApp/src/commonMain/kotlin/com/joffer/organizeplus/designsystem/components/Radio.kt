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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.localTypography
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

data class RadioOption(
    val value: String,
    val label: String,
    val disabled: Boolean = false
)

@Composable
fun OrganizeRadio(
    options: List<RadioOption>,
    selectedValue: String? = null,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    orientation: RadioOrientation = RadioOrientation.VERTICAL
) {
    val arrangement = when (orientation) {
        RadioOrientation.VERTICAL -> Arrangement.spacedBy(Spacing.sm)
        RadioOrientation.HORIZONTAL -> Arrangement.spacedBy(Spacing.lg)
    }

    Column(
        modifier = modifier.semantics { role = androidx.compose.ui.semantics.Role.RadioButton },
        verticalArrangement = arrangement
    ) {
        options.forEach { option ->
            OrganizeRadioItem(
                option = option,
                isSelected = option.value == selectedValue,
                onSelect = { onValueChange(option.value) },
                enabled = enabled && !option.disabled,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun OrganizeRadioItem(
    option: RadioOption,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val typography = localTypography()
    Row(
        modifier = modifier
            .selectable(
                selected = isSelected,
                onClick = onSelect,
                enabled = enabled
            )
            .padding(Spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // Custom radio button
        Box(
            modifier = Modifier
                .size(Spacing.iconSize)
                .clickable(enabled = enabled) { onSelect() }
                .semantics {
                    role = androidx.compose.ui.semantics.Role.RadioButton
                    contentDescription = option.label
                },
            contentAlignment = Alignment.Center
        ) {
            // Outer ring
            Box(
                modifier = Modifier
                    .size(Spacing.Icon.xs)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(
                        width = Spacing.Divider.medium,
                        color = when {
                            !enabled -> AppColorScheme.neutral200
                            isSelected -> AppColorScheme.personalAccent
                            else -> AppColorScheme.neutral400
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Inner dot
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .size(Spacing.sm)
                            .clip(CircleShape)
                            .background(
                                color = if (enabled) AppColorScheme.personalAccent else AppColorScheme.neutral500
                            )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(Spacing.sm))

        Text(
            text = option.label,
            style = typography.bodyMedium,
            color = when {
                !enabled -> AppColorScheme.neutral500
                else -> AppColorScheme.neutral700
            },
            fontWeight = FontWeight.Normal
        )
    }
}

enum class RadioOrientation {
    VERTICAL, HORIZONTAL
}

@Composable
fun OrganizeRadioGroup(
    options: List<RadioOption>,
    selectedValue: String? = null,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    orientation: RadioOrientation = RadioOrientation.VERTICAL,
    label: String? = null
) {
    val typography = localTypography()
    Column(modifier = modifier) {
        if (label != null) {
            Text(
                text = label,
                style = typography.bodyMedium,
                color = AppColorScheme.neutral600,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(bottom = Spacing.xs)
            )
        }

        OrganizeRadio(
            options = options,
            selectedValue = selectedValue,
            onValueChange = onValueChange,
            enabled = enabled,
            orientation = orientation
        )
    }
}
