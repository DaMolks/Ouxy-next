package com.damolks.ouxynext.core.data.dao

import androidx.room.*
import com.damolks.ouxynext.core.data.entities.EventLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventLogDao {
    @Query("SELECT * FROM event_logs ORDER BY timestamp DESC")
    fun getAllEvents(): Flow<List<EventLogEntity>>

    @Query("SELECT * FROM event_logs WHERE module_id = :moduleId ORDER BY timestamp DESC")
    fun getEventsForModule(moduleId: String): Flow<List<EventLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventLogEntity)

    @Query("DELETE FROM event_logs WHERE module_id = :moduleId")
    suspend fun clearEventsForModule(moduleId: String)

    @Query("DELETE FROM event_logs")
    suspend fun clearAllEvents()
}