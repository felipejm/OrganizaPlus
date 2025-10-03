package com.joffer.organizeplus.features.duty.list.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.AppGaugeChart
import com.joffer.organizeplus.designsystem.components.GaugeChartConfig
import com.joffer.organizeplus.designsystem.components.GaugeChartData
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyWithLastOccurrence
import com.joffer.organizeplus.features.duty.list.domain.DutyCategoryFilter
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.dashboard_chart_company
import organizeplus.composeapp.generated.resources.dashboard_chart_personal
import organizeplus.composeapp.generated.resources.dashboard_chart_title

/**
 * Gauge chart component specifically for duty category completion rates.
 * Shows the completion percentage for duties in the current category.
 *
 * @param duties List of duties with their occurrence data
 * @param categoryFilter The category filter (Personal or Company)
 * @param modifier Modifier for the chart container
 */
@Composable
fun DutyCategoryGaugeChart(
    duties: List<DutyWithLastOccurrence>,
    categoryFilter: DutyCategoryFilter,
    modifier: Modifier = Modifier
) {
    if (duties.isEmpty()) return

    // Calculate completion statistics
    val totalDuties = duties.size
    val completedDuties = duties.count { it.hasCurrentMonthOccurrence }
    val completionPercentage = if (totalDuties > 0) {
        completedDuties.toFloat() / totalDuties.toFloat() * 100f
    } else {
        0f
    }

    // Determine category-specific styling
    val (categoryName, categoryColor) = when (categoryFilter) {
        DutyCategoryFilter.Personal -> {
            stringResource(Res.string.dashboard_chart_personal) to SemanticColors.Legacy.personalAccent
        }
        DutyCategoryFilter.Company -> {
            stringResource(Res.string.dashboard_chart_company) to SemanticColors.Legacy.companyAccent
        }
    }

    // Create gauge data
    val gaugeData = GaugeChartData(
        value = completionPercentage,
        minValue = 0f,
        maxValue = 100f,
        unit = "%",
        color = categoryColor,
        backgroundColor = SemanticColors.Background.surfaceVariant,
        label = stringResource(Res.string.dashboard_chart_title),
        subtitle = "$categoryName: $completedDuties/$totalDuties completed"
    )

    // Create gauge config
    val gaugeConfig = GaugeChartConfig(
        strokeWidth = Spacing.Chart.strokeWidth,
        showValue = true,
        showLabel = true,
        showSubtitle = true,
        animate = true,
        animationDuration = Spacing.Chart.animationDuration
    )

    AppGaugeChart(
        data = gaugeData,
        config = gaugeConfig,
        modifier = modifier
    )
}
