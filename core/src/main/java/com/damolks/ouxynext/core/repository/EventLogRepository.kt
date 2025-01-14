package com.damolks.ouxynext.core.repository

import com.damolks.ouxynext.core.data.dao.EventLogDao
import com.damolks.ouxynext.core.data.entities.EventLogEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventLogRepository @Inject constructor(
    private val eventLogDao: EventLogDao
) {
    fun getAllEvents(): Flow<List<EventLogEntity>> = eventLogDao.getAllEvents()

    fun getEventsForModule(moduleId: String): Flow<List<EventLogEntity>> =
        eventLogDao.getEventsForModule(moduleId)

    suspend fun logEvent(moduleId: String, eventType: String, data: Map<String, Any>? = null) {
        val event = EventLogEntity(
            moduleId = moduleId,
            eventType = eventType,
            data = data
        )
        eventLogDao.insertEvent(event)
    }

    suspend fun clearEventsForModule(moduleId: String) {
        eventLogDao.clearEventsForModule(moduleId)
    }

    suspend fun clearAllEvents() {
        eventLogDao.clearAllEvents()
    }
}