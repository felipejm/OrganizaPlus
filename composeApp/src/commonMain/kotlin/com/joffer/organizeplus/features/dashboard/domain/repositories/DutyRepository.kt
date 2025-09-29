package com.joffer.organizeplus.features.dashboard.domain.repositories

import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyCategory
import com.joffer.organizeplus.features.dashboard.domain.entities.MonthlySummary
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

interface DutyRepository {
    
    // Dutys
    suspend fun getAllDuties(): Flow<Result<List<Duty>>>
    suspend fun getDutyById(id: String): Flow<Result<Duty?>>
    suspend fun getDutiesByDateRange(startDate: Instant, endDate: Instant): Flow<Result<List<Duty>>>
    suspend fun getDutiesByStatus(status: Duty.Status): Flow<Result<List<Duty>>>
    suspend fun getDutiesByCategory(categoryId: String): Flow<Result<List<Duty>>>
    suspend fun getDutiesByType(isPersonal: Boolean): Flow<Result<List<Duty>>>
    suspend fun searchDuties(query: String): Flow<Result<List<Duty>>>
    suspend fun insertDuty(duty: Duty): Flow<Result<Unit>>
    suspend fun updateDuty(duty: Duty): Flow<Result<Unit>>
    suspend fun deleteDuty(id: String): Flow<Result<Unit>>
    suspend fun markDutyPaid(id: String, paidAt: Instant): Flow<Result<Unit>>
    suspend fun markDutiesPaid(ids: List<String>, paidAt: Instant): Flow<Result<Unit>>
    suspend fun snoozeDuty(id: String, snoozeUntil: Instant): Flow<Result<Unit>>
    
    // Categories
    suspend fun getAllCategories(): Flow<Result<List<DutyCategory>>>
    suspend fun getCategoryById(id: String): Flow<Result<DutyCategory?>>
    suspend fun getCategoriesByType(isPersonal: Boolean): Flow<Result<List<DutyCategory>>>
    suspend fun insertCategory(category: DutyCategory): Flow<Result<Unit>>
    suspend fun updateCategory(category: DutyCategory): Flow<Result<Unit>>
    suspend fun deleteCategory(id: String): Flow<Result<Unit>>
    
    // Dashboard data
    suspend fun getMonthlySummary(month: Int, year: Int): Flow<Result<MonthlySummary>>
    suspend fun getTodayDuties(): Flow<Result<List<Duty>>>
    suspend fun getUpcomingDuties(days: Int = 7): Flow<Result<List<Duty>>>
}
