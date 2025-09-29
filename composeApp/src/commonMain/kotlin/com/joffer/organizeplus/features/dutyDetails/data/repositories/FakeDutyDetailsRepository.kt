package com.joffer.organizeplus.features.dutyDetails.data.repositories

import com.joffer.organizeplus.features.dutyDetails.domain.entities.DutyDetails
import com.joffer.organizeplus.features.dutyDetails.domain.repositories.DutyDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.days

class FakeDutyDetailsRepository : DutyDetailsRepository {
    private val records = mutableListOf<DutyDetails>()
    
    var shouldThrowError = false
    var errorMessage = "Test error"
    
    init {
        setupFakeData()
    }
    
    private fun setupFakeData() {
        val now = Clock.System.now()
        val yesterday = now.minus(1.days)
        val lastWeek = now.minus(7.days)
        
        records.addAll(listOf(
            DutyDetails(
                id = "1",
                dutyId = "1",
                paidAmount = 150.50,
                paidDate = yesterday,
                notes = "Pago via PIX",
                createdAt = yesterday,
                updatedAt = yesterday
            ),
            DutyDetails(
                id = "2",
                dutyId = "2",
                paidAmount = 300.00,
                paidDate = lastWeek,
                notes = "Pago via boleto",
                createdAt = lastWeek,
                updatedAt = lastWeek
            ),
            DutyDetails(
                id = "3",
                dutyId = "1",
                paidAmount = 75.25,
                paidDate = now,
                notes = "Parcela adicional",
                createdAt = now,
                updatedAt = now
            )
        ))
    }
    
    override suspend fun getAllRecords(): Flow<Result<List<DutyDetails>>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        return flowOf(Result.success(records.toList()))
    }
    
    override suspend fun getRecordsByDutyId(dutyId: String): Flow<Result<List<DutyDetails>>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        val filteredRecords = records.filter { it.dutyId == dutyId }
        return flowOf(Result.success(filteredRecords))
    }
    
    override suspend fun getRecordById(id: String): Flow<Result<DutyDetails?>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        val record = records.find { it.id == id }
        return flowOf(Result.success(record))
    }
    
    override suspend fun insertRecord(record: DutyDetails): Flow<Result<Unit>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        records.add(record)
        return flowOf(Result.success(Unit))
    }
    
    override suspend fun updateRecord(record: DutyDetails): Flow<Result<Unit>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        val index = records.indexOfFirst { it.id == record.id }
        if (index != -1) {
            records[index] = record
            return flowOf(Result.success(Unit))
        }
        return flowOf(Result.failure(Exception("Record not found")))
    }
    
    override suspend fun deleteRecord(id: String): Flow<Result<Unit>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        val removed = records.removeAll { it.id == id }
        return if (removed) flowOf(Result.success(Unit)) else flowOf(Result.failure(Exception("Record not found")))
    }
    
    override suspend fun getRecordsByDateRange(startDate: Instant, endDate: Instant): Flow<Result<List<DutyDetails>>> {
        if (shouldThrowError) {
            return flowOf(Result.failure(Exception(errorMessage)))
        }
        val filteredRecords = records.filter { 
            it.paidDate >= startDate && it.paidDate <= endDate 
        }
        return flowOf(Result.success(filteredRecords))
    }
}
