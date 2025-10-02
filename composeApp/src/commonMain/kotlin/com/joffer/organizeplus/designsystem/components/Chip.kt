package com.joffer.organizeplus.designsystem.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.status_overdue
import organizeplus.composeapp.generated.resources.status_paid
import organizeplus.composeapp.generated.resources.status_pending

@Composable
fun StatusChip(
    status: ObligationStatus,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    val (textResource, containerColor, contentColor) = when (status) {
        ObligationStatus.PENDING -> Triple(
            Res.string.status_pending,
            SemanticColors.Background.warning,
            SemanticColors.OnBackground.onWarning
        )

        ObligationStatus.PAID -> Triple(
            Res.string.status_paid,
            SemanticColors.Background.success,
            SemanticColors.OnBackground.onSuccess
        )

        ObligationStatus.OVERDUE -> Triple(
            Res.string.status_overdue,
            SemanticColors.Background.error,
            SemanticColors.OnBackground.onError
        )
    }

    AssistChip(
        onClick = { },
        label = {
            Text(
                text = stringResource(textResource),
                style = typography.labelMedium,
                color = contentColor
            )
        },
        modifier = modifier,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = containerColor,
            labelColor = contentColor
        )
    )
}

@Composable
fun PriorityChip(
    priority: ObligationPriority,
    text: String,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    val (containerColor, contentColor) = when (priority) {
        ObligationPriority.LOW -> Pair(
            SemanticColors.Background.quaternary,
            SemanticColors.OnBackground.tertiary
        )

        ObligationPriority.MEDIUM -> Pair(
            SemanticColors.Background.tertiary,
            SemanticColors.OnBackground.secondary
        )

        ObligationPriority.HIGH -> Pair(
            SemanticColors.Background.warning,
            SemanticColors.OnBackground.onWarning
        )

        ObligationPriority.URGENT -> Pair(
            SemanticColors.Background.error,
            SemanticColors.OnBackground.onError
        )
    }

    AssistChip(
        onClick = { },
        label = {
            Text(
                text = text,
                style = typography.labelMedium,
                color = contentColor
            )
        },
        modifier = modifier,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = containerColor,
            labelColor = contentColor
        )
    )
}

@Composable
fun CategoryChip(
    category: String,
    color: Color = SemanticColors.Foreground.brand,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    AssistChip(
        onClick = { },
        label = {
            Text(
                text = category,
                style = typography.labelMedium,
                color = SemanticColors.OnBackground.onBrand
            )
        },
        modifier = modifier,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = color,
            labelColor = SemanticColors.OnBackground.onBrand
        )
    )
}

enum class ObligationStatus {
    PENDING, PAID, OVERDUE
}

enum class ObligationPriority {
    LOW, MEDIUM, HIGH, URGENT
}
