package com.damolks.ouxynext.core.repository

import com.damolks.ouxynext.core.data.dao.EventLogDao
import com.damolks.ouxynext.core.data.entities.EventLogEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EventLogRepository @Inject constructor(
    private val eventLogDao: EventLogDao
) {
    fun getAllEvents(): Flow<List<EventLogEntity>> = eventLogDao.getAllEvents()
    
    suspend fun insertEvent(event: EventLogEntity) = eventLogDao.insertEvent(event)
    
    suspend fun clearAllEvents() = eventLogDao.clearAllEvents()
}