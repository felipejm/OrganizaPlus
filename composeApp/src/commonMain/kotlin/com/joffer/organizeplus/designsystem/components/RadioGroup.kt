package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.localTypography
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@Composable
fun <T> RadioGroup(
    options: List<Pair<T, String>>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit,
    label: String? = null,
    modifier: Modifier = Modifier
) {
    val typography = localTypography()
    Column(
        modifier = modifier.selectableGroup()
    ) {
        label?.let {
            Text(
                text = it,
                style = typography.bodyMedium,
                color = AppColorScheme.formLabel,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(Spacing.sm))
        }
        options.forEach { (option, label) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = option == selectedOption,
                        onClick = { onOptionSelected(option) },
                        role = Role.RadioButton
                    )
                    .padding(vertical = Spacing.xs),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = option == selectedOption,
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = AppColorScheme.primary,
                        unselectedColor = AppColorScheme.formBorder
                    )
                )

                Spacer(modifier = Modifier.width(Spacing.sm))

                Text(
                    text = label,
                    style = typography.bodyMedium,
                    color = AppColorScheme.formLabel,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
