package com.joffer.organizeplus.di

import com.joffer.organizeplus.database.OrganizePlusDatabase
import com.joffer.organizeplus.database.dao.DutyDao
import com.joffer.organizeplus.database.dao.DutyOccurrenceDao
import org.koin.dsl.module

val databaseModule = module {
    single<OrganizePlusDatabase> { OrganizePlusDatabase.create(get()) }
    single<DutyDao> { get<OrganizePlusDatabase>().dutyDao() }
    single<DutyOccurrenceDao> { get<OrganizePlusDatabase>().dutyOccurrenceDao() }
}
