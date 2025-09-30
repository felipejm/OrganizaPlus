package com.joffer.organizeplus.features.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joffer.organizeplus.common.utils.DateUtils
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.components.ResultType
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyWithLastOccurrence
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.dashboard.MonthlySummary
import com.joffer.organizeplus.common.utils.CurrencyUtils
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
    onDeleteDuty: (String) -> Unit,
    modifier: Modifier = Modifier,
    sectionTitle: String? = null,
    monthlySummary: MonthlySummary? = null,
    categoryType: String = "Personal" // "Personal" or "Company"
) {
    // Determine colors based on category type
    val accentColor = if (categoryType == "Personal") AppColorScheme.personalAccent else AppColorScheme.companyAccent
    val accentLight = if (categoryType == "Personal") AppColorScheme.personalAccentLight else AppColorScheme.companyAccentLight
    val backgroundColor = if (categoryType == "Personal") AppColorScheme.personalBackground else AppColorScheme.companyBackground
    val sectionIcon = if (categoryType == "Personal") Icons.Default.Person else Icons.Default.Home
    
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppColorScheme.background
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Section Header with colored accent bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Colored accent bar
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(24.dp)
                        .background(accentColor, RoundedCornerShape(2.dp))
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                // Section icon
                Icon(
                    imageVector = sectionIcon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(20.dp)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // Section title
                Text(
                    text = sectionTitle ?: stringResource(Res.string.latest_duties_title),
                    style = Typography.titleMedium.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color = AppColorScheme.sectionHeader
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Monthly Summary
            monthlySummary?.let { summary ->
                MonthlySummaryCard(summary = summary)
                Spacer(modifier = Modifier.height(16.dp))
            }
            
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
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    duties.forEach { dutyWithOccurrence ->
                        LatestDutyItem(
                            dutyWithOccurrence = dutyWithOccurrence,
                            onDelete = { onDeleteDuty(dutyWithOccurrence.duty.id) },
                            accentColor = accentColor,
                            accentLight = accentLight
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // View All button with new styling
                TextButton(
                    onClick = onViewAll,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.view_all_duties),
                        style = Typography.bodyMedium.copy(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = accentColor
                    )
                }
            }
        }
    }
}

@Composable
private fun LatestDutyItem(
    dutyWithOccurrence: DutyWithLastOccurrence,
    onDelete: () -> Unit,
    accentColor: Color,
    accentLight: Color,
    modifier: Modifier = Modifier
) {
    val duty = dutyWithOccurrence.duty
    val lastOccurrence = dutyWithOccurrence.lastOccurrence
    
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppColorScheme.cardBackground
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circular icon with accent background
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(accentLight, CircleShape)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (duty.categoryName == "Personal") Icons.Default.Person else Icons.Default.Home,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Duty title
                Text(
                    text = duty.title,
                    style = Typography.titleSmall.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color = AppColorScheme.dutyTitle
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Meta info (category and type)
                Text(
                    text = "${duty.categoryName} • ${when (duty.type) {
                        DutyType.PAYABLE -> stringResource(Res.string.duty_type_payable)
                        DutyType.ACTIONABLE -> stringResource(Res.string.duty_type_actionable)
                    }}",
                    style = Typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    color = AppColorScheme.dutyMeta
                )
                
                // Last occurrence info
                lastOccurrence?.let { occurrence ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Last: ${DateUtils.getMonthName(occurrence.completedDate.monthNumber)} ${occurrence.completedDate.year}",
                        style = Typography.bodySmall.copy(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = AppColorScheme.lastOccurrence
                    )
                }
            }
            
            // Delete button
            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .size(48.dp) // Minimum tap area
                    .background(
                        color = Color.Transparent,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete duty ${duty.title}",
                    tint = AppColorScheme.overdueText,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
private fun MonthlySummaryCard(
    summary: MonthlySummary,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppColorScheme.background
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Month/Year label
            Text(
                text = "${summary.month} ${summary.year} Summary",
                style = Typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = AppColorScheme.sectionHeader
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Amount Paid (left aligned)
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = CurrencyUtils.formatCurrency(summary.totalAmountPaid),
                        style = Typography.titleMedium.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = if (summary.totalAmountPaid > 0) AppColorScheme.amountPaid else AppColorScheme.dutyMeta
                    )
                    Text(
                        text = "Amount Paid",
                        style = Typography.bodySmall.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        color = AppColorScheme.dutyMeta
                    )
                }
                
                // Tasks Done (right aligned)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = summary.totalActionableCompleted.toString(),
                        style = Typography.titleMedium.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = AppColorScheme.dutyMeta
                    )
                    Text(
                        text = "Tasks Done",
                        style = Typography.bodySmall.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        color = AppColorScheme.dutyMeta
                    )
                }
            }
        }
    }
}
