package com.joffer.organizeplus.features.duty.review.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.joffer.organizeplus.common.utils.toCurrencyFormat
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.CategoryIcon
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import com.joffer.organizeplus.features.duty.review.domain.entities.DutyReviewItem

@Composable
fun DutyReviewItem(
    item: DutyReviewItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.md, vertical = Spacing.sm),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side - Icon and duty info
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            // Category icon using the design system component
            CategoryIcon(categoryName = item.categoryName)

            // Duty info
            Column {
                Text(
                    text = item.dutyTitle,
                    style = DesignSystemTypography().titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = SemanticColors.Foreground.primary
                )
            }
        }

        // Right side - Amount
        Text(
            text = item.paidAmount.toCurrencyFormat(),
            style = DesignSystemTypography().bodyMedium,
            fontWeight = FontWeight.Medium,
            color = SemanticColors.Foreground.primary
        )
    }
}
