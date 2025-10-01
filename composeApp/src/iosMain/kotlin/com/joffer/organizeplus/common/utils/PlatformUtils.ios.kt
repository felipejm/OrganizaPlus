package com.joffer.organizeplus.common.utils

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual fun formatString(format: String, vararg args: Any?): String {
    // Simple format string replacement for iOS
    // Handles basic cases like %d, %s, %f, %.2f, %02X
    if (args.isEmpty()) return format

    var result = format
    args.forEach { arg ->
        // Handle different format specifiers
        when {
            // Float with precision: %.Nf
            result.contains(Regex("%\\.[0-9]+f")) -> {
                val match = Regex("%\\.([0-9]+)f").find(result)
                if (match != null) {
                    val precision = match.groupValues[1].toIntOrNull() ?: 2
                    val doubleValue = (arg as? Number)?.toDouble() ?: 0.0
                    val formatted = formatDouble(doubleValue, precision)
                    result = result.replaceFirst(match.value, formatted)
                }
            }
            // Hex with padding: %02X
            result.contains(Regex("%[0-9]+X")) -> {
                val intValue = (arg as? Number)?.toInt() ?: 0
                val hex = intValue.toString(16).uppercase().padStart(2, '0')
                result = result.replaceFirst(Regex("%[0-9]+X"), hex)
            }
            // Generic replacement for %d, %s, etc.
            else -> {
                result = result.replaceFirst(Regex("%[a-zA-Z]"), arg.toString())
            }
        }
    }
    return result
}

private fun formatDouble(value: Double, precision: Int): String {
    val multiplier = when (precision) {
        0 -> 1.0
        1 -> 10.0
        2 -> 100.0
        3 -> 1000.0
        else -> {
            var m = 1.0
            repeat(precision) { m *= 10.0 }
            m
        }
    }
    val rounded = (value * multiplier).toLong().toDouble() / multiplier
    val wholePart = rounded.toLong()
    val fractionalPart = ((rounded - wholePart) * multiplier).toLong()

    return if (precision == 0) {
        wholePart.toString()
    } else {
        val fractionalStr = fractionalPart.toString().padStart(precision, '0')
        "$wholePart.$fractionalStr"
    }
}

actual fun currentTimeMillis(): Long {
    return (NSDate().timeIntervalSince1970 * 1000).toLong()
}
