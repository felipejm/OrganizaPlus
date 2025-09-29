package com.joffer.organizeplus.features.duty.domain.usecases.implementations

import com.joffer.organizeplus.features.duty.domain.entities.DutyForm
import com.joffer.organizeplus.features.duty.domain.usecases.SaveDutyUseCase
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyCategory
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.atStartOfDayIn

class SaveDutyUseCaseImpl(
    private val repository: DutyRepository
) : SaveDutyUseCase {
    
    override suspend operator fun invoke(form: DutyForm): Flow<Result<Unit>> {
        return try {
            val duty = convertFormToDuty(form)
            repository.insertDuty(duty)
        } catch (e: Exception) {
            flowOf(Result.failure(e))
        }
    }
    
    private suspend fun convertFormToDuty(form: DutyForm): Duty {
        val now = Clock.System.now()
        val startDate = parseDate(form.startDate) ?: now
        val dueDate = parseDate(form.dueDate) ?: now
        
        // Usar o tipo escolhido pelo usuário
        val dutyType = form.dutyType
        
        return Duty(
            id = form.id ?: generateId(),
            title = form.title,
            startDate = startDate,
            dueDate = dueDate,
            type = dutyType,
            categoryId = form.categoryId,
            status = Duty.Status.PENDING,
            priority = convertPriority(form.priority),
            createdAt = now,
            updatedAt = now
        )
    }
    
    private fun parseDate(dateString: String): Instant? {
        return try {
            val localDate = LocalDate.parse(dateString)
            localDate.atStartOfDayIn(TimeZone.currentSystemDefault())
        } catch (e: Exception) {
            null
        }
    }
    
    private fun convertPriority(priority: DutyForm.Priority): Duty.Priority {
        return when (priority) {
            DutyForm.Priority.LOW -> Duty.Priority.LOW
            DutyForm.Priority.MEDIUM -> Duty.Priority.MEDIUM
            DutyForm.Priority.HIGH -> Duty.Priority.HIGH
            DutyForm.Priority.URGENT -> Duty.Priority.URGENT
        }
    }
    
    
    private fun generateId(): String {
        return "obligation_${System.currentTimeMillis()}"
    }
}
