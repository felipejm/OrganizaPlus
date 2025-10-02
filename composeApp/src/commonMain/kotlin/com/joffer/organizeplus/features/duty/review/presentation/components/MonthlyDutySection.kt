package com.joffer.organizeplus.features.duty.review.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.common.utils.DateUtils
import com.joffer.organizeplus.common.utils.formatString
import com.joffer.organizeplus.common.utils.toCurrencyFormat
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.OrganizeCard
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import com.joffer.organizeplus.features.duty.review.domain.entities.MonthlyDutyReview
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.duty_review_date
import organizeplus.composeapp.generated.resources.duty_review_total

@Composable
fun MonthlyDutySection(
    monthlyReview: MonthlyDutyReview,
    modifier: Modifier = Modifier
) {
    OrganizeCard(
        modifier = modifier.fillMaxWidth()
    ) {
        // Header with date and total (like in the image)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.md, vertical = Spacing.md),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Date section
            Column {
                Text(
                    text = stringResource(Res.string.duty_review_date),
                    style = DesignSystemTypography().bodyMedium,
                    color = SemanticColors.Foreground.secondary
                )
                Text(
                    text = "${DateUtils.getMonthName(monthlyReview.monthNumber)} ${monthlyReview.year}",
                    style = DesignSystemTypography().titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = SemanticColors.Foreground.primary
                )
            }

            // Total section
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = stringResource(Res.string.duty_review_total),
                    style = DesignSystemTypography().bodyMedium,
                    color = SemanticColors.Foreground.secondary
                )
                Spacer(modifier = Modifier.height(Spacing.sm))
                Text(
                    text = monthlyReview.totalPaid.toCurrencyFormat(),
                    style = DesignSystemTypography().bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = SemanticColors.Foreground.success
                )
            }
        }

        // Duty items
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            monthlyReview.dutyItems.forEachIndexed { index, item ->
                DutyReviewItem(item = item)

                if (index < monthlyReview.dutyItems.size - 1) {
                    Divider()
                }
            }
        }
        Spacer(modifier = Modifier.height(Spacing.xs))
    }
}

@Composable
private fun Divider() {
    Spacer(modifier = Modifier.height(Spacing.sm))
    Divider(
        modifier = Modifier.padding(horizontal = Spacing.md),
        color = SemanticColors.Border.primary,
        thickness = 1.dp
    )
    Spacer(modifier = Modifier.height(Spacing.sm))
}
