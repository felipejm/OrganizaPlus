package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import kotlinx.coroutines.delay

enum class ResultType {
    SUCCESS, ERROR, WARNING, INFO
}

@Composable
fun OrganizeResult(
    type: ResultType,
    title: String,
    description: String? = null,
    icon: ImageVector? = null,
    actions: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    animated: Boolean = true
) {
    val typography = DesignSystemTypography()
    LaunchedEffect(Unit) {
        if (animated) {
            delay(100) // Small delay for smooth animation
        }
    }

    val (colors, iconVector) = when (type) {
        ResultType.SUCCESS -> SuccessColors to (icon ?: Icons.Default.CheckCircle)
        ResultType.ERROR -> ErrorColors to (icon ?: Icons.Default.Warning)
        ResultType.WARNING -> WarningColors to (icon ?: Icons.Default.Warning)
        ResultType.INFO -> InfoColors to (icon ?: Icons.Default.Info)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .semantics {
                contentDescription = "$title ${description ?: ""}"
            }
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            // Hero Icon
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(colors.ring)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = iconVector,
                    contentDescription = null,
                    tint = colors.icon,
                    modifier = Modifier.size(48.dp)
                )
            }

            // Title
            Text(
                text = title,
                style = typography.headlineMedium,
                color = SemanticColors.Foreground.primary,
                fontWeight = FontWeight.Medium,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            // Description
            if (description != null) {
                Text(
                    text = description,
                    style = typography.bodyMedium,
                    color = SemanticColors.Foreground.secondary,
                    fontWeight = FontWeight.Normal,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            // Actions
            if (actions != null) {
                Spacer(modifier = Modifier.height(Spacing.md))
                actions()
            }
        }
    }
}

@Composable
fun OrganizeResultCard(
    type: ResultType,
    title: String,
    description: String? = null,
    icon: ImageVector? = null,
    actions: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    animated: Boolean = true
) {
    var isVisible by remember { mutableStateOf(!animated) }

    LaunchedEffect(Unit) {
        if (animated) {
            delay(100)
            isVisible = true
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(Spacing.xl)
    ) {
        OrganizeResult(
            type = type,
            title = title,
            description = description,
            icon = icon,
            actions = actions,
            animated = false
        )
    }
}

// Color schemes for each result type
private val SuccessColors = ResultColors(
    icon = SemanticColors.Foreground.success,
    ring = SemanticColors.Background.success
)

private val ErrorColors = ResultColors(
    icon = SemanticColors.Foreground.error,
    ring = SemanticColors.Background.error
)

private val WarningColors = ResultColors(
    icon = SemanticColors.Foreground.warning,
    ring = SemanticColors.Background.warning
)

private val InfoColors = ResultColors(
    icon = SemanticColors.Foreground.info,
    ring = SemanticColors.Background.info
)

private data class ResultColors(
    val icon: Color,
    val ring: Color
)
