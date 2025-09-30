package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.barChart.BarChart
import com.aay.compose.barChart.model.BarParameters
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
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
 * A bar chart component using AAY-chart library
 * Supports multiple data series with different colors
 *
 * @param dataSeries List of data series, each containing multiple data points
 * @param xAxisLabels Labels for the x-axis (e.g., months)
 * @param title Optional title for the chart
 * @param legend Optional legend text
 * @param showValues Whether to show values on the chart
 * @param modifier Modifier for the chart container
 */
@Composable
fun AppBarChart(
    dataSeries: List<ChartDataSeries>,
    xAxisLabels: List<String>,
    title: String? = null,
    legend: String? = null,
    showValues: Boolean = true,
    modifier: Modifier = Modifier
) {
    if (dataSeries.isEmpty() || xAxisLabels.isEmpty()) {
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

        // AAY-chart BarChart
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(CHART_HEIGHT)
        ) {
            val testBarParameters: List<BarParameters> = dataSeries.mapIndexed { index, series ->
                BarParameters(
                    dataName = series.name,
                    data = series.values,
                    barColor = BAR_COLOR_PALETTE[index % BAR_COLOR_PALETTE.size]
                )
            }

            val maxValue = dataSeries.maxOfOrNull { series -> series.values.maxOrNull() ?: 0.0 } ?: 0.0

            BarChart(
                chartParameters = testBarParameters,
                gridColor = AppColorScheme.formSecondaryText.copy(alpha = 0.3f),
                xAxisData = xAxisLabels,
                isShowGrid = true,
                animateChart = true,
                showGridWithSpacer = true,
                yAxisStyle = TextStyle(
                    fontSize = 12.sp,
                    color = AppColorScheme.formText,
                    fontWeight = FontWeight.Normal
                ),
                xAxisStyle = TextStyle(
                    fontSize = 12.sp,
                    color = AppColorScheme.formText,
                    fontWeight = FontWeight.W400
                ),
                yAxisRange = (maxValue * 1.2).toInt().coerceAtLeast(50),
                barWidth = 20.dp
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
 * Data class representing a series of data points in the chart
 *
 * @param name The name of the data series
 * @param values The values for each data point in the series
 */
data class ChartDataSeries(
    val name: String,
    val values: List<Double>
)

/**
 * Data class representing a single point in the chart (for backward compatibility)
 *
 * @param label The label for the x-axis
 * @param value The value for the y-axis
 */
data class ChartDataPoint(
    val label: String,
    val value: Float
)