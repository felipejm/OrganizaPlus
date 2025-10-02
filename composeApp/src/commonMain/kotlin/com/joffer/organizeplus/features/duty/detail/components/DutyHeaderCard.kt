package com.joffer.organizeplus.features.duty.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.OrganizeCard
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.duty_detail_category
import organizeplus.composeapp.generated.resources.duty_detail_type
import organizeplus.composeapp.generated.resources.duty_type_actionable
import organizeplus.composeapp.generated.resources.duty_type_payable
import organizeplus.composeapp.generated.resources.not_available

@Composable
fun DutyHeaderCard(
    duty: Duty,
    modifier: Modifier = Modifier
) {
    OrganizeCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            // Duty Information - Horizontal Layout
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
        }
    }
}

@Composable
fun DutyInfoItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = TextStyle(fontSize = 14.sp),
            color = SemanticColors.Foreground.secondary,
            fontWeight = FontWeight.Light
        )
        Text(
            text = value,
            style = TextStyle(fontSize = 16.sp),
            color = SemanticColors.Foreground.primary,
            fontWeight = FontWeight.Bold
        )
    }
}
