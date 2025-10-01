package com.joffer.organizeplus.di

import androidx.room.RoomDatabase
import com.joffer.organizeplus.database.OrganizePlusDatabase
import com.joffer.organizeplus.database.getDatabaseBuilder
import org.koin.dsl.module

val iosDatabaseModule = module {
    single<RoomDatabase.Builder<OrganizePlusDatabase>> {
        getDatabaseBuilder()
    }
}

actual val platformDatabaseModule = iosDatabaseModule
