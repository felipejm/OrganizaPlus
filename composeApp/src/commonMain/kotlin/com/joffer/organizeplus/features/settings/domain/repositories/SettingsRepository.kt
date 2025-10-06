package com.joffer.organizeplus.features.settings.domain.repositories

import com.joffer.organizeplus.features.settings.domain.StorageMode

interface SettingsRepository {
    suspend fun getStorageMode(): StorageMode
    suspend fun setStorageMode(mode: StorageMode)
}
