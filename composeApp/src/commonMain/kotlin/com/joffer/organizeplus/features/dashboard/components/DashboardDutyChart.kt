package com.joffer.organizeplus.features.dashboard.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.components.AppCircleChart
import com.joffer.organizeplus.designsystem.components.CircleChartConfig
import com.joffer.organizeplus.designsystem.components.CircleChartSegment
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.features.dashboard.MonthlySummary
import kotlin.math.roundToInt
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.dashboard_chart_title
import organizeplus.composeapp.generated.resources.dashboard_chart_personal
import organizeplus.composeapp.generated.resources.dashboard_chart_company
import organizeplus.composeapp.generated.resources.dashboard_chart_total
import organizeplus.composeapp.generated.resources.dashboard_chart_complete

private data class CategoryCompletion(
    val completed: Int,
    val total: Int,
    val percentage: Float
) {
    val hasData: Boolean get() = total > 0
    val isComplete: Boolean get() = completed == total && total > 0
}


@Composable
fun DashboardDutyChart(
    personalSummary: MonthlySummary?,
    companySummary: MonthlySummary?,
    modifier: Modifier = Modifier
) {
    val personalCompletion = calculateCompletion(personalSummary)
    val companyCompletion = calculateCompletion(companySummary)
    val totalCompletion = calculateTotalCompletion(personalCompletion, companyCompletion)

    // Only show chart if there are tasks in at least one category
    if (totalCompletion.hasData) {
        val chartData = buildChartData(personalCompletion, companyCompletion)
        
        AppCircleChart(
            title = stringResource(Res.string.dashboard_chart_title),
            data = chartData,
            config = createChartConfig(totalCompletion),
            modifier = modifier
        )
    }
}


private fun calculateCompletion(summary: MonthlySummary?): CategoryCompletion {
    val completed = summary?.totalCompleted ?: 0
    val total = summary?.totalTasks ?: 0
    val percentage = if (total > 0) {
        (completed.toFloat() / total.toFloat()) * 100f
    } else {
        0f
    }
    
    return CategoryCompletion(
        completed = completed,
        total = total,
        percentage = percentage
    )
}

/**
 * Calculate total completion across both categories
 */
private fun calculateTotalCompletion(
    personal: CategoryCompletion,
    company: CategoryCompletion
): CategoryCompletion {
    val totalCompleted = personal.completed + company.completed
    val totalTasks = personal.total + company.total
    val percentage = if (totalTasks > 0) {
        (totalCompleted.toFloat() / totalTasks.toFloat()) * 100f
    } else {
        0f
    }
    
    return CategoryCompletion(
        completed = totalCompleted,
        total = totalTasks,
        percentage = percentage
    )
}

/**
 * Build chart data segments for the circle chart
 */
@Composable
private fun buildChartData(
    personal: CategoryCompletion,
    company: CategoryCompletion
): List<CircleChartSegment> {
    val segments = mutableListOf<CircleChartSegment>()
    
    // Add personal segment if there are personal tasks
    if (personal.hasData) {
        segments.add(
            CircleChartSegment(
                label = stringResource(Res.string.dashboard_chart_personal),
                value = personal.percentage,
                color = SemanticColors.Legacy.personalAccent
            )
        )
    }
    
    // Add company segment if there are company tasks
    if (company.hasData) {
        segments.add(
            CircleChartSegment(
                label = stringResource(Res.string.dashboard_chart_company),
                value = company.percentage,
                color = SemanticColors.Legacy.companyAccent
            )
        )
    }
    
    return segments
}


@Composable
private fun createChartConfig(totalCompletion: CategoryCompletion): CircleChartConfig {
    val centerText = if (totalCompletion.isComplete) {
        "${stringResource(Res.string.dashboard_chart_complete)}\n100%"
    } else {
        "${stringResource(Res.string.dashboard_chart_total)}\n${totalCompletion.percentage.roundToInt()}%"
    }
    
    return CircleChartConfig(
        strokeWidth = Spacing.Chart.strokeWidth,
        showCenterText = true,
        centerText = centerText,
        animationDuration = Spacing.Chart.animationDuration,
        showValues = true,
        showLabels = true,
        animate = true
    )
}
