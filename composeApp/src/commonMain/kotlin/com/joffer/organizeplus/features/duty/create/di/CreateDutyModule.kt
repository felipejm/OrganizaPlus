package com.joffer.organizeplus.features.duty.create.di

import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.duty.create.domain.usecases.SaveCreateDutyUseCase
import com.joffer.organizeplus.features.duty.create.domain.usecases.implementations.SaveCreateDutyUseCaseImpl
import com.joffer.organizeplus.features.duty.create.presentation.CreateDutyViewModel
import org.koin.dsl.module

val createDutyModule = module {
    single<SaveCreateDutyUseCase> {
        SaveCreateDutyUseCaseImpl(get<DutyRepository>())
    }

    factory { (dutyId: Long?, categoryName: String) ->
        CreateDutyViewModel(
            saveCreateDutyUseCase = get(),
            dutyRepository = get(),
            dutyId = dutyId,
            categoryName = categoryName
        )
    }
}
