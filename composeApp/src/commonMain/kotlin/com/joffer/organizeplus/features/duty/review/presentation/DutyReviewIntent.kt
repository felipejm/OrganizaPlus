package com.joffer.organizeplus.features.duty.review.presentation

sealed class DutyReviewIntent {
    object LoadData : DutyReviewIntent()
    object Retry : DutyReviewIntent()
    object ClearError : DutyReviewIntent()
}
