package com.joffer.organizeplus.features.duty.list.di

import com.joffer.organizeplus.features.dashboard.domain.usecases.DeleteDutyUseCase
import com.joffer.organizeplus.features.dashboard.domain.usecases.DeleteDutyUseCaseImpl
import com.joffer.organizeplus.features.duty.list.presentation.DutyListViewModel
import com.joffer.organizeplus.features.duty.occurrence.data.repositories.RoomDutyOccurrenceRepository
import com.joffer.organizeplus.features.duty.occurrence.domain.repositories.DutyOccurrenceRepository
import org.koin.dsl.module

val dutyListModule = module {
    single<DeleteDutyUseCase> { DeleteDutyUseCaseImpl(get<com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository>()) }
    single<DutyOccurrenceRepository> { RoomDutyOccurrenceRepository(get()) }
    factory { DutyListViewModel(get(), get<DeleteDutyUseCase>(), get()) }
}
