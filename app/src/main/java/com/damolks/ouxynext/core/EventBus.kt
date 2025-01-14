package com.damolks.ouxynext.core

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventBus @Inject constructor() {
    private val _events = MutableSharedFlow<Event>()
    val events = _events.asSharedFlow()

    suspend fun emit(event: Event) {
        _events.emit(event)
    }
}

sealed class Event {
    data class ModuleEvent(val moduleId: String, val type: String, val data: Map<String, Any>? = null) : Event()
    data class SystemEvent(val type: String, val data: Map<String, Any>? = null) : Event()
}