package com.joffer.organizeplus.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.joffer.organizeplus.database.converters.InstantConverter
import com.joffer.organizeplus.database.converters.LocalDateConverter
import com.joffer.organizeplus.database.dao.DutyDao
import com.joffer.organizeplus.database.dao.DutyOccurrenceDao
import com.joffer.organizeplus.database.entities.DutyEntity
import com.joffer.organizeplus.database.entities.DutyOccurrenceEntity

@Database(
    entities = [
        DutyEntity::class,
        DutyOccurrenceEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    InstantConverter::class,
    LocalDateConverter::class
)
abstract class OrganizePlusDatabase : RoomDatabase() {
    
    abstract fun dutyDao(): DutyDao
    abstract fun dutyOccurrenceDao(): DutyOccurrenceDao
    
    companion object {
        fun create(databaseDriverFactory: DatabaseDriverFactory): OrganizePlusDatabase {
            return Room.databaseBuilder(
                databaseDriverFactory.getContext(),
                OrganizePlusDatabase::class.java,
                "organize_plus_database"
            ).build()
        }
    }
}
