package com.joffer.organizeplus.database.dao

import androidx.room.*
import com.joffer.organizeplus.database.entities.DutyOccurrenceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DutyOccurrenceDao {
    
    @Query("SELECT * FROM duty_occurrences ORDER BY createdAt DESC")
    fun getAllDutyOccurrences(): Flow<List<DutyOccurrenceEntity>>
    
    @Query("SELECT * FROM duty_occurrences WHERE id = :id")
    suspend fun getDutyOccurrenceById(id: Long): DutyOccurrenceEntity?
    
    @Query("SELECT * FROM duty_occurrences WHERE dutyId = :dutyId ORDER BY createdAt DESC")
    fun getDutyOccurrencesByDutyId(dutyId: Long): Flow<List<DutyOccurrenceEntity>>
    
    @Query("SELECT * FROM duty_occurrences WHERE completedDate BETWEEN :startDate AND :endDate ORDER BY completedDate DESC")
    fun getDutyOccurrencesByDateRange(startDate: String, endDate: String): Flow<List<DutyOccurrenceEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDutyOccurrence(dutyOccurrence: DutyOccurrenceEntity): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDutyOccurrences(dutyOccurrences: List<DutyOccurrenceEntity>): List<Long>
    
    @Update
    suspend fun updateDutyOccurrence(dutyOccurrence: DutyOccurrenceEntity)
    
    @Delete
    suspend fun deleteDutyOccurrence(dutyOccurrence: DutyOccurrenceEntity)
    
    @Query("DELETE FROM duty_occurrences WHERE id = :id")
    suspend fun deleteDutyOccurrenceById(id: Long)
    
    @Query("DELETE FROM duty_occurrences WHERE dutyId = :dutyId")
    suspend fun deleteDutyOccurrencesByDutyId(dutyId: Long)
    
    @Query("DELETE FROM duty_occurrences")
    suspend fun deleteAllDutyOccurrences()
}
