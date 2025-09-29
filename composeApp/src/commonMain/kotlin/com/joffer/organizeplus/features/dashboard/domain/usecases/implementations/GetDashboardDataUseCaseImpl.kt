package com.joffer.organizeplus.features.dashboard.domain.usecases.implementations

import com.joffer.organizeplus.features.dashboard.domain.entities.DashboardData
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.dashboard.domain.usecases.GetDashboardDataUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetDashboardDataUseCaseImpl(
    private val repository: DutyRepository
) : GetDashboardDataUseCase {
    
    override suspend operator fun invoke(): Flow<Result<DashboardData>> {
        return repository.getUpcomingDuties(7).map { result ->
            result.map { upcomingDuties ->
                DashboardData(
                    upcomingDuties = upcomingDuties
                )
            }
        }
    }
}
