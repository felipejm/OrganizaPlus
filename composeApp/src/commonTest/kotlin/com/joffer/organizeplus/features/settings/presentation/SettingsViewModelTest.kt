package com.joffer.organizeplus.features.settings.presentation

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class SettingsViewModelTest {

    @Test
    fun `init should initialize with default ui state`() {
        // Given & When
        val viewModel = SettingsViewModel()

        // Then
        val uiState = viewModel.uiState.value
        assertEquals(SettingsUiState(), uiState)
        assertFalse(uiState.dummy)
    }

    @Test
    fun `uiState should be accessible`() {
        // Given
        val viewModel = SettingsViewModel()

        // When
        val uiState = viewModel.uiState.value

        // Then
        assertEquals(SettingsUiState(), uiState)
    }
}
