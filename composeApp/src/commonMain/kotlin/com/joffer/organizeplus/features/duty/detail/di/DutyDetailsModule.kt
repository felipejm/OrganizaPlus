package com.joffer.organizeplus.features.duty.detail.di

import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.duty.detail.presentation.DutyDetailsListViewModel
import com.joffer.organizeplus.features.duty.occurrence.data.repositories.RoomDutyOccurrenceRepository
import com.joffer.organizeplus.features.duty.occurrence.domain.repositories.DutyOccurrenceRepository
import org.koin.dsl.module

val dutyDetailsModule = module {
    single<DutyOccurrenceRepository> { RoomDutyOccurrenceRepository(get()) }
    factory { (dutyId: String) -> DutyDetailsListViewModel(get(), get<DutyRepository>(), dutyId) }
}
