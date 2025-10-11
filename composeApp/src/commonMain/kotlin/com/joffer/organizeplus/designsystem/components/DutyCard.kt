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

    // Simplified card layout matching image design
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onDutyClick() }
            .padding(vertical = Spacing.sm),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Category icon with accent colors (matching image)
        if (accentColor != null && accentLight != null) {
            Box(
                modifier = Modifier
                    .size(Spacing.Icon.xl)
                    .background(SemanticColors.Background.surfaceVariant, CircleShape), // Dark circle background
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (duty.categoryName == "Company") OrganizeIcons.Navigation.Building else OrganizeIcons.Navigation.User,
                    contentDescription = null,
                    tint = accentColor, // Category accent color
                    modifier = Modifier.size(Spacing.Icon.sm)
                )
            }
        } else {
            // List style with default colors
            CategoryIcon(
                categoryName = duty.categoryName
            )
        }

        Spacer(modifier = Modifier.width(Spacing.md))

        // Duty content
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Duty title (matching image)
            Text(
                text = duty.title,
                style = typography.bodyMedium, // 16sp, Normal
                color = SemanticColors.Foreground.primary // White
            )
            
            // Category and type info (matching image)
            if (showCategoryInfo) {
                Text(
                    text = "${duty.categoryName} ${stringResource(Res.string.duty_list_separator)} ${when (duty.type) {
                        DutyType.PAYABLE -> stringResource(Res.string.duty_type_payable)
                        DutyType.ACTIONABLE -> stringResource(Res.string.duty_type_actionable)
                    }}",
                    style = typography.bodySmall, // 12sp, Normal
                    color = SemanticColors.Foreground.secondary // Light grey
                )
            }
        }
    }
}
