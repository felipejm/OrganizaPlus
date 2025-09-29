package com.joffer.organizeplus.features.dashboard.data.repositories

import com.joffer.organizeplus.features.dashboard.domain.entities.MonthlySummary
import com.joffer.organizeplus.features.dashboard.domain.entities.CategorySummary
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyCategory
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.days

class FakeDutyRepository : DutyRepository {
    private val duties = mutableListOf<Duty>()
    private val categories = mutableListOf<DutyCategory>()

    var shouldThrowError = false
    var errorMessage = "Test error"

    init {
        setupFakeData()
    }

    private fun setupFakeData() {
        val now = Clock.System.now()
        val tomorrow = now.plus(1.days)
        val nextWeek = now.plus(7.days)
        
        categories.addAll(listOf(
            DutyCategory("1", "Conta de Luz", "Energia", true, "#FF9800", "⚡", now, now),
            DutyCategory("2", "Condomínio", "Moradia", true, "#2196F3", "🏢", now, now),
            DutyCategory("3", "Financiamento", "Financeiro", true, "#4CAF50", "🚗", now, now),
            DutyCategory("4", "DAS Empresa", "Empresa", false, "#FF5722", "📄", now, now),
            DutyCategory("5", "Internet", "Comunicação", true, "#9C27B0", "🌐", now, now)
        ))

        duties.addAll(listOf(
            Duty(
                id = "1",
                title = "Conta de Luz",
                startDate = now,
                dueDate = now,
                type = DutyType.PAYABLE,
                categoryId = "1",
                status = Duty.Status.PENDING,
                priority = Duty.Priority.MEDIUM,
                createdAt = now,
                updatedAt = now
            ),
            Duty(
                id = "2",
                title = "Condomínio",
                startDate = now,
                dueDate = now,
                type = DutyType.PAYABLE,
                categoryId = "2",
                status = Duty.Status.PAID,
                priority = Duty.Priority.HIGH,
                createdAt = now,
                updatedAt = now
            ),
            Duty(
                id = "3",
                title = "DAS Empresa",
                startDate = now,
                dueDate = now,
                type = DutyType.ACTIONABLE,
                categoryId = "4",
                status = Duty.Status.PENDING,
                priority = Duty.Priority.URGENT,
                createdAt = now,
                updatedAt = now
            ),
            Duty(
                id = "4",
                title = "Internet",
                startDate = now,
                dueDate = tomorrow,
                type = DutyType.PAYABLE,
                categoryId = "5",
                status = Duty.Status.PENDING,
                priority = Duty.Priority.MEDIUM,
                createdAt = now,
                updatedAt = now
            ),
            Duty(
                id = "5",
                title = "Financiamento Carro",
                startDate = now,
                dueDate = nextWeek,
                type = DutyType.PAYABLE,
                categoryId = "3",
                status = Duty.Status.PENDING,
                priority = Duty.Priority.HIGH,
                createdAt = now,
                updatedAt = now
            )
        ))
    }

    fun addDuty(duty: Duty) {
        duties.add(duty)
    }

    fun addCategory(category: DutyCategory) {
        categories.add(category)
    }

    fun clear() {
        duties.clear()
        categories.clear()
    }

    override suspend fun getAllDuties(): Flow<Result<List<Duty>>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        return flowOf(Result.success(duties.toList()))
    }

    override suspend fun getDutyById(id: String): Flow<Result<Duty?>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        val duty = duties.find { it.id == id }
        return flowOf(Result.success(duty))
    }

    override suspend fun getDutiesByDateRange(startDate: Instant, endDate: Instant): Flow<Result<List<Duty>>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        val filtered = duties.filter {
            it.dueDate >= startDate && it.dueDate <= endDate
        }
        return flowOf(Result.success(filtered))
    }

    override suspend fun getDutiesByStatus(status: Duty.Status): Flow<Result<List<Duty>>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        val filtered = duties.filter { it.status == status }
        return flowOf(Result.success(filtered))
    }

    override suspend fun getDutiesByCategory(categoryId: String): Flow<Result<List<Duty>>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        val filtered = duties.filter { it.categoryId == categoryId }
        return flowOf(Result.success(filtered))
    }

    override suspend fun getDutiesByType(isPersonal: Boolean): Flow<Result<List<Duty>>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        val filtered = duties.filter { duty -> 
            val category = categories.find { it.id == duty.categoryId }
            category?.isPersonal == isPersonal
        }
        return flowOf(Result.success(filtered))
    }

    override suspend fun searchDuties(query: String): Flow<Result<List<Duty>>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        val filtered = duties.filter {
            it.title.contains(query, ignoreCase = true)
        }
        return flowOf(Result.success(filtered))
    }

    override suspend fun insertDuty(duty: Duty): Flow<Result<Unit>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        duties.add(duty)
        return flowOf(Result.success(Unit))
    }

    override suspend fun updateDuty(duty: Duty): Flow<Result<Unit>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        val index = duties.indexOfFirst { it.id == duty.id }
        if (index != -1) {
            duties[index] = duty
            return flowOf(Result.success(Unit))
        }
        return flowOf(Result.failure(Exception("Duty not found")))
    }

    override suspend fun deleteDuty(id: String): Flow<Result<Unit>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        val removed = duties.removeAll { it.id == id }
        return if (removed) flowOf(Result.success(Unit)) else flowOf(Result.failure(Exception("Duty not found")))
    }

    override suspend fun markDutyPaid(id: String, paidAt: Instant): Flow<Result<Unit>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        val duty = duties.find { it.id == id }
        if (duty != null) {
            val updatedDuty = duty.copy(status = Duty.Status.PAID)
            val index = duties.indexOfFirst { it.id == id }
            duties[index] = updatedDuty
            return flowOf(Result.success(Unit))
        }
        return flowOf(Result.failure(Exception("Duty not found")))
    }

    override suspend fun markDutiesPaid(ids: List<String>, paidAt: Instant): Flow<Result<Unit>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        ids.forEach { id ->
            val duty = duties.find { it.id == id }
            if (duty != null) {
                val updatedDuty = duty.copy(status = Duty.Status.PAID)
                val index = duties.indexOfFirst { it.id == id }
                duties[index] = updatedDuty
            }
        }
        return flowOf(Result.success(Unit))
    }

    override suspend fun snoozeDuty(id: String, snoozeUntil: Instant): Flow<Result<Unit>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        val duty = duties.find { it.id == id }
        if (duty != null) {
            val updatedDuty = duty.copy(dueDate = snoozeUntil)
            val index = duties.indexOfFirst { it.id == id }
            duties[index] = updatedDuty
        }
        return flowOf(Result.success(Unit))
    }

    override suspend fun getAllCategories(): Flow<Result<List<DutyCategory>>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        return flowOf(Result.success(categories.toList()))
    }

    override suspend fun getCategoryById(id: String): Flow<Result<DutyCategory?>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        val category = categories.find { it.id == id }
        return flowOf(Result.success(category))
    }

    override suspend fun getCategoriesByType(isPersonal: Boolean): Flow<Result<List<DutyCategory>>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        val filtered = categories.filter { it.isPersonal == isPersonal }
        return flowOf(Result.success(filtered))
    }

    override suspend fun insertCategory(category: DutyCategory): Flow<Result<Unit>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        categories.add(category)
        return flowOf(Result.success(Unit))
    }

    override suspend fun updateCategory(category: DutyCategory): Flow<Result<Unit>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        val index = categories.indexOfFirst { it.id == category.id }
        if (index != -1) {
            categories[index] = category
            return flowOf(Result.success(Unit))
        }
        return flowOf(Result.failure(Exception("Category not found")))
    }

    override suspend fun deleteCategory(id: String): Flow<Result<Unit>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        val removed = categories.removeAll { it.id == id }
        return if (removed) flowOf(Result.success(Unit)) else flowOf(Result.failure(Exception("Category not found")))
    }

    override suspend fun getMonthlySummary(month: Int, year: Int): Flow<Result<MonthlySummary>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        val monthlyDutys = duties.filter {
            val dutyDate = it.dueDate.toLocalDateTime(TimeZone.currentSystemDefault())
            dutyDate.month.value == month && dutyDate.year == year
        }
        
        val personalDutys = monthlyDutys.filter { duty -> 
            val category = categories.find { it.id == duty.categoryId }
            category?.isPersonal == true
        }
        val businessDutys = monthlyDutys.filter { duty -> 
            val category = categories.find { it.id == duty.categoryId }
            category?.isPersonal == false
        }
        
        val summary = MonthlySummary(
            month = month,
            year = year,
            personal = CategorySummary(
                total = personalDutys.size,
                pending = personalDutys.count { it.status == Duty.Status.PENDING },
                paid = personalDutys.count { it.status == Duty.Status.PAID },
                overdue = personalDutys.count { it.status == Duty.Status.OVERDUE }
            ),
            business = CategorySummary(
                total = businessDutys.size,
                pending = businessDutys.count { it.status == Duty.Status.PENDING },
                paid = businessDutys.count { it.status == Duty.Status.PAID },
                overdue = businessDutys.count { it.status == Duty.Status.OVERDUE }
            )
        )
        return flowOf(Result.success(summary))
    }

    override suspend fun getTodayDuties(): Flow<Result<List<Duty>>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val todayDutys = duties.filter {
            it.dueDate.toLocalDateTime(TimeZone.currentSystemDefault()).date == today
        }
        return flowOf(Result.success(todayDutys))
    }

    override suspend fun getUpcomingDuties(days: Int): Flow<Result<List<Duty>>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        val now = Clock.System.now()
        val futureDate = now.plus(days.days)
        val upcomingDutys = duties.filter {
            it.dueDate > now && it.dueDate <= futureDate && it.status != Duty.Status.PAID
        }
        return flowOf(Result.success(upcomingDutys))
    }
}