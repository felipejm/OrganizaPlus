package com.joffer.organizeplus.features.duty.list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.components.OrganizeCard
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyWithLastOccurrence
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.common.utils.DateUtils
import com.joffer.organizeplus.designsystem.components.CategoryIcon
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.duty_type_payable
import organizeplus.composeapp.generated.resources.duty_type_actionable
import organizeplus.composeapp.generated.resources.duty_due_every_day

@Composable
fun DutyListItem(
    dutyWithOccurrence: DutyWithLastOccurrence,
    onViewOccurrences: (String) -> Unit,
    onDelete: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val duty = dutyWithOccurrence.duty
    val lastOccurrence = dutyWithOccurrence.lastOccurrence
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
                    CategoryIcon(
                        categoryName = duty.categoryName
                    )
                    Spacer(modifier = Modifier.width(Spacing.sm))
                    Text(
                        text = duty.title,
                        style = Typography.listItemTitle,
                        color = AppColorScheme.formText
                    )
                }

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

            Spacer(modifier = Modifier.height(Spacing.xs))

            // Category and Type
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                Text(
                    text = duty.categoryName,
                    style = Typography.labelSmall,
                    color = AppColorScheme.formSecondaryText
                )
                
                Text(
                    text = "•",
                    style = Typography.labelSmall,
                    color = AppColorScheme.formSecondaryText
                )
                
                Text(
                    text = when (duty.type) {
                        DutyType.PAYABLE -> stringResource(Res.string.duty_type_payable)
                        DutyType.ACTIONABLE -> stringResource(Res.string.duty_type_actionable)
                    },
                    style = Typography.labelSmall,
                    color = AppColorScheme.formSecondaryText
                )
            }

            Spacer(modifier = Modifier.height(Spacing.xs))

            // Due day
            Text(
                text = String.format(stringResource(Res.string.duty_due_every_day), duty.dueDay),
                style = Typography.secondaryText,
                color = AppColorScheme.formSecondaryText
            )
            
            // Last occurrence info
            lastOccurrence?.let { occurrence ->
                Spacer(modifier = Modifier.height(Spacing.xs))
                Text(
                    text = "Last: ${DateUtils.getMonthName(occurrence.completedDate.monthNumber)} ${occurrence.completedDate.year}",
                    style = Typography.labelSmall,
                    color = AppColorScheme.primary
                )
            }
        }
    }
}
