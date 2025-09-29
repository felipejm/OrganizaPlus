package com.joffer.organizeplus.features.dutyDetails.di

import com.joffer.organizeplus.features.dutyDetails.data.repositories.FakeDutyDetailsRepository
import com.joffer.organizeplus.features.dutyDetails.domain.repositories.DutyDetailsRepository
import com.joffer.organizeplus.features.dutyDetails.domain.usecases.SaveDutyDetailsUseCase
import com.joffer.organizeplus.features.dutyDetails.domain.usecases.implementations.SaveDutyDetailsUseCaseImpl
import com.joffer.organizeplus.features.dutyDetails.presentation.AddDutyDetailsViewModel
import com.joffer.organizeplus.features.dutyDetails.presentation.DutyDetailsListViewModel
import org.koin.dsl.module

val dutyDetailsModule = module {
    single<DutyDetailsRepository> { FakeDutyDetailsRepository() }
    single<SaveDutyDetailsUseCase> { SaveDutyDetailsUseCaseImpl(get()) }
    factory { (dutyId: String) -> DutyDetailsListViewModel(get(), dutyId) }
    factory { (dutyId: String) -> AddDutyDetailsViewModel(get(), dutyId) }
}
