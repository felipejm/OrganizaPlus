package com.joffer.organizeplus.core.di

import com.joffer.organizeplus.core.storage.AndroidSecureKeyValueStorage
import com.joffer.organizeplus.core.storage.SecureKeyValueStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidSecureStorageModule = module {
    single<SecureKeyValueStorage> {
        AndroidSecureKeyValueStorage(context = androidContext())
    }
}

actual val platformSecureStorageModule = androidSecureStorageModule
