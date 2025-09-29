package com.joffer.organizeplus.features.duty.list.presentation

import com.joffer.organizeplus.features.dashboard.domain.entities.Duty

data class DutyListUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val duties: List<Duty> = emptyList(),
    val searchQuery: String = ""
)
