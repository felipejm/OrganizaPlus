package com.joffer.organizeplus.database

import android.content.Context

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun getContext(): Context = context
}
