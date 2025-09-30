package com.joffer.organizeplus.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DateFormatterTest {

    @Test
    fun getDatePlaceholderShouldReturnCorrectFormat() {
        // When
        val result = DateFormatter.getDatePlaceholder()

        // Then
        assertEquals("MM/DD/YYYY", result)
    }

    @Test
    fun `getDatePlaceholderBR should return correct format`() {
        // When
        val result = DateFormatter.getDatePlaceholderBR()

        // Then
        assertEquals("DD/MM/YYYY", result)
    }

    @Test
    fun `formatDate should return date as string`() {
        // Given
        val date = LocalDate(2024, 1, 15)

        // When
        val result = DateFormatter.formatDate(date)

        // Then
        assertEquals("2024-01-15", result)
    }

    @Test
    fun `parseDate should parse valid date string`() {
        // Given
        val dateString = "2024-01-15"

        // When
        val result = DateFormatter.parseDate(dateString)

        // Then
        assertEquals(LocalDate(2024, 1, 15), result)
    }

    @Test
    fun `parseDate should return null for invalid date string`() {
        // Given
        val dateString = "invalid-date"

        // When
        val result = DateFormatter.parseDate(dateString)

        // Then
        assertNull(result)
    }

    @Test
    fun `parseDate should return null for empty string`() {
        // Given
        val dateString = ""

        // When
        val result = DateFormatter.parseDate(dateString)

        // Then
        assertNull(result)
    }

    @Test
    fun `Instant formatDateForDisplay should format correctly`() {
        // Given
        val instant = Clock.System.now()
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        val expected = "${localDateTime.dayOfMonth.toString().padStart(2, '0')}/${localDateTime.monthNumber.toString().padStart(2, '0')}/${localDateTime.year}"

        // When
        val result = instant.formatDateForDisplay()

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `LocalDate formatDateForDisplay should format correctly`() {
        // Given
        val date = LocalDate(2024, 1, 15)

        // When
        val result = date.formatDateForDisplay()

        // Then
        assertEquals("15/01/2024", result)
    }

    @Test
    fun `LocalDate formatDateForDisplay should pad single digits`() {
        // Given
        val date = LocalDate(2024, 1, 5)

        // When
        val result = date.formatDateForDisplay()

        // Then
        assertEquals("05/01/2024", result)
    }

    @Test
    fun stringParseDateFromStringShouldParseValidDDMMYYYYFormat() {
        // Given
        val dateString = "15/01/2024"

        // When
        val result = dateString.parseDateFromString()

        // Then
        assertEquals(LocalDate(2024, 1, 15), result)
    }

    @Test
    fun `String parseDateFromString should parse valid date with single digit day and month`() {
        // Given
        val dateString = "5/1/2024"

        // When
        val result = dateString.parseDateFromString()

        // Then
        assertEquals(LocalDate(2024, 1, 5), result)
    }

    @Test
    fun `String parseDateFromString should return null for invalid day`() {
        // Given
        val dateString = "32/01/2024"

        // When
        val result = dateString.parseDateFromString()

        // Then
        assertNull(result)
    }

    @Test
    fun `String parseDateFromString should return null for invalid month`() {
        // Given
        val dateString = "15/13/2024"

        // When
        val result = dateString.parseDateFromString()

        // Then
        assertNull(result)
    }

    @Test
    fun `String parseDateFromString should return null for invalid year`() {
        // Given
        val dateString = "15/01/1800"

        // When
        val result = dateString.parseDateFromString()

        // Then
        assertNull(result)
    }

    @Test
    fun `String parseDateFromString should return null for invalid format`() {
        // Given
        val dateString = "15-01-2024"

        // When
        val result = dateString.parseDateFromString()

        // Then
        assertNull(result)
    }

    @Test
    fun `String parseDateFromString should return null for empty string`() {
        // Given
        val dateString = ""

        // When
        val result = dateString.parseDateFromString()

        // Then
        assertNull(result)
    }

    @Test
    fun `String parseDateFromString should return null for non-numeric parts`() {
        // Given
        val dateString = "abc/01/2024"

        // When
        val result = dateString.parseDateFromString()

        // Then
        assertNull(result)
    }

    @Test
    fun `String parseDateFromString should handle edge case dates`() {
        // Given
        val validDates = listOf(
            "01/01/2024" to LocalDate(2024, 1, 1),
            "31/12/2024" to LocalDate(2024, 12, 31),
            "29/02/2024" to LocalDate(2024, 2, 29) // Leap year
        )

        validDates.forEach { (dateString, expectedDate) ->
            // When
            val result = dateString.parseDateFromString()

            // Then
            assertEquals(expectedDate, result, "Failed for date: $dateString")
        }
    }
}