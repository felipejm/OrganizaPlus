package com.joffer.organizeplus.features.duty.review.data.repositories

import com.joffer.organizeplus.database.dao.DutyOccurrenceDao
import com.joffer.organizeplus.features.duty.review.domain.entities.DutyReviewData
import com.joffer.organizeplus.features.duty.review.domain.entities.DutyReviewItem
import com.joffer.organizeplus.features.duty.review.domain.entities.MonthlyDutyReview
import com.joffer.organizeplus.features.duty.review.domain.repositories.DutyReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class RoomDutyReviewRepository(
    private val dutyOccurrenceDao: DutyOccurrenceDao
) : DutyReviewRepository {

    override suspend fun getDutyReviewData(categoryFilter: String?): Flow<Result<DutyReviewData>> {
        return flow {
            val occurrences = dutyOccurrenceDao.getAllDutyOccurrencesWithDutyInfo()
            val reviewItems = processOccurrences(occurrences, categoryFilter)
            val monthlyReviews = groupItemsByMonth(reviewItems)
            val grandTotal = calculateGrandTotal(monthlyReviews)

            emit(
                Result.success(
                    DutyReviewData(
                        monthlyReviews = monthlyReviews,
                        grandTotal = grandTotal
                    )
                )
            )
        }.catch { e ->
            emit(Result.failure(e as Exception))
        }
    }

    private fun processOccurrences(
        occurrences: List<com.joffer.organizeplus.database.entities.DutyOccurrenceWithDutyInfo>,
        categoryFilter: String?
    ): List<DutyReviewItem> {
        return occurrences
            .filter { occurrence ->
                categoryFilter == null || occurrence.categoryName == categoryFilter
            }
            .map { occurrence ->
                val completedDate = convertToLocalDate(occurrence.completedDateMillis)
                createDutyReviewItem(occurrence, completedDate)
            }
    }

    private fun convertToLocalDate(timestampMillis: Long): kotlinx.datetime.LocalDate {
        return Instant.fromEpochMilliseconds(timestampMillis)
            .toLocalDateTime(TimeZone.currentSystemDefault()).date
    }

    private fun createDutyReviewItem(
        occurrence: com.joffer.organizeplus.database.entities.DutyOccurrenceWithDutyInfo,
        completedDate: kotlinx.datetime.LocalDate
    ): DutyReviewItem {
        return DutyReviewItem(
            dutyId = occurrence.dutyId,
            dutyTitle = occurrence.dutyTitle,
            categoryName = occurrence.categoryName,
            paidAmount = occurrence.paidAmount,
            completedDate = completedDate
        )
    }

    private fun groupItemsByMonth(reviewItems: List<DutyReviewItem>): List<MonthlyDutyReview> {
        val monthlyGroups = reviewItems.groupBy { item ->
            createMonthKey(item.completedDate)
        }

        return monthlyGroups.map { (_, items) ->
            createMonthlyReview(items)
        }.sortedWith(compareByDescending<MonthlyDutyReview> { it.year }.thenByDescending { it.monthNumber })
    }

    private fun createMonthKey(date: kotlinx.datetime.LocalDate): String {
        return "${date.year}-${date.monthNumber.toString().padStart(2, '0')}"
    }

    private fun createMonthlyReview(items: List<DutyReviewItem>): MonthlyDutyReview {
        val firstDate = items.first().completedDate
        return MonthlyDutyReview(
            monthNumber = firstDate.monthNumber,
            year = firstDate.year,
            totalPaid = items.sumOf { it.paidAmount },
            dutyItems = items.sortedByDescending { it.completedDate }
        )
    }

    private fun calculateGrandTotal(monthlyReviews: List<MonthlyDutyReview>): Double {
        return monthlyReviews.sumOf { it.totalPaid }
    }
}
