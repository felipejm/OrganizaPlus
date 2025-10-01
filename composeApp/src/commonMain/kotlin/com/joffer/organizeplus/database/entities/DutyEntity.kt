package com.joffer.organizeplus.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = "duties")
data class DutyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val description: String? = null,
    val type: String = "PAYABLE",
    val isCompleted: Boolean = false,
    val categoryName: String? = null,
    val createdAt: Instant
)
