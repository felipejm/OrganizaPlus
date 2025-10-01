package com.joffer.organizeplus.features.duty.detail.domain.entities

import kotlinx.datetime.Instant

data class DutyDetails(
    val id: Long,
    val dutyId: Long,
    val paidAmount: Double,
    val paidDate: Instant,
    val createdAt: Instant,
    val updatedAt: Instant
)
