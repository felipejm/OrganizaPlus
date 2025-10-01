package com.joffer.organizeplus.common.utils

fun String.safeToDouble(): Double {
    return replace(",", ".")
        .toDoubleOrNull() ?: 0.0
}
