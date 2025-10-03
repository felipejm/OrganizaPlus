package com.joffer.organizeplus.designsystem.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.common.utils.formatString
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

data class GaugeChartData(
    val value: Float,
    val minValue: Float = 0f,
    val maxValue: Float = 100f,
    val unit: String = "%",
    val color: Color = SemanticColors.Foreground.brand,
    val backgroundColor: Color = SemanticColors.Background.surfaceVariant,
    val label: String = "",
    val subtitle: String = ""
)

data class GaugeChartConfig(
    val strokeWidth: Dp = Spacing.Chart.strokeWidth,
    val startAngle: Float = 135f, // Start angle in degrees (0° is at 3 o'clock)
    val sweepAngle: Float = 270f, // Sweep angle in degrees
    val showValue: Boolean = true,
    val showLabel: Boolean = true,
    val showSubtitle: Boolean = true,
    val animate: Boolean = true,
    val animationDuration: Int = Spacing.Chart.animationDuration,
    val emptyStateText: String = "No data available"
)

// Chart constants
private val GAUGE_SIZE = 100.dp
private val MIN_GAUGE_ANGLE = 5f // Minimum angle to show a gauge segment

@Composable
fun AppGaugeChart(
    data: GaugeChartData,
    config: GaugeChartConfig = GaugeChartConfig(),
    modifier: Modifier = Modifier
) {
    if (data.value < data.minValue || data.value > data.maxValue) {
        EmptyGaugeChartState(
            message = config.emptyStateText,
            modifier = modifier
        )
        return
    }

    OrganizeCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.md),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Chart title
            if (config.showLabel && data.label.isNotEmpty()) {
                GaugeChartTitle(
                    title = data.label,
                    modifier = Modifier.padding(bottom = Spacing.sm)
                )
            }

            // Gauge content
            GaugeChartContent(
                data = data,
                config = config,
                modifier = Modifier.padding(Spacing.md)
            )

            // Subtitle
            if (config.showSubtitle && data.subtitle.isNotEmpty()) {
                GaugeChartSubtitle(
                    subtitle = data.subtitle,
                    modifier = Modifier.padding(top = Spacing.sm)
                )
            }
        }
    }
}

/**
 * Main gauge chart content with Canvas drawing
 */
@Composable
private fun GaugeChartContent(
    data: GaugeChartData,
    config: GaugeChartConfig,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    val density = LocalDensity.current

    // Calculate the progress percentage
    val progress = (data.value - data.minValue) / (data.maxValue - data.minValue)
    val clampedProgress = progress.coerceIn(0f, 1f)

    // Animation for gauge - restart animation when data changes or component is first composed
    val animationKey = remember(data.value, data.minValue, data.maxValue) {
        "${data.value}_${data.minValue}_${data.maxValue}".hashCode()
    }
    var shouldAnimate by remember { mutableStateOf(false) }

    LaunchedEffect(animationKey) {
        shouldAnimate = false
        delay(50) // Small delay to ensure state reset
        shouldAnimate = true
    }

    val animationProgress by animateFloatAsState(
        targetValue = if (config.animate && shouldAnimate) 1f else 0f,
        animationSpec = tween(
            durationMillis = config.animationDuration,
            easing = EaseOutCubic
        ),
        label = "gauge_animation_$animationKey"
    )

    Box(
        modifier = modifier.size(GAUGE_SIZE),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = this.center
            val radius = (size.minDimension - config.strokeWidth.toPx()) / 2f

            // Draw background arc
            drawArc(
                color = data.backgroundColor,
                startAngle = config.startAngle,
                sweepAngle = config.sweepAngle,
                useCenter = false,
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = config.strokeWidth.toPx(),
                    cap = StrokeCap.Round
                ),
                topLeft = Offset(
                    center.x - radius,
                    center.y - radius
                ),
                size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
            )

            // Draw progress arc
            val animatedProgress = clampedProgress * animationProgress
            val progressSweepAngle = config.sweepAngle * animatedProgress

            if (progressSweepAngle > MIN_GAUGE_ANGLE) {
                drawArc(
                    color = data.color,
                    startAngle = config.startAngle,
                    sweepAngle = progressSweepAngle,
                    useCenter = false,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(
                        width = config.strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    ),
                    topLeft = Offset(
                        center.x - radius,
                        center.y - radius
                    ),
                    size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
                )
            }
        }

        // Center value text
        if (config.showValue) {
            val animatedValue = data.value * animationProgress
            val valueText = if (data.unit == "%") {
                "${animatedValue.toInt()}${data.unit}"
            } else {
                "${formatString("%.1f", animatedValue)}${data.unit}"
            }

            Text(
                text = valueText,
                style = typography.titleMedium,
                color = data.color,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

/**
 * Gauge chart title component
 */
@Composable
private fun GaugeChartTitle(
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
 * Gauge chart subtitle component
 */
@Composable
private fun GaugeChartSubtitle(
    subtitle: String,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()

    Text(
        text = subtitle,
        style = typography.bodyMedium,
        color = SemanticColors.Foreground.secondary,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

/**
 * Empty state when no data is available or data is invalid
 */
@Composable
private fun EmptyGaugeChartState(
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
                .height(GAUGE_SIZE),
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
