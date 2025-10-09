package com.joffer.organizeplus.features.settings.data.repositories

import com.joffer.organizeplus.core.storage.FakeSecureKeyValueStorage
import com.joffer.organizeplus.features.settings.domain.StorageMode
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SettingsRepositoryImplTest {

    private lateinit var secureStorage: FakeSecureKeyValueStorage
    private lateinit var repository: SettingsRepositoryImpl

    @BeforeTest
    fun setup() {
        secureStorage = FakeSecureKeyValueStorage()
        repository = SettingsRepositoryImpl(secureStorage)
    }

    @AfterTest
    fun tearDown() {
        secureStorage.clear()
    }

    @Test
    fun `getStorageMode should return LOCAL by default`() = runTest {
        // When
        val mode = repository.getStorageMode()

        // Then
        assertEquals(StorageMode.LOCAL, mode)
    }

    @Test
    fun `setStorageMode should update storage mode`() = runTest {
        // When
        repository.setStorageMode(StorageMode.REMOTE)

        // Then
        assertEquals(StorageMode.REMOTE, repository.getStorageMode())
    }

    @Test
    fun `setStorageMode should persist to secure storage`() = runTest {
        // When
        repository.setStorageMode(StorageMode.REMOTE)

        // Then
        assertEquals("REMOTE", secureStorage.getString("settings_storage_mode"))
    }

    @Test
    fun `setStorageMode to LOCAL should persist correctly`() = runTest {
        // Given
        repository.setStorageMode(StorageMode.REMOTE)

        // When
        repository.setStorageMode(StorageMode.LOCAL)

        // Then
        assertEquals(StorageMode.LOCAL, repository.getStorageMode())
        assertEquals("LOCAL", secureStorage.getString("settings_storage_mode"))
    }

    @Test
    fun `repository should load persisted storage mode on initialization`() = runTest {
        // Given
        secureStorage.putString("settings_storage_mode", "REMOTE")

        // When
        val newRepository = SettingsRepositoryImpl(secureStorage)

        // Then
        assertEquals(StorageMode.REMOTE, newRepository.getStorageMode())
    }

    @Test
    fun `repository should use default LOCAL when no value is stored`() = runTest {
        // When
        val newRepository = SettingsRepositoryImpl(secureStorage)

        // Then
        assertEquals(StorageMode.LOCAL, newRepository.getStorageMode())
    }
}

