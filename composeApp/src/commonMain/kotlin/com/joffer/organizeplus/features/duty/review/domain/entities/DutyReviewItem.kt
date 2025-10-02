package com.joffer.organizeplus.features.duty.review.domain.entities

import kotlinx.datetime.LocalDate

data class DutyReviewItem(
    val dutyId: Long,
    val dutyTitle: String,
    val categoryName: String,
    val paidAmount: Double,
    val completedDate: LocalDate
)

data class MonthlyDutyReview(
    val monthNumber: Int,
    val year: Int,
    val totalPaid: Double,
    val dutyItems: List<DutyReviewItem>
)

data class DutyReviewData(
    val monthlyReviews: List<MonthlyDutyReview>,
    val grandTotal: Double
)
