package com.joffer.organizeplus.features.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.common.utils.DateUtils
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.components.ResultType
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.utils.CategoryIconProvider
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.latest_duties_title
import organizeplus.composeapp.generated.resources.view_all_duties
import organizeplus.composeapp.generated.resources.add_duty
import organizeplus.composeapp.generated.resources.no_duties_created_yet
import organizeplus.composeapp.generated.resources.start_creating_first_duty
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * Latest duties section showing the 3 most recently created duties
 */
@Composable
fun LatestDutiesSection(
    duties: List<Duty>,
    onViewAll: () -> Unit,
    onAddDuty: () -> Unit,
    onEdit: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OrganizeCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md)
        ) {
            Text(
                text = stringResource(Res.string.latest_duties_title),
                style = Typography.cardTitle,
                color = AppColorScheme.formText
            )
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            if (duties.isEmpty()) {
                // Empty state with add duty button
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    OrganizeResult(
                        type = ResultType.INFO,
                        title = stringResource(Res.string.no_duties_created_yet),
                        description = stringResource(Res.string.start_creating_first_duty),
                        actions = {
                            OrganizePrimaryButton(
                                text = stringResource(Res.string.add_duty),
                                onClick = onAddDuty,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    )
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    duties.forEach { duty ->
                        LatestDutyItem(
                            duty = duty,
                            onEdit = { onEdit(duty.id) }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(Spacing.md))
                
                TextButton(
                    onClick = onViewAll,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(Res.string.view_all_duties),
                        color = AppColorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
private fun LatestDutyItem(
    duty: Duty,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppColorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(Spacing.borderRadius)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.sm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circular icon
            val iconColor = when (duty.categoryName) {
                "4" -> AppColorScheme.iconOrange
                else -> AppColorScheme.iconBlue
            }
            
            val iconContainerColor = when (duty.categoryName) {
                "4" -> AppColorScheme.iconOrangeContainer
                else -> AppColorScheme.iconBlueContainer
            }
            
            val iconText = CategoryIconProvider.getIconForCategory(duty.categoryName)
            
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = iconContainerColor,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = iconText,
                    style = Typography.bodyLarge,
                    color = iconColor
                )
            }
            
            Spacer(modifier = Modifier.width(Spacing.sm))
            
            // Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = duty.title,
                    style = Typography.listItemTitle,
                    color = AppColorScheme.formText
                )
                
                val dueDate = duty.dueDate.toLocalDateTime(TimeZone.currentSystemDefault()).date
                val dateText = "${dueDate.dayOfMonth} ${DateUtils.getMonthName(dueDate.monthNumber)}"
                val subtitle = dateText
                
                Text(
                    text = subtitle,
                    style = Typography.secondaryText,
                    color = AppColorScheme.formSecondaryText
                )
            }
            
            // Status
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.xs)
            ) {
                val status = when {
                    duty.isOverdue() -> ObligationStatus.OVERDUE
                    duty.status == Duty.Status.PAID -> ObligationStatus.PAID
                    else -> ObligationStatus.PENDING
                }
                
                StatusChip(status = status)
            }
        }
    }
}
