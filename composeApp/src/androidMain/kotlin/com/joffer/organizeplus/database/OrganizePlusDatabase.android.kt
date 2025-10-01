package com.joffer.organizeplus.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<OrganizePlusDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("organize_plus.db")
    return Room.databaseBuilder<OrganizePlusDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
