package com.joffer.organizeplus.features.settings.data.repositories

import com.joffer.organizeplus.features.settings.domain.StorageMode
import com.joffer.organizeplus.features.settings.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsRepositoryImpl : SettingsRepository {

    private val _storageMode = MutableStateFlow(StorageMode.LOCAL)
    val storageModeFlow: StateFlow<StorageMode> = _storageMode.asStateFlow()

    override suspend fun getStorageMode(): StorageMode {
        return _storageMode.value
    }

    override suspend fun setStorageMode(mode: StorageMode) {
        _storageMode.value = mode
    }
}
