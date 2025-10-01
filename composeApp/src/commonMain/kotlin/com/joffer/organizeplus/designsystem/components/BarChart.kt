package com.joffer.organizeplus.designsystem.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joffer.organizeplus.designsystem.spacing.Spacing
import kotlin.math.round
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

// Chart dimensions and styling constants
private val CHART_HEIGHT = 240.dp
private val CHART_PADDING = 16.dp
private val Y_AXIS_LABEL_PADDING = 24.dp
private val RIGHT_PADDING = 16.dp
private const val CHART_LABEL_PADDING = 40f
private val BAR_WIDTH = 18.dp
private val BAR_SPACING = 12.dp
private val BAR_CORNER_RADIUS = 4.dp

/**
 * Custom bar chart component for displaying data with configurable colors
 * Based on the Custom-Bar-Chart-Jetpack-Compose implementation
 * https://github.com/developerchunk/Custom-Bar-Chart-Jetpack-Compose
 *
 * @param data List of data points
 * @param title Optional chart title
 * @param barColors List of colors to use for bars (cycled if more bars than colors)
 * @param emptyStateText Text to show when no data is available
 * @param modifier Modifier for the chart container
 */
@Composable
fun AppBarChart(
    data: List<ChartDataPoint>,
    title: String? = null,
    barColors: List<Color> = listOf(
        AppColorScheme.success500, // Green
        AppColorScheme.error, // Red
        AppColorScheme.warning500, // Amber
        AppColorScheme.primary, // Blue
        AppColorScheme.secondary, // Purple
        AppColorScheme.info500, // Info blue
    ),
    emptyStateText: String = "No data available",
    modifier: Modifier = Modifier
) {
    if (data.isEmpty()) {
        EmptyChartState(
            emptyStateText = emptyStateText,
            modifier = modifier
        )
        return
    }

    val density = LocalDensity.current
    val animatedData = remember(data) {
        data.map { point ->
            point.copy(animatedValue = 0f)
        }
    }

    // Animate each bar from 0 to its target value
    val animatedValues by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(
            durationMillis = 800,
            easing = EaseOutCubic
        ),
        label = "bar_animation"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = AppColorScheme.surface,
                shape = RoundedCornerShape(Spacing.Radius.lg)
            )
            .clip(RoundedCornerShape(Spacing.Radius.lg))
            .padding(CHART_PADDING)
    ) {
        // Chart title
        title?.let {
            Text(
                text = it,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppColorScheme.onSurface
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Chart content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(CHART_HEIGHT)
        ) {
            CustomBarChart(
                data = animatedData.map { point ->
                    point.copy(animatedValue = point.value * animatedValues)
                },
                barColors = barColors,
                modifier = Modifier.fillMaxSize()
            )

            // X-axis labels positioned over the canvas
            XAxisLabelsOverlay(
                data = data,
                modifier = Modifier.fillMaxSize()
            )

            // Y-axis labels positioned over the canvas
            YAxisLabelsOverlay(
                data = animatedData.map { point ->
                    point.copy(animatedValue = point.value * animatedValues)
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun CustomBarChart(
    data: List<ChartDataPoint>,
    barColors: List<Color>,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current

    // Calculate chart dimensions
    val maxValue = data.maxOfOrNull { it.value }?.coerceAtLeast(1f) ?: 1f
    val yAxisTicks = generateYAxisTicks(maxValue)

    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val padding = CHART_LABEL_PADDING
        val chartWidth = canvasWidth - padding * 2
        val chartHeight = canvasHeight - padding * 2
        val chartStartX = padding
        val chartStartY = padding
        val chartEndX = canvasWidth - padding
        val chartEndY = canvasHeight - padding

        // Draw axes
        drawAxes(
            startX = chartStartX,
            startY = chartStartY,
            endX = chartEndX,
            endY = chartEndY
        )

        // Draw grid lines
        drawGridLines(
            startX = chartStartX,
            endX = chartEndX,
            endY = chartEndY,
            ticks = yAxisTicks,
            maxValue = maxValue,
            chartHeight = chartHeight
        )

        // Draw Y-axis labels
        drawYAxisLabels(
            startX = chartStartX,
            endY = chartEndY,
            ticks = yAxisTicks,
            maxValue = maxValue,
            chartHeight = chartHeight
        )

        // Draw bars
        drawBars(
            data = data,
            barColors = barColors,
            startX = chartStartX,
            endX = chartEndX,
            endY = chartEndY,
            chartHeight = chartHeight,
            maxValue = maxValue
        )

        // Draw X-axis labels
        drawXAxisLabels(
            data = data,
            startX = chartStartX,
            endX = chartEndX,
            endY = chartEndY
        )
    }
}

private fun generateYAxisTicks(maxValue: Float): List<Float> {
    return when {
        maxValue <= 1f -> {
            listOf(0f, 1f)
        }
        maxValue <= 2f -> {
            listOf(0f, 1f, 2f)
        }
        maxValue <= 3f -> {
            listOf(0f, 1f, 2f, 3f)
        }
        maxValue <= 5f -> {
            listOf(0f, 1f, 2f, 3f, 4f, 5f)
        }
        maxValue <= 10f -> {
            listOf(0f, 2f, 4f, 6f, 8f, 10f)
        }
        else -> {
            val step = (maxValue / 5f).let { if (it < 1f) 1f else it }
            (0..5).map { it * step }
        }
    }
}

private fun DrawScope.drawAxes(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float
) {
    // X-axis
    drawLine(
        color = AppColorScheme.formSecondaryText,
        start = Offset(startX, endY),
        end = Offset(endX, endY),
        strokeWidth = 2.dp.toPx()
    )

    // Y-axis
    drawLine(
        color = AppColorScheme.formSecondaryText,
        start = Offset(startX, startY),
        end = Offset(startX, endY),
        strokeWidth = 2.dp.toPx()
    )
}

private fun DrawScope.drawYAxisLabels(
    startX: Float,
    endY: Float,
    ticks: List<Float>,
    maxValue: Float,
    chartHeight: Float
) {
    ticks.forEach { tickValue ->
        val y = endY - tickValue / maxValue * chartHeight

        // Draw tick mark
        drawLine(
            color = AppColorScheme.formSecondaryText,
            start = Offset(startX - 8.dp.toPx(), y),
            end = Offset(startX, y),
            strokeWidth = 1.dp.toPx()
        )

        // Note: Text rendering requires platform-specific implementation
        // For now, we'll focus on the chart structure and bars
    }
}

private fun DrawScope.drawXAxisLabels(
    data: List<ChartDataPoint>,
    startX: Float,
    endX: Float,
    endY: Float
) {
    val barWidth = BAR_WIDTH.toPx()
    val barSpacing = BAR_SPACING.toPx()
    val totalBarWidth = barWidth + barSpacing
    val chartWidth = endX - startX
    val startOffset = (chartWidth - (totalBarWidth * data.size - barSpacing)) / 2

    data.forEachIndexed { index, point ->
        val x = startX + startOffset + totalBarWidth * index + barWidth / 2
        val y = endY + 20.dp.toPx()

        // Note: Text rendering requires platform-specific implementation
        // For now, we'll focus on the chart structure and bars
    }
}

private fun DrawScope.drawGridLines(
    startX: Float,
    endX: Float,
    endY: Float,
    ticks: List<Float>,
    maxValue: Float,
    chartHeight: Float
) {
    // Draw horizontal grid lines
    ticks.forEach { tickValue ->
        val y = endY - tickValue / maxValue * chartHeight
        drawLine(
            color = AppColorScheme.formSecondaryText.copy(alpha = 0.2f),
            start = Offset(startX, y),
            end = Offset(endX, y),
            strokeWidth = 1.dp.toPx()
        )
    }
}

private fun DrawScope.drawBars(
    data: List<ChartDataPoint>,
    barColors: List<Color>,
    startX: Float,
    endX: Float,
    endY: Float,
    chartHeight: Float,
    maxValue: Float
) {
    val barWidth = BAR_WIDTH.toPx()
    val barSpacing = BAR_SPACING.toPx()
    val cornerRadius = BAR_CORNER_RADIUS.toPx()
    val totalBarWidth = barWidth + barSpacing
    val chartWidth = endX - startX
    val startOffset = (chartWidth - (totalBarWidth * data.size - barSpacing)) / 2

    data.forEachIndexed { index, point ->
        val x = startX + startOffset + totalBarWidth * index
        val barHeight = point.animatedValue / maxValue * chartHeight
        val y = endY - barHeight

        // Get bar color from the provided colors list (cycle if needed)
        val barColor = barColors[index % barColors.size]

        // Draw bar with rounded top corners
        if (barHeight > 0) {
            drawRoundRect(
                color = barColor,
                topLeft = Offset(x, y),
                size = Size(barWidth, barHeight),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(
                    packedValue = cornerRadius.toLong()
                )
            )
        }
    }
}

@Composable
private fun XAxisLabelsOverlay(
    data: List<ChartDataPoint>,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = CHART_HEIGHT - 20.dp)
                .padding(horizontal = 40.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            data.forEach { point ->
                Text(
                    text = point.label,
                    fontSize = 12.sp,
                    color = AppColorScheme.formSecondaryText,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun YAxisLabelsOverlay(
    data: List<ChartDataPoint>,
    modifier: Modifier = Modifier
) {
    val maxValue = data.maxOfOrNull { it.value }?.coerceAtLeast(1f) ?: 1f
    val yAxisTicks = generateYAxisTicks(maxValue)
    val padding = 40.dp

    Box(modifier = modifier) {
        yAxisTicks.forEach { tickValue ->
            val chartHeight = (CHART_HEIGHT - padding * 2).value
            val y = (CHART_HEIGHT - padding).value - tickValue / maxValue * chartHeight

            Text(
                text = round(tickValue).toString(),
                modifier = Modifier
                    .offset(x = 8.dp, y = y.dp - 8.dp),
                fontSize = 12.sp,
                color = AppColorScheme.formSecondaryText,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun EmptyChartState(
    emptyStateText: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(CHART_HEIGHT)
            .background(
                color = AppColorScheme.surface,
                shape = RoundedCornerShape(Spacing.Radius.lg)
            )
            .padding(CHART_PADDING),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emptyStateText,
            style = TextStyle(
                fontSize = 14.sp,
                color = AppColorScheme.formSecondaryText
            )
        )
    }
}

/**
 * Data class representing a single point in the chart
 *
 * @param label The label for the x-axis (e.g., month abbreviation)
 * @param value The value for the y-axis
 * @param animatedValue The animated value for smooth transitions (internal use)
 */
data class ChartDataPoint(
    val label: String,
    val value: Float,
    val animatedValue: Float = value
)
