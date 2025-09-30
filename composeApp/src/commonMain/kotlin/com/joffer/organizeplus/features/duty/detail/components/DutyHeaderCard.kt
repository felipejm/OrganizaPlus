package com.joffer.organizeplus.features.duty.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.components.OrganizeCard
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.duty_detail_start_day
import organizeplus.composeapp.generated.resources.duty_detail_due_day
import organizeplus.composeapp.generated.resources.duty_detail_category
import organizeplus.composeapp.generated.resources.duty_detail_type
import organizeplus.composeapp.generated.resources.duty_detail_status
import organizeplus.composeapp.generated.resources.duty_type_payable
import organizeplus.composeapp.generated.resources.duty_type_actionable
import organizeplus.composeapp.generated.resources.status_pending
import organizeplus.composeapp.generated.resources.status_paid
import organizeplus.composeapp.generated.resources.status_overdue
import organizeplus.composeapp.generated.resources.status_snoozed
import organizeplus.composeapp.generated.resources.not_available
import organizeplus.composeapp.generated.resources.duty_due_every_day
import organizeplus.composeapp.generated.resources.duty_start_every_day

@Composable
fun DutyHeaderCard(
    duty: Duty,
    modifier: Modifier = Modifier
) {
    OrganizeCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md)
        ) {
            // Title
            Text(
                text = duty.title,
                style = Typography.headlineSmall,
                color = AppColorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            // Duty Information Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.lg)
            ) {
                // Left Column
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    DutyInfoItem(
                        label = stringResource(Res.string.duty_detail_start_day),
                        value = duty.startDay.toString()
                    )
                    
                    DutyInfoItem(
                        label = stringResource(Res.string.duty_detail_due_day),
                        value = duty.dueDay.toString()
                    )
                }
                
                // Right Column
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    DutyInfoItem(
                        label = stringResource(Res.string.duty_detail_category),
                        value = duty.categoryName.ifEmpty { stringResource(Res.string.not_available) }
                    )
                    
                    DutyInfoItem(
                        label = stringResource(Res.string.duty_detail_type),
                        value = when (duty.type) {
                            DutyType.PAYABLE -> stringResource(Res.string.duty_type_payable)
                            DutyType.ACTIONABLE -> stringResource(Res.string.duty_type_actionable)
                        }
                    )
                    
                    DutyInfoItem(
                        label = stringResource(Res.string.duty_detail_status),
                        value = when (duty.status) {
                            Duty.Status.PENDING -> stringResource(Res.string.status_pending)
                            Duty.Status.PAID -> stringResource(Res.string.status_paid)
                            Duty.Status.OVERDUE -> stringResource(Res.string.status_overdue)
                            Duty.Status.SNOOZED -> stringResource(Res.string.status_snoozed)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DutyInfoItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = Typography.labelLarge,
            color = AppColorScheme.formSecondaryText,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(Spacing.xs))
        Text(
            text = value,
            style = Typography.bodyLarge,
            color = AppColorScheme.onSurface,
            fontWeight = FontWeight.Medium
        )
    }
}

