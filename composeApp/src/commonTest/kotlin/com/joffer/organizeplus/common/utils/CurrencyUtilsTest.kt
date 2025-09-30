package com.joffer.organizeplus.common.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class CurrencyUtilsTest {

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
    fun `toCurrencyFormat should format with custom symbol`() {
        // Given
        val value = 123.45
        val symbol = "$"

        // When
        val result = value.toCurrencyFormat(symbol = symbol)

        // Then
        assertEquals("$ 123.45", result)
    }

    @Test
    fun `toCurrencyFormat should format with custom decimal places`() {
        // Given
        val value = 123.456789
        val decimalPlaces = 3

        // When
        val result = value.toCurrencyFormat(decimalPlaces = decimalPlaces)

        // Then
        assertEquals("R$ 123.457", result)
    }

    @Test
    fun `toCurrencyFormat should format with custom symbol and decimal places`() {
        // Given
        val value = 123.456789
        val symbol = "€"
        val decimalPlaces = 4

        // When
        val result = value.toCurrencyFormat(symbol = symbol, decimalPlaces = decimalPlaces)

        // Then
        assertEquals("€ 123.4568", result)
    }

    @Test
    fun `toCurrencyFormat should handle very small numbers`() {
        // Given
        val value = 0.01

        // When
        val result = value.toCurrencyFormat()

        // Then
        assertEquals("R$ 0.01", result)
    }

    @Test
    fun `toCurrencyFormat should handle very large numbers`() {
        // Given
        val value = 999999.99

        // When
        val result = value.toCurrencyFormat()

        // Then
        assertEquals("R$ 999999.99", result)
    }

    @Test
    fun `toCurrencyFormat should handle negative zero`() {
        // Given
        val value = -0.0

        // When
        val result = value.toCurrencyFormat()

        // Then
        assertEquals("R$ 0.00", result)
    }

    // Tests for nullable Double extension
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
    fun `nullable toCurrencyFormat should format non-null value with default parameters`() {
        // Given
        val value: Double? = 123.45

        // When
        val result = value.toCurrencyFormat()

        // Then
        assertEquals("R$ 123.45", result)
    }

    @Test
    fun `nullable toCurrencyFormat should format non-null value with custom parameters`() {
        // Given
        val value: Double? = 123.456
        val symbol = "¥"
        val decimalPlaces = 1

        // When
        val result = value.toCurrencyFormat(symbol = symbol, decimalPlaces = decimalPlaces)

        // Then
        assertEquals("¥ 123.5", result)
    }

    @Test
    fun `nullable toCurrencyFormat should handle negative non-null value`() {
        // Given
        val value: Double? = -50.25

        // When
        val result = value.toCurrencyFormat()

        // Then
        assertEquals("-R$ 50.25", result)
    }

    @Test
    fun `nullable toCurrencyFormat should handle zero`() {
        // Given
        val value: Double? = 0.0

        // When
        val result = value.toCurrencyFormat()

        // Then
        assertEquals("R$ 0.00", result)
    }
}
