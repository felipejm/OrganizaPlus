package com.joffer.organizeplus.features.settings.data

import com.joffer.organizeplus.features.settings.domain.StorageMode
import com.joffer.organizeplus.features.settings.domain.repositories.SettingsRepository

class FakeSettingsRepository : SettingsRepository {
    
    private var storageMode: StorageMode = StorageMode.LOCAL

    override suspend fun getStorageMode(): StorageMode {
        return storageMode
    }

    override suspend fun setStorageMode(mode: StorageMode) {
        storageMode = mode
    }

    fun reset() {
        storageMode = StorageMode.LOCAL
    }
}

