package com.joffer.organizeplus.features.duty.review.presentation

import com.joffer.organizeplus.features.duty.review.domain.entities.DutyReviewData

data class DutyReviewUiState(
    val isLoading: Boolean = false,
    val dutyReviewData: DutyReviewData? = null,
    val error: String? = null
)
