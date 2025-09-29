package com.joffer.organizeplus.features.dashboard.domain.usecases

import com.joffer.organizeplus.features.dashboard.domain.entities.DashboardData
import kotlinx.coroutines.flow.Flow

interface GetDashboardDataUseCase {
    suspend operator fun invoke(): Flow<Result<DashboardData>>
}
