package com.joffer.organizeplus.features.duty.di

import com.joffer.organizeplus.features.duty.create.di.createDutyModule
import com.joffer.organizeplus.features.duty.detail.di.dutyDetailsModule
import com.joffer.organizeplus.features.duty.list.di.dutyListModule
import com.joffer.organizeplus.features.duty.occurrence.di.dutyOccurrenceModule
import org.koin.dsl.module

val dutyModule = module {
    includes(createDutyModule)
    includes(dutyListModule)
    includes(dutyDetailsModule)
    includes(dutyOccurrenceModule)
}
