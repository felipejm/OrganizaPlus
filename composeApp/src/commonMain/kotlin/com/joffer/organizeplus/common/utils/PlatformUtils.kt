package com.joffer.organizeplus.common.utils

/**
 * Platform-specific string formatting
 * Replaces String.format which is not available in KMP common code
 */
expect fun formatString(format: String, vararg args: Any?): String

/**
 * Platform-specific current time in milliseconds
 * Replaces System.currentTimeMillis() which is not available in KMP common code
 */
expect fun currentTimeMillis(): Long
