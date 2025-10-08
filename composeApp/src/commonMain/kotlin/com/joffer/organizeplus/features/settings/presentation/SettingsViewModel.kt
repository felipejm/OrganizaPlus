package com.joffer.organizeplus.features.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.features.settings.domain.StorageMode
import com.joffer.organizeplus.features.settings.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadStorageMode()
    }

    private fun loadStorageMode() {
        viewModelScope.launch {
            val storageMode = settingsRepository.getStorageMode()
            _uiState.value = _uiState.value.copy(storageMode = storageMode)
        }
    }

    fun updateStorageMode(newMode: StorageMode) {
        viewModelScope.launch {
            settingsRepository.setStorageMode(newMode)
            _uiState.value = _uiState.value.copy(storageMode = newMode)
        }
    }
}

data class SettingsUiState(
    val storageMode: StorageMode = StorageMode.LOCAL
)
