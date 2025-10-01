package com.joffer.organizeplus.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.joffer.organizeplus.database.converters.InstantConverter
import com.joffer.organizeplus.database.converters.LocalDateConverter
import com.joffer.organizeplus.database.dao.DutyDao
import com.joffer.organizeplus.database.dao.DutyOccurrenceDao
import com.joffer.organizeplus.database.entities.DutyEntity
import com.joffer.organizeplus.database.entities.DutyOccurrenceEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [
        DutyEntity::class,
        DutyOccurrenceEntity::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(
    InstantConverter::class,
    LocalDateConverter::class
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class OrganizePlusDatabase : RoomDatabase() {

    abstract fun dutyDao(): DutyDao
    abstract fun dutyOccurrenceDao(): DutyOccurrenceDao

    companion object {
        fun create(builder: Builder<OrganizePlusDatabase>): OrganizePlusDatabase {
            return builder
                .setDriver(BundledSQLiteDriver())
                .fallbackToDestructiveMigration(true)
                .fallbackToDestructiveMigrationOnDowngrade(true)
                .setQueryCoroutineContext(Dispatchers.IO)
                .build()
        }
    }
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<OrganizePlusDatabase> {
    override fun initialize(): OrganizePlusDatabase
}
