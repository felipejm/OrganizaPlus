package com.joffer.organizeplus.features.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import kotlin.math.roundToInt
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.common.utils.DateUtils
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.features.dashboard.domain.entities.MonthlySummary
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.dashboard_monthly_summary
import organizeplus.composeapp.generated.resources.dashboard_planned
import organizeplus.composeapp.generated.resources.dashboard_paid_amount
import organizeplus.composeapp.generated.resources.dashboard_personal
import organizeplus.composeapp.generated.resources.dashboard_business
import organizeplus.composeapp.generated.resources.dashboard_total_expected
import organizeplus.composeapp.generated.resources.dashboard_total_paid

/**
 * Monthly summary section with bar chart
 */
@Composable
fun MonthlySummarySection(
    summary: MonthlySummary,
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
                text = stringResource(Res.string.dashboard_monthly_summary),
                style = Typography.cardTitle,
                color = AppColorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            // Bar chart
            MonthlyBarChart(
                personalPlanned = summary.personal.total.toDouble(),
                personalPaid = summary.personal.paid.toDouble(),
                businessPlanned = summary.business.total.toDouble(),
                businessPaid = summary.business.paid.toDouble()
            )
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            // Legend
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LegendItem(
                    label = stringResource(Res.string.dashboard_planned),
                    color = AppColorScheme.tertiary
                )
                
                Spacer(modifier = Modifier.width(Spacing.lg))
                
                LegendItem(
                    label = stringResource(Res.string.dashboard_paid_amount),
                    color = AppColorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            // Summary
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = AppColorScheme.surfaceVariant
                ),
                shape = RoundedCornerShape(Spacing.borderRadius)
            ) {
                Column(
                    modifier = Modifier.padding(Spacing.sm)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(Res.string.dashboard_total_expected),
                            style = Typography.secondaryText,
                            color = AppColorScheme.onSurfaceVariant
                        )
            Text(
                text = "${summary.personal.total + summary.business.total}",
                style = Typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppColorScheme.onSurface
            )
                    }
                    
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(Res.string.dashboard_total_paid),
                            style = Typography.secondaryText,
                            color = AppColorScheme.onSurfaceVariant
                        )
            Text(
                text = "${summary.personal.paid + summary.business.paid}",
                style = Typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppColorScheme.primary
            )
                    }
                }
            }
        }
    }
}

@Composable
private fun MonthlyBarChart(
    personalPlanned: Double,
    personalPaid: Double,
    businessPlanned: Double,
    businessPaid: Double,
    modifier: Modifier = Modifier
) {
    val maxValue = maxOf(personalPlanned, personalPaid, businessPlanned, businessPaid)
    
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        // Personal bars
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            // Planned bar
            BarItem(
                label = stringResource(Res.string.dashboard_personal),
                value = personalPlanned,
                maxValue = maxValue,
                color = AppColorScheme.tertiary,
                modifier = Modifier.weight(1f)
            )
            
            // Paid bar
            BarItem(
                label = "",
                value = personalPaid,
                maxValue = maxValue,
                color = AppColorScheme.primary,
                modifier = Modifier.weight(1f)
            )
        }
        
        // Business bars
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            // Planned bar
            BarItem(
                label = stringResource(Res.string.dashboard_business),
                value = businessPlanned,
                maxValue = maxValue,
                color = AppColorScheme.tertiary,
                modifier = Modifier.weight(1f)
            )
            
            // Paid bar
            BarItem(
                label = "",
                value = businessPaid,
                maxValue = maxValue,
                color = AppColorScheme.primary,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun CategorySummaryCard(
    title: String,
    summary: com.joffer.organizeplus.features.dashboard.domain.entities.CategorySummary,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = Typography.titleSmall,
            fontWeight = FontWeight.Medium,
            color = AppColorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(Spacing.sm))
        
        // Bar chart representation
        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.xs)
        ) {
            // Total bar
            BarItem(
                label = "Total",
                value = summary.total.toDouble(),
                maxValue = maxOf(summary.total, summary.paid).toDouble(),
                color = AppColorScheme.outline
            )
            
            // Paid bar
            BarItem(
                label = "Pago",
                value = summary.paid.toDouble(),
                maxValue = maxOf(summary.total, summary.paid).toDouble(),
                color = color
            )
        }
        
        Spacer(modifier = Modifier.height(Spacing.sm))
        
        Text(
            text = "${((if (summary.total > 0) (summary.paid.toDouble() / summary.total) * 100 else 0.0).roundToInt())}%",
            style = Typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
private fun BarItem(
    label: String,
    value: Double,
    maxValue: Double,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (label.isNotEmpty()) {
            Text(
                text = label,
                style = Typography.bodySmall,
                color = AppColorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(Spacing.xs))
        }
        
        val width = if (maxValue > 0) (value / maxValue).toFloat() else 0f
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(width)
                    .fillMaxHeight()
                    .background(color, RoundedCornerShape(Spacing.borderRadius))
            )
        }
    }
}

@Composable
private fun LegendItem(
    label: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, androidx.compose.foundation.shape.CircleShape)
        )
        
        Spacer(modifier = Modifier.width(Spacing.xs))
        
        Text(
            text = label,
            style = Typography.chipText,
                            color = AppColorScheme.onSurfaceVariant
        )
    }
}

