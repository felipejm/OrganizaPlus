package com.joffer.organizeplus.features.dashboard.data.repositories

import com.joffer.organizeplus.features.dashboard.data.local.DashboardLocalDataSource
import com.joffer.organizeplus.features.dashboard.data.local.DashboardLocalMapper
import com.joffer.organizeplus.features.dashboard.data.remote.DashboardRemoteDataSource
import com.joffer.organizeplus.features.dashboard.data.remote.DashboardRemoteMapper
import com.joffer.organizeplus.features.dashboard.domain.entities.DashboardData
import com.joffer.organizeplus.features.dashboard.domain.repositories.DashboardRepository
import com.joffer.organizeplus.features.settings.domain.StorageMode
import com.joffer.organizeplus.features.settings.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class DashboardRepositoryImpl(
    private val remoteDataSource: DashboardRemoteDataSource,
    private val localDataSource: DashboardLocalDataSource,
    private val settingsRepository: SettingsRepository
) : DashboardRepository {

    override suspend fun getDashboardData(): Flow<Result<DashboardData>> {
        return when (settingsRepository.getStorageMode()) {
            StorageMode.REMOTE -> getRemoteDashboardData()
            StorageMode.LOCAL -> getLocalDashboardData()
        }
    }

    private suspend fun getRemoteDashboardData(): Flow<Result<DashboardData>> = flow {
        val result = remoteDataSource.getDashboardData()
            .map { remoteResponse -> DashboardRemoteMapper.toDomain(remoteResponse) }
        emit(result)
    }

    private suspend fun getLocalDashboardData(): Flow<Result<DashboardData>> {
        return localDataSource.getAllDutiesWithLastOccurrence()
            .map { result ->
                result.map { dutiesWithOccurrences ->
                    DashboardLocalMapper.toDomain(dutiesWithOccurrences)
                }
            }
    }
}
