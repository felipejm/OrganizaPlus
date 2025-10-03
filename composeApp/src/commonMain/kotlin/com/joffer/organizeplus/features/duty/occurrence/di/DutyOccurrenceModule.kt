package com.joffer.organizeplus.features.duty.occurrence.di

import com.joffer.organizeplus.features.duty.occurrence.presentation.AddDutyOccurrenceViewModel
import org.koin.dsl.module

val dutyOccurrenceModule = module {
    factory { (dutyId: Long) ->
        AddDutyOccurrenceViewModel(
            dutyOccurrenceRepository = get(),
            dutyRepository = get(),
            dutyId = dutyId
        )
    }
}
