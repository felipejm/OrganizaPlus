package com.joffer.organizeplus.common.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class CurrencyUtilsSimpleTest {

    @Test
    fun `toCurrencyFormat should format positive double with default parameters`() {
        // Given
        val value = 123.45

        // When
        val result = value.toCurrencyFormat()

        // Then
        assertEquals("R$ 123.45", result)
    }

    @Test
    fun `toCurrencyFormat should format negative double with default parameters`() {
        // Given
        val value = -123.45

        // When
        val result = value.toCurrencyFormat()

        // Then
        assertEquals("-R$ 123.45", result)
    }

    @Test
    fun `toCurrencyFormat should format zero with default parameters`() {
        // Given
        val value = 0.0

        // When
        val result = value.toCurrencyFormat()

        // Then
        assertEquals("R$ 0.00", result)
    }

    @Test
    fun `nullable toCurrencyFormat should return empty string for null`() {
        // Given
        val value: Double? = null

        // When
        val result = value.toCurrencyFormat()

        // Then
        assertEquals("", result)
    }

    @Test
    fun `nullable toCurrencyFormat should format non-null value`() {
        // Given
        val value: Double? = 99.99

        // When
        val result = value.toCurrencyFormat()

        // Then
        assertEquals("R$ 99.99", result)
    }
}
