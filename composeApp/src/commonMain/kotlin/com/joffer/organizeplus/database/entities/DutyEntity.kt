package com.joffer.organizeplus.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = "duties")
data class DutyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String?,
    val dueDate: Instant?,
    val isCompleted: Boolean,
    val priority: String,
    val category: String?,
    val createdAt: Instant,
    val updatedAt: Instant
)
