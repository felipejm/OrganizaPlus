package com.joffer.organizeplus.features.duty.detail.di

import com.joffer.organizeplus.features.duty.detail.data.repositories.RoomDutyDetailsRepository
import com.joffer.organizeplus.features.duty.detail.domain.repositories.DutyDetailsRepository
import com.joffer.organizeplus.features.duty.detail.domain.usecases.SaveDutyDetailsUseCase
import com.joffer.organizeplus.features.duty.detail.domain.usecases.implementations.SaveDutyDetailsUseCaseImpl
import com.joffer.organizeplus.features.duty.detail.presentation.AddDutyDetailsViewModel
import com.joffer.organizeplus.features.duty.detail.presentation.DutyDetailsListViewModel
import org.koin.dsl.module

val dutyDetailsModule = module {
    single<DutyDetailsRepository> { RoomDutyDetailsRepository(get()) }
    single<SaveDutyDetailsUseCase> { SaveDutyDetailsUseCaseImpl(get()) }
    factory { (dutyId: String) -> DutyDetailsListViewModel(get(), dutyId) }
    factory { (dutyId: String) -> AddDutyDetailsViewModel(get(), dutyId) }
}
