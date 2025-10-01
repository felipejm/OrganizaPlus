package com.joffer.organizeplus.common.utils

actual fun formatString(format: String, vararg args: Any?): String {
    return String.format(format, *args)
}

actual fun currentTimeMillis(): Long {
    return System.currentTimeMillis()
}
