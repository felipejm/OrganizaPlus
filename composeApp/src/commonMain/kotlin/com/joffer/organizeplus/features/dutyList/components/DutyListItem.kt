package com.joffer.organizeplus.features.dutyList.components

import androidx.compose.foundation.layout.*
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
                
                StatusChip(
                    status = when {
                        duty.isOverdue() -> com.joffer.organizeplus.designsystem.components.ObligationStatus.OVERDUE
                        duty.status == Duty.Status.PAID -> com.joffer.organizeplus.designsystem.components.ObligationStatus.PAID
                        else -> com.joffer.organizeplus.designsystem.components.ObligationStatus.PENDING
                    }
                )
            }
            
            Spacer(modifier = Modifier.height(Spacing.xs))
            
            val dueDate = duty.dueDate.toLocalDateTime(TimeZone.currentSystemDefault()).date
            val dateText = "${dueDate.dayOfMonth} ${DateUtils.getMonthName(dueDate.monthNumber)}"
            
            Text(
                text = dateText,
                style = Typography.secondaryText,
                color = AppColorScheme.formSecondaryText
            )
        }
    }
}
