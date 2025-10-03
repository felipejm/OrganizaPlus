package com.joffer.organizeplus.features.duty.review.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
        // Category icon using the design system component
        CategoryIcon(categoryName = item.categoryName)

        Spacer(modifier = Modifier.width(Spacing.sm))
        // Duty info
        Text(
            modifier = Modifier.weight(1f),
            text = item.dutyTitle,
            style = DesignSystemTypography().titleMedium,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Medium,
            overflow = TextOverflow.Ellipsis,
            color = SemanticColors.Foreground.primary
        )

        Spacer(modifier = Modifier.width(Spacing.sm))
        // Right side - Amount
        Text(
            text = item.paidAmount.toCurrencyFormat(),
            style = DesignSystemTypography().bodyMedium,
            fontWeight = FontWeight.Medium,
            color = SemanticColors.Foreground.primary
        )
    }
}
