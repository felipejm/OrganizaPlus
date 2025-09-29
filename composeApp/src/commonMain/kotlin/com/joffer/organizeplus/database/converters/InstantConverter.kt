package com.joffer.organizeplus.database.converters

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

class InstantConverter {
    
    @TypeConverter
    fun fromInstant(instant: Instant?): Long? {
        return instant?.toEpochMilliseconds()
    }
    
    @TypeConverter
    fun toInstant(timestamp: Long?): Instant? {
        return timestamp?.let { Instant.fromEpochMilliseconds(it) }
    }
}