package com.joffer.organizeplus.features.dashboard.domain.entities

data class MonthlySummary(
    val year: Int,
    val month: Int,
    val personal: CategorySummary,
    val business: CategorySummary
)

data class CategorySummary(
    val total: Int,
    val pending: Int,
    val paid: Int,
    val overdue: Int
)
