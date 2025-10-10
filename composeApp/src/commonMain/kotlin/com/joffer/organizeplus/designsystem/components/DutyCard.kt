package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.icons.OrganizeIcons
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
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

/**
 * Reusable duty card component that can be used in both dashboard and duty list screens
 *
 * @param dutyWithOccurrence The duty data to display
 * @param onDutyClick Callback when the duty card is clicked
 * @param onDelete Callback when the delete button is clicked (only shown if showDeleteButton is true)
 * @param showDeleteButton Whether to show the delete button (default: false)
 * @param showPaidChip Whether to show the paid/done chip (default: true)
 * @param showCategoryInfo Whether to show category and type information (default: true)
 * @param accentColor Color for the category icon background (used in dashboard)
 * @param accentLight Light color for the category icon background (used in dashboard)
 * @param modifier Modifier for the component
 */
@Composable
fun DutyCard(
    dutyWithOccurrence: DutyWithLastOccurrence,
    onDutyClick: () -> Unit,
    onDelete: ((Long) -> Unit)? = null,
    showDeleteButton: Boolean = false,
    showPaidChip: Boolean = true,
    showCategoryInfo: Boolean = true,
    accentColor: Color? = null,
    accentLight: Color? = null,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    val duty = dutyWithOccurrence.duty

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onDutyClick() },
        colors = CardDefaults.cardColors(
            containerColor = SemanticColors.Background.surface
        ),
        shape = RoundedCornerShape(Spacing.Radius.md)
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
                    // Category icon with optional accent colors for dashboard
                    if (accentColor != null && accentLight != null) {
                        Box(
                            modifier = Modifier
                                .size(Spacing.Icon.xl)
                                .background(accentLight, CircleShape)
                                .clip(CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            CategoryIcon(categoryName = duty.categoryName)
                        }
                    } else {
                        // List style with default colors
                        CategoryIcon(
                            categoryName = duty.categoryName
                        )
                    }

                    Spacer(modifier = Modifier.width(Spacing.md))

                    // Duty title
                    Text(
                        text = duty.title,
                        style = if (accentColor != null) typography.titleMedium else typography.titleSmall,
                        color = SemanticColors.Foreground.primary,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Right side actions
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spacing.xs)
                ) {
                    // Paid/Done chip
                    if (showPaidChip && dutyWithOccurrence.hasCurrentMonthOccurrence) {
                        PriorityChip(
                            priority = ObligationPriority.LOW,
                            text = when (duty.type) {
                                DutyType.PAYABLE -> stringResource(Res.string.duty_list_paid)
                                DutyType.ACTIONABLE -> stringResource(Res.string.duty_list_done)
                            }
                        )
                    }

                    // Delete button
                    if (showDeleteButton && onDelete != null) {
                        IconButton(
                            onClick = { onDelete(duty.id) },
                            modifier = Modifier.size(Spacing.xxl)
                        ) {
                            Icon(
                                imageVector = OrganizeIcons.Actions.Delete,
                                contentDescription = stringResource(Res.string.duty_list_delete_description),
                                tint = SemanticColors.Foreground.primary,
                                modifier = Modifier.size(Spacing.Icon.sm)
                            )
                        }
                    }
                }
            }

            // Category and Type information
            if (showCategoryInfo) {
                Spacer(modifier = Modifier.height(Spacing.xs))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    Text(
                        text = duty.categoryName,
                        style = typography.labelLarge,
                        color = SemanticColors.Foreground.secondary
                    )

                    Text(
                        text = stringResource(Res.string.duty_list_separator),
                        style = typography.labelLarge,
                        color = SemanticColors.Foreground.secondary
                    )

                    Text(
                        text = when (duty.type) {
                            DutyType.PAYABLE -> stringResource(Res.string.duty_type_payable)
                            DutyType.ACTIONABLE -> stringResource(Res.string.duty_type_actionable)
                        },
                        style = typography.labelLarge,
                        color = SemanticColors.Foreground.secondary
                    )
                }
            }
        }
    }
}
