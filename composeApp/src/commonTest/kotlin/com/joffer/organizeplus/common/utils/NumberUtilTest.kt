package com.joffer.organizeplus.common.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class NumberUtilTest {

    @Test
    fun `safeToDouble should convert valid string with dot to double`() {
        // Given
        val input = "123.45"

        // When
        val result = input.safeToDouble()

        // Then
        assertEquals(123.45, result)
    }

    @Test
    fun `safeToDouble should convert valid string with comma to double`() {
        // Given
        val input = "123,45"

        // When
        val result = input.safeToDouble()

        // Then
        assertEquals(123.45, result)
    }

    @Test
    fun `safeToDouble should convert integer string to double`() {
        // Given
        val input = "100"

        // When
        val result = input.safeToDouble()

        // Then
        assertEquals(100.0, result)
    }

    @Test
    fun `safeToDouble should convert zero string to zero`() {
        // Given
        val input = "0"

        // When
        val result = input.safeToDouble()

        // Then
        assertEquals(0.0, result)
    }

    @Test
    fun `safeToDouble should convert negative number string to negative double`() {
        // Given
        val input = "-123.45"

        // When
        val result = input.safeToDouble()

        // Then
        assertEquals(-123.45, result)
    }

    @Test
    fun `safeToDouble should convert negative number with comma to negative double`() {
        // Given
        val input = "-123,45"

        // When
        val result = input.safeToDouble()

        // Then
        assertEquals(-123.45, result)
    }

    @Test
    fun `safeToDouble should return zero for empty string`() {
        // Given
        val input = ""

        // When
        val result = input.safeToDouble()

        // Then
        assertEquals(0.0, result)
    }

    @Test
    fun `safeToDouble should return zero for invalid string`() {
        // Given
        val input = "invalid"

        // When
        val result = input.safeToDouble()

        // Then
        assertEquals(0.0, result)
    }

    @Test
    fun `safeToDouble should return zero for string with only spaces`() {
        // Given
        val input = "   "

        // When
        val result = input.safeToDouble()

        // Then
        assertEquals(0.0, result)
    }

    @Test
    fun `safeToDouble should handle string with multiple commas`() {
        // Given
        val input = "1,2,3"

        // When
        val result = input.safeToDouble()

        // Then
        assertEquals(0.0, result) // Invalid format, should return 0.0
    }

    @Test
    fun `safeToDouble should handle string with multiple dots`() {
        // Given
        val input = "1.2.3"

        // When
        val result = input.safeToDouble()

        // Then
        assertEquals(0.0, result) // Invalid format, should return 0.0
    }

    @Test
    fun `safeToDouble should handle mixed separators`() {
        // Given
        val input = "123.45,67"

        // When
        val result = input.safeToDouble()

        // Then
        assertEquals(0.0, result) // Invalid format, should return 0.0
    }

    @Test
    fun `safeToDouble should handle very large numbers`() {
        // Given
        val input = "999999999.99"

        // When
        val result = input.safeToDouble()

        // Then
        assertEquals(999999999.99, result)
    }

    @Test
    fun `safeToDouble should handle very small numbers`() {
        // Given
        val input = "0.000001"

        // When
        val result = input.safeToDouble()

        // Then
        assertEquals(0.000001, result)
    }

    @Test
    fun `safeToDouble should handle scientific notation`() {
        // Given
        val input = "1e5"

        // When
        val result = input.safeToDouble()

        // Then
        assertEquals(100000.0, result)
    }

    @Test
    fun `safeToDouble should handle currency format with comma`() {
        // Given
        val input = "1,234.56"

        // When
        val result = input.safeToDouble()

        // Then
        assertEquals(0.0, result) // Invalid format (1.234.56), should return 0.0
    }

    @Test
    fun `safeToDouble should handle European format with comma as decimal separator`() {
        // Given
        val input = "1234,56"

        // When
        val result = input.safeToDouble()

        // Then
        assertEquals(1234.56, result) // Comma replaced with dot, then parsed
    }

    @Test
    fun `safeToDouble should return non-null value`() {
        // Given
        val input = "123.45"

        // When
        val result = input.safeToDouble()

        // Then
        assertNotNull(result)
    }
}
