package com.joffer.organizeplus.designsystem.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.joffer.organizeplus.common.utils.toCurrencyFormat
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography

data class ChartDataPoint(
    val label: String,
    val value: Float
)

data class ChartConfig(
    val barColors: List<Color> = listOf(SemanticColors.Foreground.brand),
    val showValues: Boolean = true,
    val showLabels: Boolean = true,
    val animate: Boolean = true,
    val animationDuration: Int = 800,
    val emptyStateText: String = "No data available"
)

// Chart constants
private val CHART_HEIGHT = 120.dp
private val MIN_BAR_HEIGHT = 4.dp
private val MAX_BAR_WIDTH = 30.dp
private val BAR_SPACING = 2.dp
private val VALUE_PADDING = Spacing.xs
private val LABEL_PADDING = Spacing.xs

@Composable
fun AppBarChart(
    title: String? = null,
    data: List<ChartDataPoint>,
    config: ChartConfig = ChartConfig(),
    modifier: Modifier = Modifier
) {
    if (data.isEmpty()) {
        EmptyChartState(
            message = config.emptyStateText,
            modifier = modifier
        )
        return
    }

    OrganizeCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Chart title
            if (title != null) {
                ChartTitle(
                    title = title,
                    modifier = Modifier.padding(Spacing.md)
                )
            }

            // Chart content
            ChartContent(
                data = data,
                config = config,
                modifier = Modifier.padding(
                    horizontal = Spacing.md,
                    vertical = Spacing.sm
                )
            )
        }
    }
}

/**
 * Chart title component
 */
@Composable
private fun ChartTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()

    Text(
        text = title,
        style = typography.titleLarge,
        color = SemanticColors.Foreground.primary,
        modifier = modifier
    )
}

/**
 * Main chart content with KoalaPlot integration
 */
@Composable
private fun ChartContent(
    data: List<ChartDataPoint>,
    config: ChartConfig,
    modifier: Modifier = Modifier
) {
    val maxValue = data.maxOfOrNull { it.value }?.coerceAtLeast(1f) ?: 1f
    val barWidthProportion = 0.8f / data.size

    // Animation for bars - restart animation when data changes or component is first composed
    val animationKey = remember(data) { data.hashCode() }
    var shouldAnimate by remember { mutableStateOf(false) }

    // Trigger animation restart when data changes or component is first composed
    LaunchedEffect(animationKey) {
        shouldAnimate = false
        kotlinx.coroutines.delay(50) // Small delay to ensure state reset
        shouldAnimate = true
    }

    val animationProgress by animateFloatAsState(
        targetValue = if (config.animate && shouldAnimate) 1f else 0f,
        animationSpec = tween(
            durationMillis = config.animationDuration,
            easing = EaseOutCubic
        ),
        label = "bar_animation_$animationKey"
    )
    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        data.forEachIndexed { index, point ->
            BarColumn(
                point = point,
                maxValue = maxValue,
                barWidth = min((barWidthProportion * 200).dp, MAX_BAR_WIDTH),
                barColor = config.barColors[index % config.barColors.size],
                showValue = config.showValues,
                showLabel = config.showLabels,
                animationProgress = animationProgress
            )
        }
    }
}

/**
 * Individual bar column component
 */
@Composable
private fun BarColumn(
    point: ChartDataPoint,
    maxValue: Float,
    barWidth: Dp,
    barColor: Color,
    showValue: Boolean,
    showLabel: Boolean,
    animationProgress: Float,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    val heightRatio = point.value / maxValue
    val animatedHeight = (CHART_HEIGHT * heightRatio * animationProgress).coerceAtLeast(MIN_BAR_HEIGHT)

    Column(
        modifier = modifier.height(CHART_HEIGHT + VALUE_PADDING + LABEL_PADDING + 24.dp), // Fixed height
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        // Value label above bar
        if (showValue) {
            Text(
                text = point.value.toDouble().toCurrencyFormat(),
                style = typography.bodySmall,
                color = SemanticColors.Foreground.secondary,
                textAlign = TextAlign.Center,
            )
        }

        // Bar visualization
        BarVisualization(
            height = animatedHeight,
            width = barWidth,
            color = barColor,
            modifier = Modifier.padding(horizontal = BAR_SPACING)
        )

        // Label below bar
        if (showLabel) {
            Text(
                text = point.label,
                style = typography.bodySmall,
                color = SemanticColors.Foreground.secondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = LABEL_PADDING)
            )
        }
    }
}

/**
 * Individual bar visualization component
 */
@Composable
private fun BarVisualization(
    height: Dp,
    width: Dp,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(width)
            .height(height)
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            colors = CardDefaults.cardColors(containerColor = color),
            shape = RoundedCornerShape(4.dp)
        ) {}
    }
}

/**
 * Empty state when no data is available
 */
@Composable
private fun EmptyChartState(
    message: String,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()

    OrganizeCard(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(CHART_HEIGHT),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = message,
                style = typography.bodyMedium,
                color = SemanticColors.Foreground.secondary,
                textAlign = TextAlign.Center
            )
        }
    }
}
