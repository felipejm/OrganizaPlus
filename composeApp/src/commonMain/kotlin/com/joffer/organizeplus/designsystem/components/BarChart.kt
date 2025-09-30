package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography

/**
 * A simple bar chart component placeholder
 *
 * @param data List of chart data points with x-axis labels and y-axis values
 * @param title Optional title for the chart
 * @param legend Optional legend text
 * @param barColor Color of the bars (defaults to primary color)
 * @param showValues Whether to show values on the chart
 * @param modifier Modifier for the chart container
 */
@Composable
fun BarChart(
    data: List<ChartDataPoint>,
    title: String? = null,
    legend: String? = null,
    barColor: Color = AppColorScheme.primary,
    showValues: Boolean = true,
    modifier: Modifier = Modifier
) {
    if (data.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    color = AppColorScheme.surfaceVariant,
                    shape = RoundedCornerShape(Spacing.borderRadius)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No data available",
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
                shape = RoundedCornerShape(Spacing.borderRadius)
            )
            .padding(Spacing.md)
    ) {
        // Chart title
        title?.let {
            Text(
                text = it,
                style = Typography.titleMedium,
                color = AppColorScheme.formText
            )
            Spacer(modifier = Modifier.height(Spacing.md))
        }

        // Simple chart representation
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            data.forEach { dataPoint ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = dataPoint.label,
                        style = Typography.bodySmall,
                        color = AppColorScheme.formText,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .width((dataPoint.value * 2).dp.coerceAtMost(200.dp))
                            .height(20.dp)
                            .background(
                                color = barColor,
                                shape = RoundedCornerShape(4.dp)
                            )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = dataPoint.value.toString(),
                        style = Typography.bodySmall,
                        color = AppColorScheme.formText
                    )
                }
            }
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