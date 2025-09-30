package com.joffer.organizeplus.features.duty.detail.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.common.utils.DateUtils
import com.joffer.organizeplus.designsystem.components.AppBarChart
import com.joffer.organizeplus.designsystem.components.ChartDataPoint
import com.joffer.organizeplus.designsystem.components.ChartDataSeries
import com.joffer.organizeplus.features.duty.detail.domain.entities.ChartData
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.chart_occurrences_by_month
import organizeplus.composeapp.generated.resources.chart_amount_paid_by_month
import organizeplus.composeapp.generated.resources.chart_occurrences_series_name
import organizeplus.composeapp.generated.resources.chart_amount_series_name

@Composable
fun DutyBarChart(
    chartData: ChartData,
    modifier: Modifier = Modifier
) {
    val chartPoints = chartData.monthlyData.map { monthData ->
        ChartDataPoint(
            label = "${DateUtils.getShortMonthName(monthData.month)}",
            value = monthData.value
        )
    }

    val title = when (chartData.dutyType) {
        DutyType.ACTIONABLE ->
            stringResource(Res.string.chart_occurrences_by_month)
        DutyType.PAYABLE ->
            stringResource(Res.string.chart_amount_paid_by_month)
    }

    val xAxisLabels = chartPoints.map { it.label }
    val dataSeries = listOf(
        ChartDataSeries(
            name = when (chartData.dutyType) {
                DutyType.ACTIONABLE -> stringResource(Res.string.chart_occurrences_series_name)
                DutyType.PAYABLE -> stringResource(Res.string.chart_amount_series_name)
            },
            values = chartPoints.map { it.value.toDouble() }
        )
    )

    AppBarChart(
        dataSeries = dataSeries,
        xAxisLabels = xAxisLabels,
        title = title,
        legend = null,
        modifier = modifier
    )
}
