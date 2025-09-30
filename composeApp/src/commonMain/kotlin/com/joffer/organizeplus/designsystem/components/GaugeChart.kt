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
import network.chaintech.cmpcharts.ui.gaugechart.GaugeChart
import network.chaintech.cmpcharts.ui.gaugechart.config.GaugeChartDefaults
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.chart_no_data_available

// Chart dimensions - using design system spacing
private val GAUGE_CHART_HEIGHT = Spacing.Chart.height
private val GAUGE_EMPTY_STATE_HEIGHT = Spacing.Chart.emptyStateHeight


@Composable
fun AppGaugeChart(
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
                .height(GAUGE_EMPTY_STATE_HEIGHT)
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
                color = backgroundColor,
                shape = RoundedCornerShape(Spacing.Radius.lg)
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

        // CMPCharts GaugeChart
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentAlignment = Alignment.Center
        ) {
            val percentValue = ((value / maxValue * 100).coerceIn(0f, 100f)).toInt()
            
            GaugeChart(
                percentValue = percentValue,
                gaugeChartConfig = GaugeChartDefaults.gaugeConfigDefaults().copy(
                    primaryColor = gaugeColor,
                    placeHolderColor = AppColorScheme.formSecondaryText.copy(alpha = 0.2f),
                    indicatorColor = gaugeColor
                ),
                needleConfig = GaugeChartDefaults.needleConfigDefaults().copy(
                    color = gaugeColor
                )
            )
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