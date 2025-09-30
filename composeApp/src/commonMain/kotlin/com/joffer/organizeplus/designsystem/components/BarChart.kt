package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import network.chaintech.cmpcharts.ui.barchart.BarChart
import network.chaintech.cmpcharts.ui.barchart.config.BarChartConfig
import network.chaintech.cmpcharts.ui.barchart.config.BarChartStyle
import network.chaintech.cmpcharts.ui.barchart.config.SelectionHighlightData
import network.chaintech.cmpcharts.axis.AxisProperties
import network.chaintech.cmpcharts.ui.barchart.config.BarData
import network.chaintech.cmpcharts.common.model.Point
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.chart_no_data_available

// Chart dimensions - using design system spacing
private val CHART_HEIGHT = Spacing.Chart.height
private val EMPTY_STATE_HEIGHT = Spacing.Chart.emptyStateHeight

// Color palette for bars
private val BAR_COLOR_PALETTE = listOf(
    AppColorScheme.primary,
    AppColorScheme.secondary,
    AppColorScheme.success500,
    AppColorScheme.warning500,
    AppColorScheme.error,
    AppColorScheme.info500,
    AppColorScheme.tertiary,
    AppColorScheme.auxiliary500
)

/**
 * A vertical bar chart component using CMPCharts library
 * Each bar uses a different color from the design system color palette
 *
 * @param data List of chart data points with x-axis labels and y-axis values
 * @param title Optional title for the chart
 * @param legend Optional legend text
 * @param showValues Whether to show values on the chart
 * @param modifier Modifier for the chart container
 */
@Composable
fun AppBarChart(
    data: List<ChartDataPoint>,
    title: String? = null,
    legend: String? = null,
    showValues: Boolean = true,
    modifier: Modifier = Modifier
) {
    if (data.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(EMPTY_STATE_HEIGHT)
                .background(
                    color = AppColorScheme.surfaceVariant,
                    shape = RoundedCornerShape(Spacing.Radius.lg)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(Res.string.chart_no_data_available),
                style = Typography.bodyMedium,
                color = AppColorScheme.formSecondaryText
            )
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = AppColorScheme.surfaceVariant,
                shape = RoundedCornerShape(Spacing.Radius.lg)
            )
            .padding(Spacing.md)
    ) {
        // Chart title
        title?.let {
            Text(
                text = it,
                style = Typography.headlineSmall,
                color = AppColorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(Spacing.md))
        }

        // CMPCharts BarChart
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(CHART_HEIGHT)
        ) {
            val maxValue = data.maxOfOrNull { it.value } ?: 0f
            val maxRange = if (maxValue > 0) (maxValue * 1.2f).toInt() else 100
            val yStepSize = 5

            // Convert our data to CMPCharts format with different colors
            val barData = data.mapIndexed { index, dataPoint ->
                BarData(
                    point = Point(
                        index.toFloat(),
                        dataPoint.value
                    ),
                    label = dataPoint.label,
                    color = BAR_COLOR_PALETTE[index % BAR_COLOR_PALETTE.size]
                )
            }

            val xAxisData = AxisProperties(
                font = FontFamily.Default,
                initialDrawPadding = Spacing.xl,
                labelColor = AppColorScheme.formText,
                lineColor = AppColorScheme.formSecondaryText,
                labelFormatter = { index: Int -> 
                    if (index < barData.size) barData[index].label else ""
                }
            )

            val yAxisData = AxisProperties(
                font = FontFamily.Default,
                offset = Spacing.md,
                labelColor = AppColorScheme.formText,
                lineColor = AppColorScheme.formSecondaryText,
                labelFormatter = { index: Int ->
                    (index * (maxRange / yStepSize)).toString()
                }
            )

            val barChartData = BarChartConfig(
                chartData = barData,
                xAxisData = xAxisData,
                yAxisData = yAxisData,
                barStyle = BarChartStyle(
                    barWidth = Spacing.Chart.barWidth,
                    selectionHighlightData = if (showValues) {
                        SelectionHighlightData(
                            highlightBarColor = AppColorScheme.primary.copy(alpha = 0.8f),
                            highlightTextColor = AppColorScheme.onPrimary,
                            highlightTextTypeface = FontWeight.Bold,
                            highlightTextBackgroundColor = AppColorScheme.primary,
                            popUpLabel = { _, y -> "Value: $y" }
                        )
                    } else null
                ),
                horizontalExtraSpace = Spacing.xs,
            )

            BarChart(
                modifier = Modifier.fillMaxSize(),
                barChartData = barChartData
            )
        }

        // Legend
        legend?.let {
            Spacer(modifier = Modifier.height(Spacing.sm))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = it,
                    style = Typography.labelSmall,
                    color = AppColorScheme.formSecondaryText
                )
            }
        }
    }
}

/**
 * Data class representing a single point in the chart
 *
 * @param label The label for the x-axis
 * @param value The value for the y-axis
 */
data class ChartDataPoint(
    val label: String,
    val value: Float
)