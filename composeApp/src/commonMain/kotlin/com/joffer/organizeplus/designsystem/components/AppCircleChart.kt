package com.joffer.organizeplus.designsystem.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

/**
 * Data class representing a segment in the circle chart
 *
 * @param label The label for this segment
 * @param value The value for this segment
 * @param color The color for this segment
 */
data class CircleChartSegment(
    val label: String,
    val value: Float,
    val color: Color
)

/**
 * Configuration for circle chart styling and behavior
 */
data class CircleChartConfig(
    val strokeWidth: Dp = 12.dp,
    val showValues: Boolean = true,
    val showLabels: Boolean = true,
    val animate: Boolean = true,
    val animationDuration: Int = 1000,
    val emptyStateText: String = "No data available",
    val showCenterText: Boolean = true,
    val centerText: String = ""
)

// Chart constants
private val CHART_SIZE = 160.dp
private val MIN_SEGMENT_ANGLE = 0.02f // Minimum angle to show a segment (about 1.15 degrees)

/**
 * A customizable circle chart component with design system integration.
 *
 * @param title Optional chart title
 * @param data List of segments to display
 * @param config Chart configuration for styling and behavior
 * @param modifier Modifier for the chart container
 */
@Composable
fun AppCircleChart(
    title: String? = null,
    data: List<CircleChartSegment>,
    config: CircleChartConfig = CircleChartConfig(),
    modifier: Modifier = Modifier
) {
    if (data.isEmpty()) {
        EmptyCircleChartState(
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
                CircleChartTitle(
                    title = title,
                    modifier = Modifier.padding(Spacing.md)
                )
            }

            // Chart content
            CircleChartContent(
                data = data,
                config = config,
                modifier = Modifier.padding(Spacing.md)
            )
        }
    }
}

/**
 * Chart title component
 */
@Composable
private fun CircleChartTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()

    Text(
        text = title,
        style = typography.titleLarge,
        color = SemanticColors.Foreground.primary,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier
    )
}

/**
 * Main chart content with circle chart implementation
 */
@Composable
private fun CircleChartContent(
    data: List<CircleChartSegment>,
    config: CircleChartConfig,
    modifier: Modifier = Modifier
) {
    val totalValue = data.sumOf { it.value.toDouble() }.toFloat()
    
    // Animation for chart - restart animation when data changes
    val animationKey = remember(data) { data.hashCode() }
    var shouldAnimate by remember { mutableStateOf(false) }
    
    // Trigger animation restart when data changes or component is first composed
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
        label = "circle_animation_$animationKey"
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Circle chart
        Box(
            modifier = Modifier.size(CHART_SIZE),
            contentAlignment = Alignment.Center
        ) {
            CircleChartCanvas(
                data = data,
                totalValue = totalValue,
                config = config,
                animationProgress = animationProgress
            )
            
            // Center text
            if (config.showCenterText) {
                val centerText = config.centerText.ifEmpty {
                    "Total\n ${totalValue}"
                }
                
                Text(
                    text = centerText,
                    style = DesignSystemTypography().bodyMedium,
                    color = SemanticColors.Foreground.primary,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Legend
        if (config.showLabels) {
            CircleChartLegend(
                data = data,
                modifier = Modifier.padding(start = Spacing.md)
            )
        }
    }
}

/**
 * Canvas component for drawing the circle chart
 */
@Composable
private fun CircleChartCanvas(
    data: List<CircleChartSegment>,
    totalValue: Float,
    config: CircleChartConfig,
    animationProgress: Float
) {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val strokeWidthPx = config.strokeWidth.toPx()
        val radius = (size.minDimension - strokeWidthPx) / 2
        val center = size.center

        var currentAngle = -90f // Start from top

        data.forEach { segment ->
            val segmentAngle = (segment.value / totalValue) * 360f * animationProgress
            
            // Only draw segment if it has meaningful size
            if (segmentAngle >= MIN_SEGMENT_ANGLE) {
                drawArc(
                    color = segment.color,
                    startAngle = currentAngle,
                    sweepAngle = segmentAngle,
                    useCenter = false,
                    topLeft = Offset(
                        center.x - radius,
                        center.y - radius
                    ),
                    size = androidx.compose.ui.geometry.Size(
                        radius * 2,
                        radius * 2
                    ),
                    style = androidx.compose.ui.graphics.drawscope.Stroke(
                        width = strokeWidthPx,
                        cap = StrokeCap.Round
                    )
                )
                
                currentAngle += segmentAngle
            }
        }
    }
}

/**
 * Legend component for the circle chart
 */
@Composable
private fun CircleChartLegend(
    data: List<CircleChartSegment>,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.xs)
    ) {
        data.forEach { segment ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.xs)
            ) {
                // Color indicator
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            color = segment.color,
                            shape = RoundedCornerShape(2.dp)
                        )
                )
                
                // Label
                Text(
                    text = segment.label,
                    style = typography.bodySmall,
                    color = SemanticColors.Foreground.primary
                )
                
                // Value
                Text(
                    text = "(${segment.value})",
                    style = typography.bodySmall,
                    color = SemanticColors.Foreground.secondary
                )
            }
        }
    }
}

/**
 * Empty state when no data is available
 */
@Composable
private fun EmptyCircleChartState(
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
                .height(CHART_SIZE + Spacing.md * 2),
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

