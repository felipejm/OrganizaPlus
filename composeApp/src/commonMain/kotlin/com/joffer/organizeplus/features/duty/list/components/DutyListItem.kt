package com.joffer.organizeplus.features.duty.list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.components.CategoryIcon
import com.joffer.organizeplus.designsystem.components.OrganizeCard
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.localTypography
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyWithLastOccurrence
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.duty_list_delete_description
import organizeplus.composeapp.generated.resources.duty_list_done
import organizeplus.composeapp.generated.resources.duty_list_paid
import organizeplus.composeapp.generated.resources.duty_list_separator
import organizeplus.composeapp.generated.resources.duty_type_actionable
import organizeplus.composeapp.generated.resources.duty_type_payable
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@Composable
fun DutyListItem(
    dutyWithOccurrence: DutyWithLastOccurrence,
    onViewOccurrences: (Long) -> Unit,
    onDelete: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = localTypography()
    val duty = dutyWithOccurrence.duty
    val lastOccurrence = dutyWithOccurrence.lastOccurrence
    OrganizeCard(
        modifier = modifier.fillMaxWidth(),
        onClick = { onViewOccurrences(duty.id) },
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
                        style = typography.titleSmall,
                        color = AppColorScheme.formText
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spacing.xs)
                ) {
                    if (dutyWithOccurrence.hasCurrentMonthOccurrence) {
                        Spacer(modifier = Modifier.width(Spacing.sm))
                        AssistChip(
                            onClick = { },
                            label = {
                                Text(
                                    text = when (duty.type) {
                                        DutyType.PAYABLE -> stringResource(Res.string.duty_list_paid)
                                        DutyType.ACTIONABLE -> stringResource(Res.string.duty_list_done)
                                    },
                                    style = typography.labelMedium,
                                    color = AppColorScheme.success700
                                )
                            },
                            modifier = modifier,
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = AppColorScheme.success100,
                                labelColor = AppColorScheme.success700
                            )
                        )
                    }

                    IconButton(
                        onClick = { onDelete(duty.id) },
                        modifier = Modifier.size(Spacing.xxl)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(Res.string.duty_list_delete_description),
                            tint = AppColorScheme.error,
                            modifier = Modifier.size(Spacing.Icon.sm)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(Spacing.xs))

            // Category and Type
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                Text(
                    text = duty.categoryName,
                    style = typography.labelSmall,
                    color = AppColorScheme.formSecondaryText
                )

                Text(
                    text = stringResource(Res.string.duty_list_separator),
                    style = typography.labelSmall,
                    color = AppColorScheme.formSecondaryText
                )

                Text(
                    text = when (duty.type) {
                        DutyType.PAYABLE -> stringResource(Res.string.duty_type_payable)
                        DutyType.ACTIONABLE -> stringResource(Res.string.duty_type_actionable)
                    },
                    style = typography.labelSmall,
                    color = AppColorScheme.formSecondaryText
                )
            }
        }
    }
}
