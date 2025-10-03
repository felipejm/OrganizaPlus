package com.joffer.organizeplus.designsystem.components.charts

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography

@Composable
fun BarChart(
    data: BarChartData,
    modifier: Modifier = Modifier,
    onBarClick: ((BarDataPoint) -> Unit)? = null
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
                    textMeasurer = textMeasurer,
                    density = density,
                    typography = typography,
                    onBarClick = onBarClick
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
    textMeasurer: androidx.compose.ui.text.TextMeasurer,
    _: androidx.compose.ui.unit.Density,
    typography: com.joffer.organizeplus.designsystem.typography.Typography,
    _: ((BarDataPoint) -> Unit)?
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
            endY = chartHeight - padding,
            maxValue = maxValue
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

        // Draw value label if enabled
        if (data.config.showValues && barHeight > 20f) {
            val valueText = formatValue(dataPoint.value)
            val textStyle = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = SemanticColors.Foreground.primary
            )

            val textLayoutResult = textMeasurer.measure(
                text = valueText,
                style = textStyle
            )

            val textX = barX + (barWidth - textLayoutResult.size.width) / 2
            val textY = barY - textLayoutResult.size.height - 4f

            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(textX, textY)
            )
        }

        // Draw bar label
        val labelText = dataPoint.label
        val labelStyle = TextStyle(
            fontSize = 10.sp,
            color = SemanticColors.Foreground.secondary,
            textAlign = TextAlign.Center
        )

        val labelLayoutResult = textMeasurer.measure(
            text = labelText,
            style = labelStyle
        )

        val labelX = barX + (barWidth - labelLayoutResult.size.width) / 2
        val labelY = chartHeight - padding + 16f

        drawText(
            textLayoutResult = labelLayoutResult,
            topLeft = Offset(labelX, labelY)
        )
    }
}

private fun DrawScope.drawGridLines(
    startX: Float,
    endX: Float,
    startY: Float,
    endY: Float,
    _: Float
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

private fun formatValue(value: Float): String {
    return when {
        value >= 1000000 -> "${(value / 1000000).toInt()}M"
        value >= 1000 -> "${(value / 1000).toInt()}K"
        value == value.toInt().toFloat() -> value.toInt().toString()
        else -> String.format("%.1f", value)
    }
}
