package com.joffer.organizeplus.features.duty.detail.di

import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.duty.detail.presentation.DutyDetailsListViewModel
import com.joffer.organizeplus.features.duty.occurrence.data.repositories.RoomDutyOccurrenceRepository
import com.joffer.organizeplus.features.duty.occurrence.domain.repositories.DutyOccurrenceRepository
import org.koin.dsl.module

val dutyDetailsModule = module {
    single<DutyOccurrenceRepository> { RoomDutyOccurrenceRepository(dutyOccurrenceDao = get()) }
    factory { (dutyId: Long) ->
        DutyDetailsListViewModel(
            repository = get(),
            dutyRepository = get<DutyRepository>(),
            dutyId = dutyId
        )
    }
}
