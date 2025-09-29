package com.joffer.organizeplus.di

import android.content.Context
import com.joffer.organizeplus.database.DatabaseDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidDatabaseModule = module {
    single<DatabaseDriverFactory> { DatabaseDriverFactory(androidContext()) }
}

actual val platformDatabaseModule = androidDatabaseModule
