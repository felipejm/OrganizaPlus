package com.joffer.organizeplus.features.dashboard.domain.entities

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class Duty(
    val id: String,
    val title: String,
    val startDay: Int,
    val dueDay: Int,
    val type: DutyType,
    val categoryName: String,
    val status: Status,
    val snoozeUntil: Instant? = null,
    val createdAt: Instant
) {
    enum class Status {
        PENDING, PAID, OVERDUE, SNOOZED
    }
    
    fun isOverdue(): Boolean {
        val now = kotlinx.datetime.Clock.System.now()
        val currentDay = now.toLocalDateTime(TimeZone.currentSystemDefault()).date.dayOfMonth
        return status == Status.PENDING && dueDay < currentDay
    }
    
    fun isDueToday(): Boolean {
        val now = kotlinx.datetime.Clock.System.now()
        val currentDay = now.toLocalDateTime(TimeZone.currentSystemDefault()).date.dayOfMonth
        return status == Status.PENDING && dueDay == currentDay
    }
    
    fun isDueInNext7Days(): Boolean {
        val now = kotlinx.datetime.Clock.System.now()
        val currentDay = now.toLocalDateTime(TimeZone.currentSystemDefault()).date.dayOfMonth
        val nextWeekDay = ((currentDay + 6) % 31) + 1 // Simple calculation for next 7 days
        return status == Status.PENDING && dueDay in currentDay..nextWeekDay
    }
}
