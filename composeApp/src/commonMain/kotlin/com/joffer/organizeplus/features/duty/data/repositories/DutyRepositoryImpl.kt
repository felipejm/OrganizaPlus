package com.joffer.organizeplus.features.duty.data.repositories

import com.joffer.organizeplus.database.dao.DutyDao
import com.joffer.organizeplus.database.mappers.DutyMapper
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.features.dashboard.domain.repositories.DutyRepository
import com.joffer.organizeplus.features.duty.data.remote.DutyRemoteDataSource
import com.joffer.organizeplus.features.duty.data.remote.DutyRemoteMapper
import com.joffer.organizeplus.features.settings.domain.StorageMode
import com.joffer.organizeplus.features.settings.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class DutyRepositoryImpl(
    private val remoteDataSource: DutyRemoteDataSource,
    private val dutyDao: DutyDao,
    private val settingsRepository: SettingsRepository
) : DutyRepository {

    override suspend fun getAllDuties(): Flow<Result<List<Duty>>> {
        return when (settingsRepository.getStorageMode()) {
            StorageMode.REMOTE -> getRemoteDuties()
            StorageMode.LOCAL -> getLocalDuties()
        }
    }

    override suspend fun getDutyById(id: Long): Flow<Result<Duty?>> {
        return when (settingsRepository.getStorageMode()) {
            StorageMode.REMOTE -> getRemoteDutyById(id)
            StorageMode.LOCAL -> getLocalDutyById(id)
        }
    }

    override suspend fun insertDuty(duty: Duty): Flow<Result<Unit>> {
        return when (settingsRepository.getStorageMode()) {
            StorageMode.REMOTE -> createRemoteDuty(duty)
            StorageMode.LOCAL -> insertLocalDuty(duty)
        }
    }

    override suspend fun updateDuty(duty: Duty): Flow<Result<Unit>> {
        return when (settingsRepository.getStorageMode()) {
            StorageMode.REMOTE -> flow { emit(Result.failure(Exception("Update not yet implemented for remote"))) }
            StorageMode.LOCAL -> updateLocalDuty(duty)
        }
    }

    override suspend fun deleteDuty(id: Long): Flow<Result<Unit>> {
        return when (settingsRepository.getStorageMode()) {
            StorageMode.REMOTE -> deleteRemoteDuty(id)
            StorageMode.LOCAL -> deleteLocalDuty(id)
        }
    }

    override suspend fun getUpcomingDuties(days: Int): Flow<Result<List<Duty>>> {
        // For now, return all duties regardless of storage mode
        return getAllDuties()
    }

    override suspend fun getLatestDuties(limit: Int): Flow<Result<List<Duty>>> {
        // For now, return all duties regardless of storage mode
        return getAllDuties()
    }

    // Remote operations
    private suspend fun getRemoteDuties(): Flow<Result<List<Duty>>> = flow {
        val result = remoteDataSource.getAllDuties()
            .map { response -> response.duties.map { DutyRemoteMapper.toDomain(it) } }
        emit(result)
    }

    private suspend fun getRemoteDutyById(id: Long): Flow<Result<Duty?>> = flow {
        val result = remoteDataSource.getDutyById(id)
            .map { response -> DutyRemoteMapper.toDomain(response.duty) }
        emit(result)
    }

    private suspend fun createRemoteDuty(duty: Duty): Flow<Result<Unit>> = flow {
        val request = DutyRemoteMapper.toRemote(duty)
        val result = remoteDataSource.createDuty(request)
            .map { Unit }
        emit(result)
    }

    private suspend fun deleteRemoteDuty(id: Long): Flow<Result<Unit>> = flow {
        val result = remoteDataSource.deleteDuty(id)
        emit(result)
    }

    // Local operations
    private suspend fun getLocalDuties(): Flow<Result<List<Duty>>> {
        return dutyDao.getAllDuties().map { entities ->
            Result.success(entities.map { DutyMapper.toDomainEntity(it) })
        }
    }

    private suspend fun getLocalDutyById(id: Long): Flow<Result<Duty?>> {
        val entity = dutyDao.getDutyById(id)
        return flowOf(Result.success(entity?.let { DutyMapper.toDomainEntity(it) }))
    }

    private suspend fun insertLocalDuty(duty: Duty): Flow<Result<Unit>> = flow {
        try {
            val entity = DutyMapper.toRoomEntity(duty)
            dutyDao.insertDuty(entity)
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    private suspend fun updateLocalDuty(duty: Duty): Flow<Result<Unit>> = flow {
        try {
            val entity = DutyMapper.toRoomEntity(duty)
            dutyDao.updateDuty(entity)
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    private suspend fun deleteLocalDuty(id: Long): Flow<Result<Unit>> = flow {
        try {
            dutyDao.deleteDutyById(id)
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
