package com.joffer.organizeplus.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "duty_occurrences")
data class DutyOccurrenceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dutyId: Long,
    val paidAmount: Double,
    val completedDateMillis: Long
)
