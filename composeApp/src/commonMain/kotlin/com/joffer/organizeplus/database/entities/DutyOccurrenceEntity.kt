package com.joffer.organizeplus.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "duty_occurrences")
data class DutyOccurrenceEntity(
    @PrimaryKey
    val id: String,
    val dutyId: String,
    val paidAmount: Double,
    val completedDateMillis: Long
)
