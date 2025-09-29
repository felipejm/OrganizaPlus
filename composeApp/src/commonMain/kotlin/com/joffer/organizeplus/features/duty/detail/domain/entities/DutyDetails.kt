package com.joffer.organizeplus.features.dutyDetails.domain.entities

import kotlinx.datetime.Instant

data class DutyDetails(
    val id: String,
    val dutyId: String,
    val paidAmount: Double,
    val paidDate: Instant,
    val notes: String? = null,
    val createdAt: Instant,
    val updatedAt: Instant
)
