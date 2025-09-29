package com.joffer.organizeplus.features.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joffer.organizeplus.features.dashboard.presentation.DashboardViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val dashboardViewModel: DashboardViewModel
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()
    
    init {
        // Initialize with current user name from dashboard
        viewModelScope.launch {
            dashboardViewModel.userName.collect { userName ->
                _uiState.value = _uiState.value.copy(userName = userName)
            }
        }
    }
    
    fun updateUserName(userName: String) {
        _uiState.value = _uiState.value.copy(userName = userName)
    }
    
    fun saveSettings() {
        viewModelScope.launch {
            // Update the dashboard with the new user name
            dashboardViewModel.setUserName(_uiState.value.userName)
            _uiState.value = _uiState.value.copy(isSaved = true)
        }
    }
    
    fun clearSaveStatus() {
        _uiState.value = _uiState.value.copy(isSaved = false)
    }
}

data class SettingsUiState(
    val userName: String = "User",
    val isSaved: Boolean = false
)
