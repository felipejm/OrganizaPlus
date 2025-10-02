package com.joffer.organizeplus.features.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.common.constants.CategoryConstants
import com.joffer.organizeplus.common.utils.CurrencyUtils
import com.joffer.organizeplus.common.utils.DateUtils
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import com.joffer.organizeplus.features.dashboard.MonthlySummary
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyWithLastOccurrence
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.dashboard_amount_paid
import organizeplus.composeapp.generated.resources.dashboard_company_duties
import organizeplus.composeapp.generated.resources.dashboard_monthly_summary
import organizeplus.composeapp.generated.resources.dashboard_personal_duties
import organizeplus.composeapp.generated.resources.dashboard_tasks_done
import organizeplus.composeapp.generated.resources.view_all_duties
import com.joffer.organizeplus.designsystem.colors.SemanticColors

// Component-specific constants
private val ACCENT_BAR_WIDTH = 4.dp
private val ACCENT_BAR_HEIGHT = 24.dp

/**
 * Duty category section showing duties for a specific category (Personal or Company)
 * with monthly summary and latest duties
 */
@Composable
fun DutyCategorySection(
    duties: List<DutyWithLastOccurrence>,
    onViewAll: () -> Unit,
    onDutyClick: (Long) -> Unit,
    categoryName: String,
    modifier: Modifier = Modifier,
    sectionTitle: String? = null,
    monthlySummary: MonthlySummary? = null
) {
    val typography = DesignSystemTypography()
    val config = getCategoryConfig(categoryName)

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = config.backgroundColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Spacing.Elevation.none)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.Card.padding)
        ) {
            // Section Header with colored accent bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Colored accent bar
                Box(
                    modifier = Modifier
                        .width(ACCENT_BAR_WIDTH)
                        .height(ACCENT_BAR_HEIGHT)
                        .background(config.accentColor, RoundedCornerShape(Spacing.Radius.xs))
                )

                Spacer(modifier = Modifier.width(Spacing.md))

                // Section icon
                Icon(
                    imageVector = config.sectionIcon,
                    contentDescription = null,
                    tint = config.accentColor,
                    modifier = Modifier.size(Spacing.Icon.sm)
                )

                Spacer(modifier = Modifier.width(Spacing.sm))

                // Section title
                Text(
                    text = sectionTitle ?: stringResource(config.titleResource),
                    style = typography.titleMedium,
                    color = SemanticColors.Foreground.primary
                )
            }

            Spacer(modifier = Modifier.height(Spacing.lg))

            // Monthly Summary
            monthlySummary?.let { summary ->
                MonthlySummaryCard(summary = summary)
                Spacer(modifier = Modifier.height(Spacing.lg))
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                duties.forEachIndexed { index, dutyWithOccurrence ->
                    DutyCategoryItem(
                        dutyWithOccurrence = dutyWithOccurrence,
                        onDutyClick = { onDutyClick(dutyWithOccurrence.duty.id) },
                        accentColor = config.accentColor,
                        accentLight = config.accentLight
                    )

                    // Add divider between items (except for the last one)
                    if (index < duties.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Spacing.md),
                            color = SemanticColors.Border.primary,
                            thickness = Spacing.Divider.thin
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(Spacing.md))
            OrganizeSecondaryButton(
                modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.lg),
                text = stringResource(Res.string.view_all_duties),
                onClick = onViewAll
            )
        }
    }
}

@Composable
private fun DutyCategoryItem(
    dutyWithOccurrence: DutyWithLastOccurrence,
    onDutyClick: () -> Unit,
    accentColor: Color,
    accentLight: Color,
    modifier: Modifier = Modifier
) {
    DutyCard(
        dutyWithOccurrence = dutyWithOccurrence,
        onDutyClick = onDutyClick,
        showDeleteButton = false,
        showPaidChip = true,
        showCategoryInfo = true,
        accentColor = accentColor,
        accentLight = accentLight,
        modifier = modifier
    )
}

@Composable
private fun MonthlySummaryCard(
    summary: MonthlySummary,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = SemanticColors.Background.surface
        ),
        shape = RoundedCornerShape(Spacing.Radius.md)
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md)
        ) {
            // Month/Year label
            Text(
                text = stringResource(
                    Res.string.dashboard_monthly_summary,
                    DateUtils.getMonthName(summary.currentMonth),
                    summary.year
                ),
                style = typography.bodyMedium,
                color = SemanticColors.Foreground.primary
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Amount Paid (left aligned)
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = CurrencyUtils.formatCurrency(summary.totalAmountPaid),
                        style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = if (summary.totalAmountPaid > 0) SemanticColors.Foreground.success else SemanticColors.Foreground.secondary
                    )
                    Text(
                        text = stringResource(Res.string.dashboard_amount_paid),
                        style = typography.caption,
                        color = SemanticColors.Foreground.secondary
                    )
                }

                // Tasks Done (right aligned)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${summary.totalCompleted}/${summary.totalTasks}",
                        style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = SemanticColors.Foreground.secondary
                    )
                    Text(
                        text = stringResource(Res.string.dashboard_tasks_done),
                        style = typography.caption,
                        color = SemanticColors.Foreground.secondary
                    )
                }
            }
        }
    }
}

/**
 * Configuration data for category-specific styling and content
 */
private data class CategoryConfig(
    val accentColor: Color,
    val accentLight: Color,
    val backgroundColor: Color,
    val sectionIcon: ImageVector,
    val titleResource: StringResource
)

private fun getCategoryConfig(categoryName: String): CategoryConfig {
    return when (categoryName) {
        CategoryConstants.COMPANY -> CategoryConfig(
            accentColor = SemanticColors.Legacy.companyAccent,
            accentLight = SemanticColors.Legacy.companyAccentLight,
            backgroundColor = SemanticColors.Background.surface,
            sectionIcon = Icons.Default.Home,
            titleResource = Res.string.dashboard_company_duties
        )

        else -> CategoryConfig(
            accentColor = SemanticColors.Legacy.personalAccent,
            accentLight = SemanticColors.Legacy.personalAccentLight,
            backgroundColor = SemanticColors.Background.surface,
            sectionIcon = Icons.Default.Person,
            titleResource = Res.string.dashboard_personal_duties
        )
    }
}
