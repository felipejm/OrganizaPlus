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

    return if (this < 0) {
        "-$symbol $formattedValue"
    } else {
        "$symbol $formattedValue"
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
