package com.joffer.organizeplus.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.joffer.organizeplus.database.entities.DutyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DutyDao {

    @Query("SELECT * FROM duties ORDER BY createdAt DESC")
    fun getAllDuties(): Flow<List<DutyEntity>>

    @Query("SELECT * FROM duties WHERE id = :id")
    suspend fun getDutyById(id: Long): DutyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDuty(duty: DutyEntity)

    @Update
    suspend fun updateDuty(duty: DutyEntity)

    @Delete
    suspend fun deleteDuty(duty: DutyEntity)

    @Query("DELETE FROM duties WHERE id = :id")
    suspend fun deleteDutyById(id: Long)

    @Query("SELECT * FROM duties ORDER BY createdAt DESC LIMIT :limit")
    fun getLatestDuties(limit: Int): Flow<List<DutyEntity>>

    @Query("SELECT * FROM duties ORDER BY createdAt DESC")
    fun getUpcomingDuties(): Flow<List<DutyEntity>>
}
