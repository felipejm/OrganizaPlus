package com.joffer.organizeplus.designsystem.components.charts

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Represents a single data point in a bar chart.
 *
 * @param label The label displayed below the bar
 * @param value The numeric value represented by the bar height
 * @param color Optional custom color for this specific bar
 * @param description Optional description for accessibility
 */
@Immutable
data class BarDataPoint(
    val label: String,
    val value: Float,
    val color: Color? = null,
    val description: String? = null
)

/**
 * Configuration for bar chart styling and behavior.
 *
 * @param showValues Whether to display value labels on top of bars
 * @param showGrid Whether to display grid lines
 * @param showAxisLabels Whether to display axis labels
 * @param animationEnabled Whether to animate bar appearance
 * @param animationDuration Duration of animation in milliseconds
 * @param barSpacing Spacing between bars as a fraction of bar width
 * @param maxValue Optional maximum value for consistent scaling across charts
 */
@Immutable
data class BarChartConfig(
    val showValues: Boolean = true,
    val showGrid: Boolean = true,
    val showAxisLabels: Boolean = true,
    val animationEnabled: Boolean = true,
    val animationDuration: Int = 1200,
    val barSpacing: Float = 0.2f, // 20% of bar width
    val maxValue: Float? = null
)

/**
 * Complete data structure for a bar chart.
 *
 * @param dataPoints List of data points to display
 * @param config Chart configuration options
 * @param title Optional chart title
 * @param subtitle Optional chart subtitle
 * @param xAxisLabel Optional X-axis label
 * @param yAxisLabel Optional Y-axis label
 */
@Immutable
data class BarChartData(
    val dataPoints: List<BarDataPoint>,
    val config: BarChartConfig = BarChartConfig(),
    val title: String? = null,
    val subtitle: String? = null,
    val xAxisLabel: String? = null,
    val yAxisLabel: String? = null
) {
    /**
     * Calculates the maximum value from data points or uses configured max value.
     */
    val maxValue: Float
        get() = config.maxValue ?: dataPoints.maxOfOrNull { it.value } ?: 0f

    /**
     * Validates that the chart data is valid for rendering.
     */
    val isValid: Boolean
        get() = dataPoints.isNotEmpty() && dataPoints.all { it.value >= 0f }
}
