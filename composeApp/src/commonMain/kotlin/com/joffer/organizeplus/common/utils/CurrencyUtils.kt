package com.joffer.organizeplus.common.utils

import kotlin.math.abs

/**
 * Extension function to format a Double as currency
 *
 * @param symbol The currency symbol (default: "R$")
 * @param decimalPlaces Number of decimal places to show (default: 2)
 * @return Formatted currency string
 */
fun Double.toCurrencyFormat(
    symbol: String = "R$",
    decimalPlaces: Int = 2
): String {
    val absValue = abs(this)
    val formattedValue = formatString("%.${decimalPlaces}f", absValue)
    val valueWithThousandsSeparator = addThousandsSeparator(formattedValue)

    return if (this < 0) {
        "-$symbol $valueWithThousandsSeparator"
    } else {
        "$symbol $valueWithThousandsSeparator"
    }
}

/**
 * Extension function to format a nullable Double as currency
 *
 * @param symbol The currency symbol (default: "R$")
 * @param decimalPlaces Number of decimal places to show (default: 2)
 * @return Formatted currency string or empty string if null
 */
fun Double?.toCurrencyFormat(
    symbol: String = "R$",
    decimalPlaces: Int = 2
): String {
    return this?.toCurrencyFormat(symbol, decimalPlaces) ?: ""
}

object CurrencyUtils {
    fun formatCurrency(amount: Double): String {
        return amount.toCurrencyFormat()
    }
}

/**
 * Adds thousands separator (dot) to a formatted number string
 *
 * @param formattedValue The formatted number string (e.g., "1234.56")
 * @return The number string with thousands separator (e.g., "1.234,56")
 */
private fun addThousandsSeparator(formattedValue: String): String {
    val parts = formattedValue.split(".")
    val integerPart = parts[0]
    val decimalPart = if (parts.size > 1) parts[1] else ""

    // Add thousands separator to integer part
    val integerWithSeparator = integerPart.reversed()
        .chunked(3)
        .joinToString(".")
        .reversed()

    return if (decimalPart.isNotEmpty()) {
        "$integerWithSeparator,$decimalPart"
    } else {
        integerWithSeparator
    }
}
