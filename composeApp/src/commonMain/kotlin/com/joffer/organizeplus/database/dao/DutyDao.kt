package com.joffer.organizeplus.database.dao

import androidx.room.*
import com.joffer.organizeplus.database.entities.DutyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DutyDao {
    
    @Query("SELECT * FROM duties ORDER BY createdAt DESC")
    fun getAllDuties(): Flow<List<DutyEntity>>
    
    @Query("SELECT * FROM duties WHERE id = :id")
    suspend fun getDutyById(id: Long): DutyEntity?
    
    @Query("SELECT * FROM duties WHERE isCompleted = :isCompleted ORDER BY createdAt DESC")
    fun getDutiesByCompletionStatus(isCompleted: Boolean): Flow<List<DutyEntity>>
    
    @Query("SELECT * FROM duties WHERE dueDate BETWEEN :startDate AND :endDate ORDER BY dueDate ASC")
    fun getDutiesByDateRange(startDate: Long, endDate: Long): Flow<List<DutyEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDuty(duty: DutyEntity): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDuties(duties: List<DutyEntity>): List<Long>
    
    @Update
    suspend fun updateDuty(duty: DutyEntity)
    
    @Delete
    suspend fun deleteDuty(duty: DutyEntity)
    
    @Query("DELETE FROM duties WHERE id = :id")
    suspend fun deleteDutyById(id: Long)
    
    @Query("DELETE FROM duties")
    suspend fun deleteAllDuties()
}
