package com.joffer.organizeplus.features.dashboard.domain.entities

import kotlinx.datetime.Instant

data class Duty(
    val id: String,
    val title: String,
    val startDay: Int,
    val dueDay: Int,
    val type: DutyType,
    val categoryName: String,
    val createdAt: Instant
)
