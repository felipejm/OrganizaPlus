package com.joffer.organizeplus.features.duty.review.data.repositories

import com.joffer.organizeplus.database.dao.DutyOccurrenceDao
import com.joffer.organizeplus.database.entities.DutyOccurrenceWithDutyInfo
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

    override suspend fun getDutyReviewData(): Flow<Result<DutyReviewData>> {
        return flow {
            try {
                val occurrences = dutyOccurrenceDao.getAllDutyOccurrencesWithDutyInfo()
                
                val reviewItems = occurrences.map { occurrence ->
                    DutyReviewItem(
                        dutyId = occurrence.dutyId,
                        dutyTitle = occurrence.dutyTitle,
                        categoryName = occurrence.categoryName,
                        paidAmount = occurrence.paidAmount,
                        completedDate = Instant.fromEpochMilliseconds(occurrence.completedDateMillis)
                            .toLocalDateTime(TimeZone.currentSystemDefault()).date
                    )
                }

                val monthlyGroups = reviewItems.groupBy { item ->
                    val dateTime = Instant.fromEpochMilliseconds(
                        item.completedDate.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
                    ).toLocalDateTime(TimeZone.currentSystemDefault())
                    "${dateTime.year}-${dateTime.monthNumber.toString().padStart(2, '0')}"
                }

                val monthlyReviews = monthlyGroups.map { (monthKey, items) ->
                    val dateTime = Instant.fromEpochMilliseconds(
                        items.first().completedDate.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
                    ).toLocalDateTime(TimeZone.currentSystemDefault())
                    
                    MonthlyDutyReview(
                        month = getMonthName(dateTime.monthNumber),
                        year = dateTime.year,
                        totalPaid = items.sumOf { it.paidAmount },
                        dutyItems = items.sortedByDescending { it.completedDate }
                    )
                }.sortedWith(compareByDescending<MonthlyDutyReview> { it.year }.thenByDescending { getMonthNumber(it.month) })

                val grandTotal = monthlyReviews.sumOf { it.totalPaid }

                emit(Result.success(DutyReviewData(
                    monthlyReviews = monthlyReviews,
                    grandTotal = grandTotal
                )))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }

    private fun getMonthName(monthNumber: Int): String {
        return when (monthNumber) {
            1 -> "January"
            2 -> "February"
            3 -> "March"
            4 -> "April"
            5 -> "May"
            6 -> "June"
            7 -> "July"
            8 -> "August"
            9 -> "September"
            10 -> "October"
            11 -> "November"
            12 -> "December"
            else -> "Unknown"
        }
    }

    private fun getMonthNumber(monthName: String): Int {
        return when (monthName) {
            "January" -> 1
            "February" -> 2
            "March" -> 3
            "April" -> 4
            "May" -> 5
            "June" -> 6
            "July" -> 7
            "August" -> 8
            "September" -> 9
            "October" -> 10
            "November" -> 11
            "December" -> 12
            else -> 0
        }
    }
}
