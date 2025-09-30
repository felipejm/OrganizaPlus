package com.joffer.organizeplus.features.duty.detail.domain.entities

import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType

data class MonthlyChartData(
    val month: Int, // Month number (1-12)
    val year: Int,
    val value: Float, // Either count of occurrences or sum of paid amounts
    val dutyType: DutyType
)

data class ChartData(
    val monthlyData: List<MonthlyChartData>,
    val dutyType: DutyType
)
