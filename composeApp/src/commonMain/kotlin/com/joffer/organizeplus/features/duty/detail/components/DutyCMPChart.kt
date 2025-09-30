package com.joffer.organizeplus.features.duty.detail.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.components.BarChart
import com.joffer.organizeplus.designsystem.components.ChartDataPoint
import com.joffer.organizeplus.features.duty.detail.domain.entities.ChartData
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.chart_occurrences_by_month
import organizeplus.composeapp.generated.resources.chart_amount_paid_by_month
import organizeplus.composeapp.generated.resources.chart_count_legend
import organizeplus.composeapp.generated.resources.chart_amount_legend
import organizeplus.composeapp.generated.resources.*

@Composable
fun DutyCMPChart(
    chartData: ChartData,
    modifier: Modifier = Modifier
) {
    val chartPoints = chartData.monthlyData.map { monthData ->
        ChartDataPoint(
            label = "${getShortMonthName(monthData.month)} ${monthData.year}",
            value = monthData.value
        )
    }

    val title = when (chartData.dutyType) {
        DutyType.ACTIONABLE -> 
            stringResource(Res.string.chart_occurrences_by_month)
        DutyType.PAYABLE -> 
            stringResource(Res.string.chart_amount_paid_by_month)
    }

    val legend = when (chartData.dutyType) {
        DutyType.ACTIONABLE -> 
            stringResource(Res.string.chart_count_legend)
        DutyType.PAYABLE -> 
            stringResource(Res.string.chart_amount_legend)
    }

    BarChart(
        data = chartPoints,
        title = title,
        legend = legend,
        modifier = modifier
    )
}

@Composable
private fun getShortMonthName(month: Int): String {
    return when (month) {
        1 -> stringResource(Res.string.month_jan)
        2 -> stringResource(Res.string.month_feb)
        3 -> stringResource(Res.string.month_mar)
        4 -> stringResource(Res.string.month_apr)
        5 -> stringResource(Res.string.month_may_short)
        6 -> stringResource(Res.string.month_jun)
        7 -> stringResource(Res.string.month_jul)
        8 -> stringResource(Res.string.month_aug)
        9 -> stringResource(Res.string.month_sep)
        10 -> stringResource(Res.string.month_oct)
        11 -> stringResource(Res.string.month_nov)
        12 -> stringResource(Res.string.month_dec)
        else -> month.toString() // fallback to month number
    }
}
