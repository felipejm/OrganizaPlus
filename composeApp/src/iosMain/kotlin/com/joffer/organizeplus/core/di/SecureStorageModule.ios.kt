package com.joffer.organizeplus.core.di

import com.joffer.organizeplus.core.storage.IosSecureKeyValueStorage
import com.joffer.organizeplus.core.storage.SecureKeyValueStorage
import org.koin.dsl.module

val iosSecureStorageModule = module {
    single<SecureKeyValueStorage> {
        IosSecureKeyValueStorage()
    }
}

actual val platformSecureStorageModule = iosSecureStorageModule
