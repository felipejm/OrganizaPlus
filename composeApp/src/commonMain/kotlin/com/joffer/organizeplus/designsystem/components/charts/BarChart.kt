package com.joffer.organizeplus.designsystem.components.charts

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography

@Composable
fun BarChart(
    data: BarChartData,
    modifier: Modifier = Modifier
) {
    if (!data.isValid) {
        EmptyChartState(modifier = modifier)
        return
    }

    val typography = DesignSystemTypography()
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()

    // Animation state
    val animationProgress = remember { Animatable(0f) }

    // Launch animation when data changes
    LaunchedEffect(data) {
        if (data.config.animationEnabled) {
            animationProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = data.config.animationDuration)
            )
        } else {
            animationProgress.snapTo(1f)
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = SemanticColors.Background.surface,
                shape = RoundedCornerShape(Spacing.Radius.lg)
            )
            .padding(Spacing.lg)
    ) {
        // Chart title and subtitle
        if (data.title != null || data.subtitle != null) {
            ChartHeader(
                title = data.title,
                subtitle = data.subtitle,
                typography = typography
            )
            Spacer(modifier = Modifier.height(Spacing.lg))
        }

        // Main chart area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Spacing.Chart.height)
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                drawBarChart(
                    data = data,
                    animationProgress = animationProgress.value,
                )
            }
        }

        // Axis labels
        if (data.config.showAxisLabels) {
            Spacer(modifier = Modifier.height(Spacing.sm))
            AxisLabels(
                xAxisLabel = data.xAxisLabel,
                yAxisLabel = data.yAxisLabel,
                typography = typography
            )
        }
    }
}

@Composable
private fun ChartHeader(
    title: String?,
    subtitle: String?,
    typography: com.joffer.organizeplus.designsystem.typography.Typography
) {
    Column {
        title?.let {
            androidx.compose.material3.Text(
                text = it,
                style = typography.titleMedium,
                color = SemanticColors.Foreground.primary
            )
        }
        subtitle?.let {
            androidx.compose.material3.Text(
                text = it,
                style = typography.bodyMedium,
                color = SemanticColors.Foreground.secondary
            )
        }
    }
}

@Composable
private fun AxisLabels(
    xAxisLabel: String?,
    yAxisLabel: String?,
    typography: com.joffer.organizeplus.designsystem.typography.Typography
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        xAxisLabel?.let {
            androidx.compose.material3.Text(
                text = it,
                style = typography.bodySmall,
                color = SemanticColors.Foreground.tertiary
            )
        }
        yAxisLabel?.let {
            androidx.compose.material3.Text(
                text = it,
                style = typography.bodySmall,
                color = SemanticColors.Foreground.tertiary
            )
        }
    }
}

@Composable
private fun EmptyChartState(
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(Spacing.Chart.emptyStateHeight)
            .background(
                color = SemanticColors.Background.surface,
                shape = RoundedCornerShape(Spacing.Radius.lg)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            androidx.compose.material3.Text(
                text = "No data available",
                style = typography.bodyMedium,
                color = SemanticColors.Foreground.secondary
            )
        }
    }
}

private fun DrawScope.drawBarChart(
    data: BarChartData,
    animationProgress: Float,
) {
    val chartWidth = size.width
    val chartHeight = size.height
    val padding = 40f
    val availableWidth = chartWidth - padding * 2
    val availableHeight = chartHeight - padding * 2

    val dataPoints = data.dataPoints
    val maxValue = data.maxValue
    val barCount = dataPoints.size

    if (barCount == 0) return

    // Calculate bar dimensions
    val barWidth = availableWidth / barCount * (1f - data.config.barSpacing)
    val barSpacing = availableWidth / barCount * data.config.barSpacing

    // Draw grid lines if enabled
    if (data.config.showGrid) {
        drawGridLines(
            startX = padding,
            endX = chartWidth - padding,
            startY = padding,
            endY = chartHeight - padding
        )
    }

    // Draw bars
    dataPoints.forEachIndexed { index, dataPoint ->
        val barX = padding + index * (barWidth + barSpacing) + barSpacing / 2
        val barHeight = dataPoint.value / maxValue * availableHeight * animationProgress
        val barY = chartHeight - padding - barHeight

        // Draw bar
        val barColor = dataPoint.color ?: getDefaultBarColor(index)
        drawRect(
            color = barColor,
            topLeft = Offset(barX, barY),
            size = Size(barWidth, barHeight)
        )
    }
}

private fun DrawScope.drawGridLines(
    startX: Float,
    endX: Float,
    startY: Float,
    endY: Float
) {
    val gridColor = SemanticColors.Border.secondary.copy(alpha = 0.3f)
    val strokeWidth = 1f

    // Horizontal grid lines
    val horizontalLines = 5
    for (i in 0..horizontalLines) {
        val y = startY + (endY - startY) * (i.toFloat() / horizontalLines)
        drawLine(
            color = gridColor,
            start = Offset(startX, y),
            end = Offset(endX, y),
            strokeWidth = strokeWidth
        )
    }

    // Vertical grid lines
    val verticalLines = 4
    for (i in 0..verticalLines) {
        val x = startX + (endX - startX) * (i.toFloat() / verticalLines)
        drawLine(
            color = gridColor,
            start = Offset(x, startY),
            end = Offset(x, endY),
            strokeWidth = strokeWidth
        )
    }
}

private fun getDefaultBarColor(index: Int): Color {
    val colors = listOf(
        SemanticColors.Foreground.interactive,
        SemanticColors.Foreground.success,
        SemanticColors.Foreground.warning,
        SemanticColors.Foreground.error,
        SemanticColors.Foreground.info
    )
    return colors[index % colors.size]
}
