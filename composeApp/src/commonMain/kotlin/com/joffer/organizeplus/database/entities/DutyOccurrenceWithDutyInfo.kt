package com.joffer.organizeplus.database.entities

data class DutyOccurrenceWithDutyInfo(
    val id: Long,
    val dutyId: Long,
    val paidAmount: Double,
    val completedDateMillis: Long,
    val dutyTitle: String,
    val categoryName: String
)
