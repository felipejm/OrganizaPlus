package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@Composable
fun OrganizeTag(
    text: String,
    variant: TagVariant = TagVariant.DEFAULT,
    size: TagSize = TagSize.MEDIUM,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    contentDescription: String? = null
) {
    val currentVariant = if (!enabled) TagVariant.DISABLED else variant
    val colors = getTagColors(currentVariant)
    
    val tagModifier = modifier
        .height(size.height.dp)
        .clip(RoundedCornerShape(size.borderRadius.dp))
        .background(colors.background)
        .then(
            if (colors.border != Color.Transparent) {
                Modifier.border(
                    width = 1.dp,
                    color = colors.border,
                    shape = RoundedCornerShape(size.borderRadius.dp)
                )
            } else {
                Modifier
            }
        )
        .semantics {
            contentDescription?.let { this.contentDescription = it }
        }
    
    Box(modifier = tagModifier) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = size.horizontalPadding.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Leading icon
            leadingIcon?.let { icon ->
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = colors.icon,
                    modifier = Modifier.size(size.iconSize.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            
            // Text
            Text(
                text = text,
                style = TextStyle(
                    fontSize = size.fontSize.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = size.lineHeight.sp,
                    color = colors.text
                )
            )
            
            // Trailing icon
            trailingIcon?.let { icon ->
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = colors.icon,
                    modifier = Modifier.size(size.iconSize.dp)
                )
            }
        }
    }
}

enum class TagSize(
    val height: Int,
    val horizontalPadding: Int,
    val borderRadius: Int,
    val fontSize: Int,
    val lineHeight: Int,
    val iconSize: Int
) {
    LARGE(
        height = 32,
        horizontalPadding = 12,
        borderRadius = 16,
        fontSize = 14,
        lineHeight = 22,
        iconSize = 16
    ),
    MEDIUM(
        height = 28,
        horizontalPadding = 10,
        borderRadius = 14,
        fontSize = 14,
        lineHeight = 22,
        iconSize = 14
    ),
    SMALL(
        height = 24,
        horizontalPadding = 8,
        borderRadius = 12,
        fontSize = 12,
        lineHeight = 18,
        iconSize = 12
    )
}

enum class TagVariant {
    DEFAULT,      // Basic neutral style
    BORDER,       // With border
    FILLED,       // Filled background
    OUTLINED,     // Outlined style
    SUCCESS,      // Success color scheme
    WARNING,      // Warning color scheme
    ERROR,        // Error color scheme
    INFO,         // Info color scheme
    DISABLED      // Disabled state
}

@Composable
private fun getTagColors(variant: TagVariant): TagColors {
    return when (variant) {
        TagVariant.DEFAULT -> TagColors(
            background = AppColorScheme.neutral100,
            border = AppColorScheme.neutral300,
            text = AppColorScheme.neutral700,
            icon = AppColorScheme.neutral500
        )
        TagVariant.BORDER -> TagColors(
            background = AppColorScheme.white,
            border = AppColorScheme.neutral400,
            text = AppColorScheme.neutral700,
            icon = AppColorScheme.neutral500
        )
        TagVariant.FILLED -> TagColors(
            background = AppColorScheme.primary500,
            border = Color.Transparent,
            text = AppColorScheme.white,
            icon = AppColorScheme.white
        )
        TagVariant.OUTLINED -> TagColors(
            background = Color.Transparent,
            border = AppColorScheme.primary500,
            text = AppColorScheme.primary700,
            icon = AppColorScheme.primary500
        )
        TagVariant.SUCCESS -> TagColors(
            background = AppColorScheme.success100,
            border = Color.Transparent,
            text = AppColorScheme.success700,
            icon = AppColorScheme.success700
        )
        TagVariant.WARNING -> TagColors(
            background = AppColorScheme.warning100,
            border = Color.Transparent,
            text = AppColorScheme.warning700,
            icon = AppColorScheme.warning700
        )
        TagVariant.ERROR -> TagColors(
            background = AppColorScheme.danger100,
            border = Color.Transparent,
            text = AppColorScheme.danger700,
            icon = AppColorScheme.danger700
        )
        TagVariant.INFO -> TagColors(
            background = AppColorScheme.info100,
            border = Color.Transparent,
            text = AppColorScheme.info700,
            icon = AppColorScheme.info700
        )
        TagVariant.DISABLED -> TagColors(
            background = AppColorScheme.neutral200,
            border = AppColorScheme.neutral300,
            text = AppColorScheme.neutral500,
            icon = AppColorScheme.neutral400
        )
    }
}

private data class TagColors(
    val background: Color,
    val border: Color,
    val text: Color,
    val icon: Color
)
