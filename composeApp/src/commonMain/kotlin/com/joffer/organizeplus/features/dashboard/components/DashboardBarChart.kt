package com.joffer.organizeplus.features.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.charts.BarChart
import com.joffer.organizeplus.designsystem.components.charts.BarChartConfig
import com.joffer.organizeplus.designsystem.components.charts.BarChartData
import com.joffer.organizeplus.designsystem.components.charts.BarDataPoint
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography

/**
 * A dashboard-specific bar chart component that shows duty completion statistics.
 * This is an example of how to integrate the BarChart component into existing features.
 */
@Composable
fun DashboardBarChart(
    weeklyCompletions: List<Pair<String, Int>>,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()

    // Convert data to BarDataPoint format
    val chartData = remember(weeklyCompletions) {
        BarChartData(
            dataPoints = weeklyCompletions.mapIndexed { index, (day, count) ->
                BarDataPoint(
                    label = day,
                    value = count.toFloat(),
                    color = getDayColor(index),
                    description = "$day: $count tasks completed"
                )
            },
            config = BarChartConfig(
                showValues = true,
                showGrid = true,
                showAxisLabels = true,
                animationEnabled = true,
                animationDuration = 1000
            ),
            title = "Weekly Progress",
            subtitle = "Tasks completed this week",
            xAxisLabel = "Days",
            yAxisLabel = "Tasks"
        )
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = SemanticColors.Background.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Spacing.Elevation.sm),
        shape = RoundedCornerShape(Spacing.Radius.lg)
    ) {
        Column(
            modifier = Modifier.padding(Spacing.lg)
        ) {
            BarChart(
                data = chartData,
                onBarClick = { dataPoint ->
                    // Handle bar click - could navigate to day details
                    // or show more information
                }
            )
        }
    }
}

/**
 * Returns a color for each day of the week.
 * This creates a consistent color scheme for the weekly chart.
 */
private fun getDayColor(dayIndex: Int): Color {
    val colors = listOf(
        Color(0xFF4CAF50), // Monday - Green
        Color(0xFF2196F3), // Tuesday - Blue
        Color(0xFFFF9800), // Wednesday - Orange
        Color(0xFF9C27B0), // Thursday - Purple
        Color(0xFFF44336), // Friday - Red
        Color(0xFF00BCD4), // Saturday - Cyan
        Color(0xFF607D8B) // Sunday - Blue Grey
    )
    return colors[dayIndex % colors.size]
}

/**
 * Example usage in a dashboard screen:
 *
 * ```kotlin
 * val weeklyData = listOf(
 *     "Mon" to 8,
 *     "Tue" to 12,
 *     "Wed" to 6,
 *     "Thu" to 15,
 *     "Fri" to 10,
 *     "Sat" to 4,
 *     "Sun" to 2
 * )
 *
 * DashboardBarChart(
 *     weeklyCompletions = weeklyData,
 *     modifier = Modifier.padding(Spacing.md)
 * )
 * ```
 */
