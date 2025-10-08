package com.joffer.organizeplus.features.duty.occurrence.di

import com.joffer.organizeplus.features.duty.occurrence.data.repositories.RoomDutyOccurrenceRepository
import com.joffer.organizeplus.features.duty.occurrence.domain.repositories.DutyOccurrenceRepository
import com.joffer.organizeplus.features.duty.occurrence.presentation.AddDutyOccurrenceViewModel
import org.koin.dsl.module

val dutyOccurrenceModule = module {
    // Repository
    single<DutyOccurrenceRepository> {
        RoomDutyOccurrenceRepository(
            dutyOccurrenceDao = get()
        )
    }

    // ViewModels
    factory { (dutyId: Long) ->
        AddDutyOccurrenceViewModel(
            dutyOccurrenceRepository = get(),
            dutyRepository = get(),
            dutyId = dutyId
        )
    }
}
