package com.joffer.organizeplus.database

import android.content.Context

expect class DatabaseDriverFactory {
    fun getContext(): Context
}
