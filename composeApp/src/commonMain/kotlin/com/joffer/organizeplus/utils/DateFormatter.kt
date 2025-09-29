package com.joffer.organizeplus.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DateFormatter {
    
    fun getDatePlaceholder(): String {
        return "MM/DD/YYYY"
    }
    
    fun getDatePlaceholderBR(): String {
        return "DD/MM/YYYY"
    }
    
    fun formatDate(date: LocalDate): String {
        return date.toString()
    }
    
    fun parseDate(dateString: String): LocalDate? {
        return try {
            LocalDate.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }
}

fun Instant.formatDateForDisplay(): String {
    val localDateTime = this.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${localDateTime.dayOfMonth.toString().padStart(2, '0')}/${localDateTime.monthNumber.toString().padStart(2, '0')}/${localDateTime.year}"
}

fun LocalDate.formatDateForDisplay(): String {
    return "${this.dayOfMonth.toString().padStart(2, '0')}/${this.monthNumber.toString().padStart(2, '0')}/${this.year}"
}

fun String.parseDateFromString(): LocalDate? {
    return try {
        // Try to parse DD/MM/YYYY format
        val parts = this.split("/")
        if (parts.size == 3) {
            val day = parts[0].toIntOrNull() ?: return null
            val month = parts[1].toIntOrNull() ?: return null
            val year = parts[2].toIntOrNull() ?: return null
            
            if (day in 1..31 && month in 1..12 && year > 1900) {
                LocalDate(year, month, day)
            } else {
                null
            }
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }
}
