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
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyWithLastOccurrence
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.latest_duties_title
import organizeplus.composeapp.generated.resources.view_all_duties
import organizeplus.composeapp.generated.resources.add_duty
import organizeplus.composeapp.generated.resources.no_duties_created_yet
import organizeplus.composeapp.generated.resources.start_creating_first_duty
import organizeplus.composeapp.generated.resources.duty_type_payable
import organizeplus.composeapp.generated.resources.duty_type_actionable
import organizeplus.composeapp.generated.resources.duty_due_every_day
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * Latest duties section showing the 3 most recently created duties
 */
@Composable
fun LatestDutiesSection(
    duties: List<DutyWithLastOccurrence>,
    onViewAll: () -> Unit,
    onAddDuty: () -> Unit,
    modifier: Modifier = Modifier,
    sectionTitle: String? = null
) {
    OrganizeCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(Spacing.xs)
        ) {
            Text(
                text = sectionTitle ?: stringResource(Res.string.latest_duties_title),
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
                    duties.forEach { dutyWithOccurrence ->
                        LatestDutyItem(
                            dutyWithOccurrence = dutyWithOccurrence,
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
    dutyWithOccurrence: DutyWithLastOccurrence,
    modifier: Modifier = Modifier
) {
    val duty = dutyWithOccurrence.duty
    val lastOccurrence = dutyWithOccurrence.lastOccurrence
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
            CategoryIcon(
                categoryName = duty.categoryName
            )
            
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
}
