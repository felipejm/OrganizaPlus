package com.joffer.organizeplus.features.duty.review.di

import com.joffer.organizeplus.features.duty.review.data.repositories.RoomDutyReviewRepository
import com.joffer.organizeplus.features.duty.review.domain.repositories.DutyReviewRepository
import com.joffer.organizeplus.features.duty.review.presentation.DutyReviewViewModel
import org.koin.dsl.module

val dutyReviewModule = module {
    single<DutyReviewRepository> {
        RoomDutyReviewRepository(
            dutyOccurrenceDao = get()
        )
    }

    factory { (categoryFilter: String?) ->
        DutyReviewViewModel(
            repository = get(),
            categoryFilter = categoryFilter
        )
    }
}
