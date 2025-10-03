package com.joffer.organizeplus.features.duty.detail.presentation

import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.duty.detail.domain.entities.ChartData
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrence

data class DutyDetailsListUiState(
    val isLoading: Boolean = false,
    val records: List<DutyOccurrence> = emptyList(),
    val duty: Duty? = null,
    val chartData: ChartData? = null,
    val error: String? = null
)
