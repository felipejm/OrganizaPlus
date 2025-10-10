package com.joffer.organizeplus.features.dashboard.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DashboardRemoteResponse(
    @SerialName("dashboard") val dashboard: DashboardDataRemote,
    @SerialName("metadata") val metadata: DashboardMetadataRemote
)

@Serializable
data class DashboardDataRemote(
    @SerialName("personalDuties") val personalDuties: List<EnhancedDutyWithOccurrenceRemote>,
    @SerialName("companyDuties") val companyDuties: List<EnhancedDutyWithOccurrenceRemote>,
    @SerialName("personalSummary") val personalSummary: EnhancedMonthlySummaryRemote,
    @SerialName("companySummary") val companySummary: EnhancedMonthlySummaryRemote,
    @SerialName("overallStats") val overallStats: OverallStatsRemote
)

@Serializable
data class EnhancedDutyWithOccurrenceRemote(
    @SerialName("duty") val duty: EnhancedDutyRemote,
    @SerialName("lastOccurrence") val lastOccurrence: EnhancedDutyOccurrenceRemote?,
    @SerialName("hasCurrentMonthOccurrence") val hasCurrentMonthOccurrence: Boolean,
    @SerialName("status") val status: DutyStatusRemote,
    @SerialName("nextDueDate") val nextDueDate: String?,
    @SerialName("isOverdue") val isOverdue: Boolean,
    @SerialName("displayInfo") val displayInfo: DutyDisplayInfoRemote
)

@Serializable
data class EnhancedDutyRemote(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String,
    @SerialName("type") val type: String,
    @SerialName("categoryName") val categoryName: String,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("frequency") val frequency: String?,
    @SerialName("estimatedAmount") val estimatedAmount: Double?
)

@Serializable
data class EnhancedDutyOccurrenceRemote(
    @SerialName("id") val id: Long,
    @SerialName("dutyId") val dutyId: Long,
    @SerialName("amountPaid") val amountPaid: Double?,
    @SerialName("completedAt") val completedAt: String,
    @SerialName("notes") val notes: String?,
    @SerialName("formattedAmount") val formattedAmount: String?,
    @SerialName("daysAgo") val daysAgo: Int,
    @SerialName("isRecent") val isRecent: Boolean
)

@Serializable
data class EnhancedMonthlySummaryRemote(
    @SerialName("totalAmountPaid") val totalAmountPaid: Double,
    @SerialName("totalCompleted") val totalCompleted: Int,
    @SerialName("totalTasks") val totalTasks: Int,
    @SerialName("currentMonth") val currentMonth: Int,
    @SerialName("year") val year: Int,
    @SerialName("formattedAmount") val formattedAmount: String,
    @SerialName("completionRate") val completionRate: Double,
    @SerialName("monthName") val monthName: String,
    @SerialName("comparisonWithPreviousMonth") val comparisonWithPreviousMonth: String?
)

@Serializable
data class OverallStatsRemote(
    @SerialName("totalDuties") val totalDuties: Int,
    @SerialName("completedThisMonth") val completedThisMonth: Int,
    @SerialName("pendingDuties") val pendingDuties: Int,
    @SerialName("overdueDuties") val overdueDuties: Int,
    @SerialName("totalAmountPaidThisMonth") val totalAmountPaidThisMonth: Double,
    @SerialName("formattedTotalAmount") val formattedTotalAmount: String,
    @SerialName("completionRate") val completionRate: Double,
    @SerialName("hasOverdueItems") val hasOverdueItems: Boolean,
    @SerialName("needsAttention") val needsAttention: List<Long>
)

@Serializable
data class DashboardMetadataRemote(
    @SerialName("generatedAt") val generatedAt: String,
    @SerialName("currentMonth") val currentMonth: Int,
    @SerialName("currentYear") val currentYear: Int,
    @SerialName("monthName") val monthName: String,
    @SerialName("timezone") val timezone: String,
    @SerialName("version") val version: String,
    @SerialName("hasData") val hasData: Boolean,
    @SerialName("isEmpty") val isEmpty: Boolean
)

@Serializable
data class DutyStatusRemote(
    @SerialName("isCompleted") val isCompleted: Boolean,
    @SerialName("isPaid") val isPaid: Boolean,
    @SerialName("statusText") val statusText: String,
    @SerialName("statusColor") val statusColor: String
)

@Serializable
data class DutyDisplayInfoRemote(
    @SerialName("priority") val priority: String,
    @SerialName("categoryColor") val categoryColor: String,
    @SerialName("categoryIcon") val categoryIcon: String,
    @SerialName("typeDisplayName") val typeDisplayName: String,
    @SerialName("shortDescription") val shortDescription: String?
)
