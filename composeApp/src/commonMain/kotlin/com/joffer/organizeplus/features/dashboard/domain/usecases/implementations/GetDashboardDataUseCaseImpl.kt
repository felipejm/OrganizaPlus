package com.joffer.organizeplus.features.dashboard.domain.usecases.implementations

import com.joffer.organizeplus.features.dashboard.domain.entities.DashboardData
import com.joffer.organizeplus.features.dashboard.domain.repositories.DashboardRepository
import com.joffer.organizeplus.features.dashboard.domain.usecases.GetDashboardDataUseCase
import kotlinx.coroutines.flow.Flow

/**
 * Use case that retrieves complete dashboard data
 * 
 * Following clean architecture principles:
 * - Use case only interacts with its domain repository
 * - Repository layer handles all data coordination
 * - Business logic is properly separated by layers
 */
class GetDashboardDataUseCaseImpl(
    private val dashboardRepository: DashboardRepository
) : GetDashboardDataUseCase {

    override suspend operator fun invoke(): Flow<Result<DashboardData>> {
        return dashboardRepository.getDashboardData()
    }
}
