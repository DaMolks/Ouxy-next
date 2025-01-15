package com.damolks.ouxynext.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.damolks.ouxynext.core.data.entities.EventLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventLogDao {
    @Query("SELECT * FROM event_logs ORDER BY timestamp DESC")
    fun getAllEvents(): Flow<List<EventLogEntity>>
    
    @Insert
    suspend fun insertEvent(event: EventLogEntity)
    
    @Query("DELETE FROM event_logs")
    suspend fun clearAllEvents()
}