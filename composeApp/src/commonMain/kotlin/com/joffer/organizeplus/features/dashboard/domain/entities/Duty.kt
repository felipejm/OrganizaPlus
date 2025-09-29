package com.joffer.organizeplus.features.dashboard.domain.entities

import kotlinx.datetime.Instant

data class Duty(
    val id: String,
    val title: String,
    val startDate: Instant,
    val dueDate: Instant,
    val type: DutyType,
    val categoryId: String,
    val status: Status,
    val priority: Priority,
    val snoozeUntil: Instant? = null,
    val createdAt: Instant,
    val updatedAt: Instant
) {
    enum class Status {
        PENDING, PAID, OVERDUE, SNOOZED
    }
    
    enum class Priority {
        LOW, MEDIUM, HIGH, URGENT
    }
    
    fun isOverdue(): Boolean {
        val now = kotlinx.datetime.Clock.System.now()
        return status == Status.PENDING && dueDate < now
    }
    
    fun isDueToday(): Boolean {
        return status == Status.PENDING
    }
    
    fun isDueInNext7Days(): Boolean {
        return status == Status.PENDING
    }
}
