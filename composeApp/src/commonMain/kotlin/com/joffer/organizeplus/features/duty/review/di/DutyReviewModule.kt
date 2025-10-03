package com.joffer.organizeplus.features.duty.review.di

import com.joffer.organizeplus.features.duty.review.presentation.DutyReviewViewModel
import org.koin.dsl.module

val dutyReviewModule = module {
    factory { (categoryFilter: String?) ->
        DutyReviewViewModel(
            repository = get(),
            categoryFilter = categoryFilter
        )
    }
}
