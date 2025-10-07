package com.joffer.organizeplus.features.dashboard.domain.repositories

import com.joffer.organizeplus.features.dashboard.domain.entities.DashboardData
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    suspend fun getDashboardData(): Flow<Result<DashboardData>>
}
