package com.joffer.organizeplus.features.duty.list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.components.OrganizeCard
import com.joffer.organizeplus.designsystem.components.StatusChip
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.utils.CategoryIconProvider
import com.joffer.organizeplus.common.utils.DateUtils
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun DutyListItem(
    duty: Duty,
    onViewOccurrences: (String) -> Unit,
    onDelete: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OrganizeCard(
        modifier = modifier.fillMaxWidth(),
        onClick = { onViewOccurrences(duty.id) }
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = CategoryIconProvider.getIconForCategory(duty.categoryName),
                        style = Typography.headlineMedium,
                        modifier = Modifier.padding(end = Spacing.xs)
                    )

                    Text(
                        text = duty.title,
                        style = Typography.listItemTitle,
                        color = AppColorScheme.formText
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spacing.xs)
                ) {
                    StatusChip(
                        status = when {
                            duty.isOverdue() -> com.joffer.organizeplus.designsystem.components.ObligationStatus.OVERDUE
                            duty.status == Duty.Status.PAID -> com.joffer.organizeplus.designsystem.components.ObligationStatus.PAID
                            else -> com.joffer.organizeplus.designsystem.components.ObligationStatus.PENDING
                        }
                    )

                    IconButton(
                        onClick = { onDelete(duty.id) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Duty",
                            tint = AppColorScheme.error,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(Spacing.xs))

            val dateText = "Day ${duty.dueDay}"

            Text(
                text = dateText,
                style = Typography.secondaryText,
                color = AppColorScheme.formSecondaryText
            )
        }
    }
}
