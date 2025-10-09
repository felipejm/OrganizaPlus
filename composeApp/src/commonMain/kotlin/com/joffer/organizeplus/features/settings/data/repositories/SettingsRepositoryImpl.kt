package com.joffer.organizeplus.features.settings.data.repositories

import com.joffer.organizeplus.core.storage.SecureKeyValueStorage
import com.joffer.organizeplus.features.settings.domain.StorageMode
import com.joffer.organizeplus.features.settings.domain.repositories.SettingsRepository

class SettingsRepositoryImpl(
    private val secureStorage: SecureKeyValueStorage
) : SettingsRepository {

    override suspend fun getStorageMode(): StorageMode {
        val storedValue = secureStorage.getString(KEY_STORAGE_MODE, null)
        return when (storedValue) {
            STORAGE_MODE_REMOTE -> StorageMode.REMOTE
            STORAGE_MODE_LOCAL -> StorageMode.LOCAL
            else -> StorageMode.LOCAL
        }
    }

    override suspend fun setStorageMode(mode: StorageMode) {
        val value = when (mode) {
            StorageMode.REMOTE -> STORAGE_MODE_REMOTE
            StorageMode.LOCAL -> STORAGE_MODE_LOCAL
        }
        secureStorage.putString(KEY_STORAGE_MODE, value)
    }

    companion object {
        private const val KEY_STORAGE_MODE = "settings_storage_mode"
        private const val STORAGE_MODE_REMOTE = "REMOTE"
        private const val STORAGE_MODE_LOCAL = "LOCAL"
    }
}
