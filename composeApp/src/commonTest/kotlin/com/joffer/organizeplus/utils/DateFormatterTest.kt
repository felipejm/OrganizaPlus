package com.joffer.organizeplus.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DateFormatterTest {

    @Test
    fun `formatDateForDisplay should format Instant to DD-MM-YYYY`() {
        // Given
        val instant = LocalDate(2024, 3, 15).atStartOfDayIn(TimeZone.currentSystemDefault())
        
        // When
        val result = instant.formatDateForDisplay()
        
        // Then
        assertEquals("15/03/2024", result)
    }

    @Test
    fun `formatDateForDisplay should pad single digits with zero`() {
        // Given
        val instant = LocalDate(2024, 1, 5).atStartOfDayIn(TimeZone.currentSystemDefault())
        
        // When
        val result = instant.formatDateForDisplay()
        
        // Then
        assertEquals("05/01/2024", result)
    }

    @Test
    fun `parseDateFromString should parse valid DD-MM-YYYY format`() {
        // Given
        val dateString = "15/03/2024"
        
        // When
        val result = dateString.parseDateFromString()
        
        // Then
        assertEquals(LocalDate(2024, 3, 15), result)
    }

    @Test
    fun `parseDateFromString should parse single digit day and month`() {
        // Given
        val dateString = "5/1/2024"
        
        // When
        val result = dateString.parseDateFromString()
        
        // Then
        assertEquals(LocalDate(2024, 1, 5), result)
    }

    @Test
    fun `parseDateFromString should return null for invalid format`() {
        // Given
        val dateString = "invalid-date"
        
        // When
        val result = dateString.parseDateFromString()
        
        // Then
        assertNull(result)
    }

    @Test
    fun `parseDateFromString should return null for invalid day`() {
        // Given
        val dateString = "32/03/2024"
        
        // When
        val result = dateString.parseDateFromString()
        
        // Then
        assertNull(result)
    }

    @Test
    fun `parseDateFromString should return null for invalid month`() {
        // Given
        val dateString = "15/13/2024"
        
        // When
        val result = dateString.parseDateFromString()
        
        // Then
        assertNull(result)
    }

    @Test
    fun `parseDateFromString should return null for invalid year`() {
        // Given
        val dateString = "15/03/1800"
        
        // When
        val result = dateString.parseDateFromString()
        
        // Then
        assertNull(result)
    }

    @Test
    fun `parseDateFromString should return null for empty string`() {
        // Given
        val dateString = ""
        
        // When
        val result = dateString.parseDateFromString()
        
        // Then
        assertNull(result)
    }

    @Test
    fun `parseDateFromString should return null for wrong number of parts`() {
        // Given
        val dateString = "15/03"
        
        // When
        val result = dateString.parseDateFromString()
        
        // Then
        assertNull(result)
    }

    @Test
    fun `parseDateFromString should return null for non-numeric parts`() {
        // Given
        val dateString = "15/abc/2024"
        
        // When
        val result = dateString.parseDateFromString()
        
        // Then
        assertNull(result)
    }

    @Test
    fun `round trip test - format then parse should return same date`() {
        // Given
        val originalDate = LocalDate(2024, 12, 25)
        val instant = originalDate.atStartOfDayIn(TimeZone.currentSystemDefault())
        
        // When
        val formatted = instant.formatDateForDisplay()
        val parsed = formatted.parseDateFromString()
        
        // Then
        assertEquals(originalDate, parsed)
    }
}
