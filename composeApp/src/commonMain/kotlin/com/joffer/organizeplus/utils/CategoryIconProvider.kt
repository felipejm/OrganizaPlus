package com.joffer.organizeplus.utils

object CategoryIconProvider {
    fun getIconForCategory(categoryId: String): String = when (categoryId) {
        "1" -> "⚡"
        "2" -> "🏢"
        "3" -> "🚗"
        "4" -> "📄"
        "5" -> "🌐"
        else -> "📋"
    }
}

