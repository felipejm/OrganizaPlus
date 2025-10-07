package com.joffer.organizeplus.features.dashboard.domain.usecases

import com.joffer.organizeplus.features.dashboard.domain.entities.DashboardData
import com.joffer.organizeplus.features.dashboard.domain.repositories.DashboardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDashboardRepository : DashboardRepository {
    
    var dashboardData: DashboardData? = null
    var shouldThrowError = false
    var errorMessage = "Repository error"

    override suspend fun getDashboardData(): Flow<Result<DashboardData>> {
        return if (shouldThrowError) {
            flowOf(Result.failure(Exception(errorMessage)))
        } else {
            dashboardData?.let { flowOf(Result.success(it)) }
                ?: flowOf(Result.failure(Exception("No data configured")))
        }
    }

    fun clear() {
        dashboardData = null
        shouldThrowError = false
    }
}

