package com.joffer.organizeplus.di

import com.joffer.organizeplus.features.dashboard.data.repositories.RoomDutyRepository
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.duty.occurrence.data.repositories.RoomDutyOccurrenceRepository
import com.joffer.organizeplus.features.duty.occurrence.domain.repositories.DutyOccurrenceRepository
import com.joffer.organizeplus.features.duty.review.data.repositories.RoomDutyReviewRepository
import com.joffer.organizeplus.features.duty.review.domain.repositories.DutyReviewRepository
import org.koin.dsl.module

/**
 * Centralized repository module to avoid duplicate registrations
 * across different feature modules.
 */
val repositoryModule = module {
    // Core repositories
    single<DutyRepository> { RoomDutyRepository(get()) }
    single<DutyOccurrenceRepository> { RoomDutyOccurrenceRepository(get()) }
    single<DutyReviewRepository> { RoomDutyReviewRepository(get()) }
}

