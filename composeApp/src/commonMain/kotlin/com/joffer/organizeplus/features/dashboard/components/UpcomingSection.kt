package com.joffer.organizeplus.features.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.duty_type_actionable
import organizeplus.composeapp.generated.resources.duty_type_payable
import organizeplus.composeapp.generated.resources.no_duties_next_7_days
import organizeplus.composeapp.generated.resources.upcoming_7_days
import organizeplus.composeapp.generated.resources.view_all_obligations
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

/**
 * Upcoming duties section with swipe actions
 */
@Composable
fun UpcomingSection(
    duties: List<Duty>,
    onViewAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    OrganizeCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md)
        ) {
            Text(
                text = stringResource(Res.string.upcoming_7_days),
                style = Typography.cardTitle,
                color = AppColorScheme.formText
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            if (duties.isEmpty()) {
                Text(
                    text = stringResource(Res.string.no_duties_next_7_days),
                    style = Typography.bodyMedium,
                    color = AppColorScheme.formSecondaryText,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    duties.take(4).forEach { duty ->
                        UpcomingDutyItem(
                            duty = duty
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Spacing.md))

                TextButton(
                    onClick = onViewAll,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(Res.string.view_all_obligations),
                        color = AppColorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
private fun UpcomingDutyItem(
    duty: Duty,
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

                Text(
                    text = when (duty.type) {
                        DutyType.PAYABLE -> stringResource(Res.string.duty_type_payable)
                        DutyType.ACTIONABLE -> stringResource(Res.string.duty_type_actionable)
                    },
                    style = Typography.secondaryText,
                    color = AppColorScheme.formSecondaryText
                )
            }
        }
    }
}
