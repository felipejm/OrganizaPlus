package com.joffer.organizeplus.designsystem.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

enum class BadgeVariant {
    SINGLE_DIGIT,    // 1-9
    DOUBLE_DIGIT,    // 10-99
    DOT             // No number, just a dot
}

enum class BadgeState {
    DEFAULT, HOVER, PRESSED, DISABLED
}

@Composable
fun OrganizeBadge(
    count: Int? = null,
    variant: BadgeVariant = BadgeVariant.SINGLE_DIGIT,
    modifier: Modifier = Modifier,
    state: BadgeState = BadgeState.DEFAULT,
    enabled: Boolean = true,
    contentDescription: String? = null,
    onClick: (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val currentState = when {
        !enabled -> BadgeState.DISABLED
        isPressed -> BadgeState.PRESSED
        else -> state
    }
    
    val colors = getBadgeColors(currentState)
    val scale = animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(100),
        label = "badge_scale"
    )
    
    val displayText = when (variant) {
        BadgeVariant.SINGLE_DIGIT -> {
            when {
                count == null || count <= 0 -> null
                count <= 9 -> count.toString()
                else -> "9+"
            }
        }
        BadgeVariant.DOUBLE_DIGIT -> {
            when {
                count == null || count <= 0 -> null
                count <= 99 -> count.toString()
                else -> "99+"
            }
        }
        BadgeVariant.DOT -> null
    }
    
    val badgeModifier = modifier
        .scale(scale.value)
        .then(
            if (onClick != null && enabled) {
                Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onClick
                    )
                    .semantics {
                        role = Role.Button
                        contentDescription?.let { this.contentDescription = it }
                    }
            } else {
                Modifier.semantics {
                    contentDescription?.let { this.contentDescription = it }
                }
            }
        )
    
    when (variant) {
        BadgeVariant.SINGLE_DIGIT -> {
            SingleDigitBadge(
                text = displayText,
                colors = colors,
                modifier = badgeModifier
            )
        }
        BadgeVariant.DOUBLE_DIGIT -> {
            DoubleDigitBadge(
                text = displayText,
                colors = colors,
                modifier = badgeModifier
            )
        }
        BadgeVariant.DOT -> {
            DotBadge(
                colors = colors,
                modifier = badgeModifier
            )
        }
    }
}

@Composable
private fun SingleDigitBadge(
    text: String?,
    colors: BadgeColors,
    modifier: Modifier = Modifier
) {
    if (text != null) {
        Box(
            modifier = modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(colors.background)
                .padding(3.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 16.sp,
                    color = colors.text,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@Composable
private fun DoubleDigitBadge(
    text: String?,
    colors: BadgeColors,
    modifier: Modifier = Modifier
) {
    if (text != null) {
        Box(
            modifier = modifier
                .width(24.dp)
                .height(20.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colors.background)
                .padding(horizontal = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 16.sp,
                    color = colors.text,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@Composable
private fun DotBadge(
    colors: BadgeColors,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(6.dp)
            .clip(CircleShape)
            .background(colors.background)
    )
}

@Composable
private fun getBadgeColors(state: BadgeState): BadgeColors {
    return when (state) {
        BadgeState.DEFAULT -> BadgeColors(
            background = AppColorScheme.danger500,
            text = AppColorScheme.white
        )
        BadgeState.HOVER -> BadgeColors(
            background = AppColorScheme.danger600,
            text = AppColorScheme.white
        )
        BadgeState.PRESSED -> BadgeColors(
            background = AppColorScheme.danger700,
            text = AppColorScheme.white
        )
        BadgeState.DISABLED -> BadgeColors(
            background = AppColorScheme.neutral300,
            text = AppColorScheme.neutral500
        )
    }
}

private data class BadgeColors(
    val background: Color,
    val text: Color
)

// Convenience composables for common use cases
@Composable
fun NotificationBadge(
    count: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null
) {
    val variant = when {
        count <= 9 -> BadgeVariant.SINGLE_DIGIT
        count <= 99 -> BadgeVariant.DOUBLE_DIGIT
        else -> BadgeVariant.DOUBLE_DIGIT
    }
    
    val contentDescription = when {
        count <= 0 -> null
        count == 1 -> "1 notification"
        count <= 99 -> "$count notifications"
        else -> "99+ notifications"
    }
    
    OrganizeBadge(
        count = count,
        variant = variant,
        modifier = modifier,
        enabled = enabled,
        contentDescription = contentDescription,
        onClick = onClick
    )
}

@Composable
fun StatusBadge(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null
) {
    OrganizeBadge(
        variant = BadgeVariant.DOT,
        modifier = modifier,
        enabled = enabled,
        contentDescription = "New activity",
        onClick = onClick
    )
}

@Composable
fun CounterBadge(
    count: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null
) {
    val variant = when {
        count <= 9 -> BadgeVariant.SINGLE_DIGIT
        count <= 99 -> BadgeVariant.DOUBLE_DIGIT
        else -> BadgeVariant.DOUBLE_DIGIT
    }
    
    val contentDescription = when {
        count <= 0 -> null
        count == 1 -> "1 item"
        count <= 99 -> "$count items"
        else -> "99+ items"
    }
    
    OrganizeBadge(
        count = count,
        variant = variant,
        modifier = modifier,
        enabled = enabled,
        contentDescription = contentDescription,
        onClick = onClick
    )
}
