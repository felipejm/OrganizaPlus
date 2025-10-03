package com.joffer.organizeplus.features.duty.list.presentation

import com.joffer.organizeplus.features.dashboard.domain.entities.DutyWithLastOccurrence

data class DutyListUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val duties: List<DutyWithLastOccurrence> = emptyList(),
    val showDeleteConfirmation: Boolean = false,
    val dutyToDelete: Long? = null,
)
