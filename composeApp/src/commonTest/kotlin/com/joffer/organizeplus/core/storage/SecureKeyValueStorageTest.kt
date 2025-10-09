package com.joffer.organizeplus.core.storage

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SecureKeyValueStorageTest {

    private lateinit var storage: FakeSecureKeyValueStorage

    @BeforeTest
    fun setup() {
        storage = FakeSecureKeyValueStorage()
    }

    @AfterTest
    fun tearDown() {
        storage.clear()
    }

    @Test
    fun `putString and getString should store and retrieve string value`() {
        // When
        storage.putString("key1", "value1")

        // Then
        assertEquals("value1", storage.getString("key1"))
    }

    @Test
    fun `getString should return default value when key does not exist`() {
        // When
        val result = storage.getString("nonexistent", "default")

        // Then
        assertEquals("default", result)
    }

    @Test
    fun `putInt and getInt should store and retrieve int value`() {
        // When
        storage.putInt("age", 25)

        // Then
        assertEquals(25, storage.getInt("age"))
    }

    @Test
    fun `getInt should return default value when key does not exist`() {
        // When
        val result = storage.getInt("nonexistent", 0)

        // Then
        assertEquals(0, result)
    }

    @Test
    fun `putBoolean and getBoolean should store and retrieve boolean value`() {
        // When
        storage.putBoolean("isActive", true)

        // Then
        assertTrue(storage.getBoolean("isActive"))
    }

    @Test
    fun `contains should return true when key exists`() {
        // Given
        storage.putString("key1", "value1")

        // When
        val result = storage.contains("key1")

        // Then
        assertTrue(result)
    }

    @Test
    fun `contains should return false when key does not exist`() {
        // When
        val result = storage.contains("nonexistent")

        // Then
        assertFalse(result)
    }

    @Test
    fun `remove should delete the key`() {
        // Given
        storage.putString("key1", "value1")
        assertTrue(storage.contains("key1"))

        // When
        storage.remove("key1")

        // Then
        assertFalse(storage.contains("key1"))
        assertNull(storage.getString("key1"))
    }

    @Test
    fun `clear should remove all keys`() {
        // Given
        storage.putString("key1", "value1")
        storage.putInt("key2", 42)
        storage.putBoolean("key3", true)

        // When
        storage.clear()

        // Then
        assertFalse(storage.contains("key1"))
        assertFalse(storage.contains("key2"))
        assertFalse(storage.contains("key3"))
    }

    @Test
    fun `putString should overwrite existing value`() {
        // Given
        storage.putString("key1", "value1")

        // When
        storage.putString("key1", "value2")

        // Then
        assertEquals("value2", storage.getString("key1"))
    }

    @Test
    fun `putLong and getLong should store and retrieve long value`() {
        // When
        storage.putLong("timestamp", 1234567890L)

        // Then
        assertEquals(1234567890L, storage.getLong("timestamp"))
    }

    @Test
    fun `putFloat and getFloat should store and retrieve float value`() {
        // When
        storage.putFloat("rate", 3.14f)

        // Then
        assertEquals(3.14f, storage.getFloat("rate"))
    }
}

