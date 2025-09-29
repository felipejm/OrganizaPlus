package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography

enum class InputSize(
    val height: Int,
    val verticalPadding: Int,
    val horizontalPadding: Int,
    val fontSize: Int,
    val lineHeight: Int
) {
    LARGE(
        height = 48,
        verticalPadding = 12,
        horizontalPadding = 16,
        fontSize = 16,
        lineHeight = 24
    ),
    MEDIUM(
        height = 40,
        verticalPadding = 8,
        horizontalPadding = 16,
        fontSize = 14,
        lineHeight = 22
    ),
    SMALL(
        height = 32,
        verticalPadding = 6,
        horizontalPadding = 12,
        fontSize = 14,
        lineHeight = 22
    )
}

enum class InputState {
    DEFAULT, HOVER, FOCUSED, ERROR, DISABLED
}

@Composable
fun OrganizeInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String = "",
    helperText: String? = null,
    errorText: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    size: InputSize = InputSize.MEDIUM,
    state: InputState = InputState.DEFAULT,
    enabled: Boolean = true,
    isRequired: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val focusRequester = remember { FocusRequester() }
    
    val currentState = when {
        !enabled -> InputState.DISABLED
        errorText != null -> InputState.ERROR
        isFocused -> InputState.FOCUSED
        isHovered -> InputState.HOVER
        else -> state
    }
    
    val colors = getInputColors(currentState)
    val textStyle = getTextStyle(size)
    
    Column(
        modifier = modifier
    ) {
        // Label
        if (label != null) {
            Text(
                text = if (isRequired) "$label *" else label,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 20.sp
                ),
                color = colors.label
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
        
        // Input Container
        Box(
            modifier = Modifier
                .height(size.height.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(colors.background)
                .border(
                    width = 1.dp,
                    color = colors.border,
                    shape = RoundedCornerShape(4.dp)
                )
                .clickable(
                    enabled = enabled,
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    focusRequester.requestFocus()
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = size.horizontalPadding.dp,
                        vertical = size.verticalPadding.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Leading Icon
                if (leadingIcon != null) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = colors.icon,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                
                // Text Field
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester),
                    enabled = enabled,
                    singleLine = singleLine,
                    maxLines = maxLines,
                    minLines = minLines,
                    textStyle = textStyle.copy(
                        color = colors.text
                    ),
                    cursorBrush = SolidColor(colors.text),
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                    keyboardActions = keyboardActions,
                    visualTransformation = visualTransformation,
                    interactionSource = interactionSource,
                    decorationBox = { innerTextField ->
                        if (value.isEmpty() && placeholder.isNotEmpty()) {
                            Text(
                                text = placeholder,
                                style = textStyle.copy(
                                    color = colors.placeholder
                                )
                            )
                        }
                        innerTextField()
                    }
                )
                
                // Trailing Icon
                if (trailingIcon != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = null,
                        tint = colors.icon,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable(
                                enabled = enabled && onTrailingIconClick != null,
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onTrailingIconClick?.invoke()
                            }
                    )
                }
            }
        }
        
        // Helper/Error Text
        if (errorText != null || helperText != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorText ?: helperText ?: "",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 16.sp
                ),
                color = colors.helper
            )
        }
    }
}

@Composable
private fun getInputColors(state: InputState): InputColors {
    return when (state) {
        InputState.DEFAULT -> InputColors(
            border = AppColorScheme.neutral400,
            background = AppColorScheme.white,
            text = AppColorScheme.neutral700,
            placeholder = AppColorScheme.neutral500,
            label = AppColorScheme.neutral600,
            helper = AppColorScheme.neutral500,
            icon = AppColorScheme.neutral500
        )
        InputState.HOVER -> InputColors(
            border = AppColorScheme.primary400,
            background = AppColorScheme.white,
            text = AppColorScheme.neutral700,
            placeholder = AppColorScheme.neutral500,
            label = AppColorScheme.neutral600,
            helper = AppColorScheme.neutral500,
            icon = AppColorScheme.primary400
        )
        InputState.FOCUSED -> InputColors(
            border = AppColorScheme.primary500,
            background = AppColorScheme.white,
            text = AppColorScheme.neutral700,
            placeholder = AppColorScheme.neutral500,
            label = AppColorScheme.primary500,
            helper = AppColorScheme.neutral500,
            icon = AppColorScheme.primary500
        )
        InputState.ERROR -> InputColors(
            border = AppColorScheme.danger500,
            background = AppColorScheme.white,
            text = AppColorScheme.neutral700,
            placeholder = AppColorScheme.neutral500,
            label = AppColorScheme.danger500,
            helper = AppColorScheme.danger500,
            icon = AppColorScheme.danger500
        )
        InputState.DISABLED -> InputColors(
            border = AppColorScheme.neutral200,
            background = AppColorScheme.neutral100,
            text = AppColorScheme.neutral500,
            placeholder = AppColorScheme.neutral400,
            label = AppColorScheme.neutral400,
            helper = AppColorScheme.neutral400,
            icon = AppColorScheme.neutral400
        )
    }
}

@Composable
private fun getTextStyle(size: InputSize): TextStyle {
    return TextStyle(
        fontSize = size.fontSize.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = size.lineHeight.sp
    )
}

private data class InputColors(
    val border: Color,
    val background: Color,
    val text: Color,
    val placeholder: Color,
    val label: Color,
    val helper: Color,
    val icon: Color
)
