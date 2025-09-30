package com.joffer.organizeplus.features.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joffer.organizeplus.common.constants.CategoryConstants
import com.joffer.organizeplus.common.utils.DateUtils
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.components.ResultType
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyWithLastOccurrence
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.dashboard.MonthlySummary
import com.joffer.organizeplus.common.utils.CurrencyUtils
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.latest_duties_title
import organizeplus.composeapp.generated.resources.view_all_duties
import organizeplus.composeapp.generated.resources.add_duty
import organizeplus.composeapp.generated.resources.no_duties_created_yet
import organizeplus.composeapp.generated.resources.start_creating_first_duty
import organizeplus.composeapp.generated.resources.duty_type_payable
import organizeplus.composeapp.generated.resources.duty_type_actionable
import organizeplus.composeapp.generated.resources.dashboard_personal_duties
import organizeplus.composeapp.generated.resources.dashboard_company_duties
import organizeplus.composeapp.generated.resources.dashboard_monthly_summary
import organizeplus.composeapp.generated.resources.dashboard_amount_paid
import organizeplus.composeapp.generated.resources.dashboard_tasks_done
import organizeplus.composeapp.generated.resources.dashboard_last_occurrence

// Component-specific constants
private val ACCENT_BAR_WIDTH = 4.dp
private val ACCENT_BAR_HEIGHT = 24.dp

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

/**
 * Gets the configuration for a specific category
 */
private fun getCategoryConfig(categoryName: String): CategoryConfig {
    return when (categoryName) {
        CategoryConstants.COMPANY -> CategoryConfig(
            accentColor = AppColorScheme.companyAccent,
            accentLight = AppColorScheme.companyAccentLight,
            backgroundColor = AppColorScheme.background,
            sectionIcon = Icons.Default.Home,
            titleResource = Res.string.dashboard_company_duties
        )

        else -> CategoryConfig(
            accentColor = AppColorScheme.personalAccent,
            accentLight = AppColorScheme.personalAccentLight,
            backgroundColor = AppColorScheme.background,
            sectionIcon = Icons.Default.Person,
            titleResource = Res.string.dashboard_personal_duties
        ) // Default fallback
    }
}

/**
 * Duty category section showing duties for a specific category (Personal or Company)
 * with monthly summary and latest duties
 */
@Composable
fun DutyCategorySection(
    duties: List<DutyWithLastOccurrence>,
    onViewAll: () -> Unit,
    onAddDuty: () -> Unit,
    onDutyClick: (String) -> Unit,
    categoryName: String,
    modifier: Modifier = Modifier,
    sectionTitle: String? = null,
    monthlySummary: MonthlySummary? = null
) {
    // Get category configuration
    val config = getCategoryConfig(categoryName)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onViewAll() },
        colors = CardDefaults.cardColors(
            containerColor = config.backgroundColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Spacing.Elevation.md)
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
                    style = Typography.title,
                    color = AppColorScheme.sectionHeader
                )
            }

            Spacer(modifier = Modifier.height(Spacing.lg))

            // Monthly Summary
            monthlySummary?.let { summary ->
                MonthlySummaryCard(summary = summary)
                Spacer(modifier = Modifier.height(Spacing.lg))
            }

            if (duties.isEmpty()) {
                // Empty state with add duty button
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    OrganizeResult(
                        type = ResultType.INFO,
                        title = stringResource(Res.string.no_duties_created_yet),
                        description = stringResource(Res.string.start_creating_first_duty),
                        actions = {
                            OrganizePrimaryButton(
                                text = stringResource(Res.string.add_duty),
                                onClick = onAddDuty,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    )
                }
            } else {
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
                                    .padding(horizontal = Spacing.lg),
                                color = AppColorScheme.divider,
                                thickness = Spacing.Divider.thin
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(Spacing.md))

                // View All button with new styling
                TextButton(
                    onClick = onViewAll,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Spacing.buttonHeight + Spacing.sm)
                ) {
                    Text(
                        text = stringResource(Res.string.view_all_duties),
                        style = Typography.bodyMedium,
                        color = config.accentColor
                    )
                }
            }
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
    val duty = dutyWithOccurrence.duty
    val lastOccurrence = dutyWithOccurrence.lastOccurrence

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onDutyClick() },
        colors = CardDefaults.cardColors(
            containerColor = AppColorScheme.cardBackground
        ),
        shape = RoundedCornerShape(Spacing.Radius.md)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circular icon with accent background
            Box(
                modifier = Modifier
                    .size(Spacing.Icon.xl)
                    .background(accentLight, CircleShape)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (duty.categoryName == CategoryConstants.PERSONAL) {
                        Icons.Default.Person
                    } else {
                        Icons.Default.Home
                    },
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(Spacing.Icon.md)
                )
            }

            Spacer(modifier = Modifier.width(Spacing.md))

            // Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Duty title
                Text(
                    text = duty.title,
                    style = Typography.subtitle,
                    color = AppColorScheme.dutyTitle
                )

                Spacer(modifier = Modifier.height(Spacing.xs))

                // Meta info (category and type)
                Text(
                    text = "${duty.categoryName} • ${
                        when (duty.type) {
                            DutyType.PAYABLE -> stringResource(Res.string.duty_type_payable)
                            DutyType.ACTIONABLE -> stringResource(Res.string.duty_type_actionable)
                        }
                    }",
                    style = Typography.body,
                    color = AppColorScheme.dutyMeta
                )

                // Last occurrence info
                lastOccurrence?.let { occurrence ->
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    Text(
                        text = stringResource(
                            Res.string.dashboard_last_occurrence,
                            DateUtils.getMonthName(occurrence.completedDate.monthNumber),
                            occurrence.completedDate.year
                        ),
                        style = Typography.captionMedium,
                        color = AppColorScheme.lastOccurrence
                    )
                }
            }
        }
    }
}

@Composable
private fun MonthlySummaryCard(
    summary: MonthlySummary,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppColorScheme.summaryBackground
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
                    summary.month,
                    summary.year
                ),
                style = Typography.bodyMedium,
                color = AppColorScheme.summaryMonthLabel
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
                        style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = if (summary.totalAmountPaid > 0) AppColorScheme.amountPaid else AppColorScheme.dutyMeta
                    )
                    Text(
                        text = stringResource(Res.string.dashboard_amount_paid),
                        style = Typography.caption,
                        color = AppColorScheme.dutyMeta
                    )
                }

                // Tasks Done (right aligned)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = summary.totalActionableCompleted.toString(),
                        style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = AppColorScheme.dutyMeta
                    )
                    Text(
                        text = stringResource(Res.string.dashboard_tasks_done),
                        style = Typography.caption,
                        color = AppColorScheme.dutyMeta
                    )
                }
            }
        }
    }
}

// Helper data class for multiple return values
private data class Tuple5<A, B, C, D, E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E
)
