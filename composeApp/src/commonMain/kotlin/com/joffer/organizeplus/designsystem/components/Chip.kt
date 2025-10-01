package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.joffer.organizeplus.designsystem.typography.Typography
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.priority_high
import organizeplus.composeapp.generated.resources.priority_low
import organizeplus.composeapp.generated.resources.priority_medium
import organizeplus.composeapp.generated.resources.priority_urgent
import organizeplus.composeapp.generated.resources.status_overdue
import organizeplus.composeapp.generated.resources.status_paid
import organizeplus.composeapp.generated.resources.status_pending

@Composable
fun StatusChip(
    status: ObligationStatus,
    modifier: Modifier = Modifier
) {
    val (textResource, containerColor, contentColor) = when (status) {
        ObligationStatus.PENDING -> Triple(
            Res.string.status_pending,
            MaterialTheme.colorScheme.tertiaryContainer,
            MaterialTheme.colorScheme.onTertiaryContainer
        )
        ObligationStatus.PAID -> Triple(
            Res.string.status_paid,
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.onPrimaryContainer
        )
        ObligationStatus.OVERDUE -> Triple(
            Res.string.status_overdue,
            MaterialTheme.colorScheme.errorContainer,
            MaterialTheme.colorScheme.onErrorContainer
        )
    }

    AssistChip(
        onClick = { },
        label = {
            Text(
                text = stringResource(textResource),
                style = Typography.chip,
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
    modifier: Modifier = Modifier
) {
    val (textResource, containerColor, contentColor) = when (priority) {
        ObligationPriority.LOW -> Triple(
            Res.string.priority_low,
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.onSurfaceVariant
        )
        ObligationPriority.MEDIUM -> Triple(
            Res.string.priority_medium,
            MaterialTheme.colorScheme.secondaryContainer,
            MaterialTheme.colorScheme.onSecondaryContainer
        )
        ObligationPriority.HIGH -> Triple(
            Res.string.priority_high,
            MaterialTheme.colorScheme.tertiaryContainer,
            MaterialTheme.colorScheme.onTertiaryContainer
        )
        ObligationPriority.URGENT -> Triple(
            Res.string.priority_urgent,
            MaterialTheme.colorScheme.errorContainer,
            MaterialTheme.colorScheme.onErrorContainer
        )
    }

    AssistChip(
        onClick = { },
        label = {
            Text(
                text = stringResource(textResource),
                style = Typography.chip,
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
    color: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier
) {
    AssistChip(
        onClick = { },
        label = {
            Text(
                text = category,
                style = Typography.chip,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        modifier = modifier,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = color,
            labelColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

enum class ObligationStatus {
    PENDING, PAID, OVERDUE
}

enum class ObligationPriority {
    LOW, MEDIUM, HIGH, URGENT
}
