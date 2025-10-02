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
import com.joffer.organizeplus.designsystem.colors.ColorScheme
import com.joffer.organizeplus.designsystem.components.OrganizeCard
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.localTypography
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
                    style = localTypography().bodyMedium,
                    color = ColorScheme.formSecondaryText
                )
                Text(
                    text = "${DateUtils.getMonthName(monthlyReview.monthNumber)} ${monthlyReview.year}",
                    style = localTypography().titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = ColorScheme.onSurface
                )
            }

            // Total section
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = stringResource(Res.string.duty_review_total),
                    style = localTypography().titleMedium,
                    color = ColorScheme.formSecondaryText
                )
                Spacer(modifier = Modifier.height(Spacing.sm))
                Text(
                    text = formatString("$%.2f", monthlyReview.totalPaid),
                    style = localTypography().bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = ColorScheme.amountPaid
                )
            }
        }

        // Duty items
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            monthlyReview.dutyItems.forEachIndexed { index, item ->
                DutyReviewItem(item = item)

                // Divider between items (except for the last one)
                if (index < monthlyReview.dutyItems.size - 1) {
                    Spacer(modifier = Modifier.height(Spacing.sm))
                    Divider(
                        modifier = Modifier.padding(horizontal = Spacing.md),
                        color = ColorScheme.outline,
                        thickness = 1.dp
                    )
                    Spacer(modifier = Modifier.height(Spacing.sm))
                }
            }
        }
        Spacer(modifier = Modifier.height(Spacing.xs))
    }
}
