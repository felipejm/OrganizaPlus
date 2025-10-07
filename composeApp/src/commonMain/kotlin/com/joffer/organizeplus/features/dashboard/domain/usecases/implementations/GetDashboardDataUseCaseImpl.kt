package com.joffer.organizeplus.features.dashboard.domain.usecases.implementations

import com.joffer.organizeplus.features.dashboard.domain.entities.DashboardData
import com.joffer.organizeplus.features.dashboard.domain.repositories.DashboardRepository
import com.joffer.organizeplus.features.dashboard.domain.usecases.GetDashboardDataUseCase
import kotlinx.coroutines.flow.Flow

class GetDashboardDataUseCaseImpl(
    private val repository: DashboardRepository
) : GetDashboardDataUseCase {

    override suspend operator fun invoke(): Flow<Result<DashboardData>> {
        return repository.getDashboardData()
    }
}
