package com.joffer.organizeplus.features.duty.review.data.repositories

import com.joffer.organizeplus.database.dao.DutyOccurrenceDao
import com.joffer.organizeplus.features.duty.review.domain.entities.DutyReviewData
import com.joffer.organizeplus.features.duty.review.domain.entities.DutyReviewItem
import com.joffer.organizeplus.features.duty.review.domain.entities.MonthlyDutyReview
import com.joffer.organizeplus.features.duty.review.domain.repositories.DutyReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class RoomDutyReviewRepository(
    private val dutyOccurrenceDao: DutyOccurrenceDao
) : DutyReviewRepository {

    override suspend fun getDutyReviewData(categoryFilter: String?): Flow<Result<DutyReviewData>> {
        return flow {
            try {
                val occurrences = dutyOccurrenceDao.getAllDutyOccurrencesWithDutyInfo()

                val reviewItems = occurrences
                    .filter { occurrence ->
                        // Filter by category if provided
                        categoryFilter == null || occurrence.categoryName == categoryFilter
                    }
                    .map { occurrence ->
                        val completedDate = Instant.fromEpochMilliseconds(occurrence.completedDateMillis)
                            .toLocalDateTime(TimeZone.currentSystemDefault()).date

                        DutyReviewItem(
                            dutyId = occurrence.dutyId,
                            dutyTitle = occurrence.dutyTitle,
                            categoryName = occurrence.categoryName,
                            paidAmount = occurrence.paidAmount,
                            completedDate = completedDate
                        )
                    }

                // Group by year-month directly from the LocalDate
                val monthlyGroups = reviewItems.groupBy { item ->
                    "${item.completedDate.year}-${item.completedDate.monthNumber.toString().padStart(2, '0')}"
                }

                val monthlyReviews = monthlyGroups.map { (monthKey, items) ->
                    // Use the first item's date to get month and year info
                    val firstDate = items.first().completedDate

                    MonthlyDutyReview(
                        monthNumber = firstDate.monthNumber,
                        year = firstDate.year,
                        totalPaid = items.sumOf { it.paidAmount },
                        dutyItems = items.sortedByDescending { it.completedDate }
                    )
                }.sortedWith(compareByDescending<MonthlyDutyReview> { it.year }.thenByDescending { it.monthNumber })

                val grandTotal = monthlyReviews.sumOf { it.totalPaid }

                emit(
                    Result.success(
                        DutyReviewData(
                            monthlyReviews = monthlyReviews,
                            grandTotal = grandTotal
                        )
                    )
                )
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }
}
