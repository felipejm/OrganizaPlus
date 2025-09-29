package com.joffer.organizeplus.features.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.dashboard_mark_all_paid
import organizeplus.composeapp.generated.resources.dashboard_paid
import organizeplus.composeapp.generated.resources.dashboard_pending
import organizeplus.composeapp.generated.resources.dashboard_today
import organizeplus.composeapp.generated.resources.dashboard_today_subtitle

@Composable
fun TodaySection(
    pendingCount: Int,
    paidCount: Int,
    todayDuties: List<Duty>,
    onMarkAllPaid: () -> Unit,
    modifier: Modifier = Modifier
) {
    OrganizeCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md)
        ) {
            Text(
                text = stringResource(Res.string.dashboard_today),
                style = Typography.cardTitle,
                color = AppColorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = AppColorScheme.surface
                    ),
                    shape = RoundedCornerShape(Spacing.borderRadius)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Spacing.md),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = pendingCount.toString(),
                            style = Typography.kpiNumber,
                            color = AppColorScheme.amber
                        )
                        Text(
                            text = stringResource(Res.string.dashboard_pending),
                            style = Typography.secondaryText,
                            color = AppColorScheme.onAmber
                        )
                    }
                }
                
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = AppColorScheme.surface
                    ),
                    shape = RoundedCornerShape(Spacing.borderRadius)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Spacing.md),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = paidCount.toString(),
                            style = Typography.kpiNumber,
                            color = AppColorScheme.green
                        )
                        Text(
                            text = stringResource(Res.string.dashboard_paid),
                            style = Typography.secondaryText,
                            color = AppColorScheme.onGreen
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            if (todayDuties.isNotEmpty()) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(Spacing.xs)
                ) {
                    todayDuties.forEach { duty ->
                        TodayDutyItem(
                            duty = duty,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(Spacing.md))
            }
            
            Button(
                onClick = onMarkAllPaid,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColorScheme.surfaceVariant
                ),
                shape = RoundedCornerShape(Spacing.borderRadius)
            ) {
                Text(
                    text = stringResource(Res.string.dashboard_mark_all_paid),
                    color = AppColorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun TodayDutyItem(
    duty: Duty,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
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
            val statusColor = when (duty.status) {
                Duty.Status.PAID -> AppColorScheme.primary
                Duty.Status.OVERDUE -> AppColorScheme.error
                else -> AppColorScheme.formPlaceholder
            }
            
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = statusColor,
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )
            
            Spacer(modifier = Modifier.width(Spacing.sm))
            
            Text(
                text = duty.title,
                style = Typography.bodyMedium,
                color = AppColorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            
            val statusText = when (duty.status) {
                Duty.Status.PAID -> stringResource(Res.string.dashboard_paid)
                Duty.Status.OVERDUE -> "Atrasado"
                else -> stringResource(Res.string.dashboard_pending)
            }
            
            Text(
                text = statusText,
                style = Typography.bodySmall,
                color = statusColor
            )
        }
    }
}
