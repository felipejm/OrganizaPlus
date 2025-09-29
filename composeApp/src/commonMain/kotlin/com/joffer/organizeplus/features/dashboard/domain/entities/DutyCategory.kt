package com.joffer.organizeplus.features.dashboard.domain.entities

import kotlinx.datetime.Instant

data class DutyCategory(
    val id: String,
    val name: String,
    val description: String,
    val isPersonal: Boolean,
    val color: String,
    val icon: String,
    val createdAt: Instant,
    val updatedAt: Instant
)
