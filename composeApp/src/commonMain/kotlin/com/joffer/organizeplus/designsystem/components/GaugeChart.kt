package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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

/**
 * A simple gauge chart component placeholder
 * 
 * @param value Current value to display on the gauge
 * @param maxValue Maximum value for the gauge scale
 * @param title Optional title for the chart
 * @param subtitle Optional subtitle for the chart
 * @param unit Unit of measurement (e.g., "%", "R$", "kg")
 * @param gaugeColor Color of the gauge (defaults to primary color)
 * @param backgroundColor Background color of the gauge
 * @param showValue Whether to show the current value
 * @param modifier Modifier for the chart container
 */
@Composable
fun GaugeChart(
    value: Float,
    maxValue: Float = 100f,
    title: String? = null,
    subtitle: String? = null,
    unit: String = "",
    gaugeColor: Color = AppColorScheme.primary,
    backgroundColor: Color = AppColorScheme.surfaceVariant,
    showValue: Boolean = true,
    modifier: Modifier = Modifier
) {
    if (maxValue <= 0f) {
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
                text = "Invalid gauge data",
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
                color = backgroundColor,
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
            Spacer(modifier = Modifier.height(Spacing.sm))
        }

        // Simple gauge representation
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Circular gauge representation
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(
                            color = AppColorScheme.formSecondaryText.copy(alpha = 0.1f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Progress indicator
                    val progress = (value / maxValue).coerceIn(0f, 1f)
                    Box(
                        modifier = Modifier
                            .size((120 * progress).dp)
                            .background(
                                color = gaugeColor.copy(alpha = 0.3f),
                                shape = CircleShape
                            )
                    )
                    
                    // Center value
                    if (showValue) {
                        Text(
                            text = "${value.toInt()}${if (unit.isNotEmpty()) unit else ""}",
                            style = Typography.headlineMedium,
                            color = AppColorScheme.formText,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                // Min/Max labels
                Spacer(modifier = Modifier.height(Spacing.sm))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "0",
                        style = Typography.bodySmall,
                        color = AppColorScheme.formSecondaryText
                    )
                    Text(
                        text = "${maxValue.toInt()}",
                        style = Typography.bodySmall,
                        color = AppColorScheme.formSecondaryText
                    )
                }
            }
        }

        // Subtitle
        subtitle?.let {
            Spacer(modifier = Modifier.height(Spacing.sm))
            Text(
                text = it,
                style = Typography.bodySmall,
                color = AppColorScheme.formSecondaryText,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}