package com.joffer.organizeplus.features.duty.detail.di

import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.duty.detail.presentation.DutyDetailsListViewModel
import org.koin.dsl.module

val dutyDetailsModule = module {
    factory { (dutyId: Long) ->
        DutyDetailsListViewModel(
            repository = get(),
            dutyRepository = get<DutyRepository>(),
            dutyId = dutyId
        )
    }
}
