package com.joffer.organizeplus.features.duty.review.domain.repositories

import com.joffer.organizeplus.features.duty.review.domain.entities.DutyReviewData
import kotlinx.coroutines.flow.Flow

interface DutyReviewRepository {
    suspend fun getDutyReviewData(categoryFilter: String?): Flow<Result<DutyReviewData>>
}
