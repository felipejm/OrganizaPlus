package com.joffer.organizeplus.features.dashboard.domain.entities

import kotlinx.datetime.Instant

data class Duty(
    val id: String,
    val title: String,
    val type: DutyType,
    val categoryName: String,
    val createdAt: Instant
)
