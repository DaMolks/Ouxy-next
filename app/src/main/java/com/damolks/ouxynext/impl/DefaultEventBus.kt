package com.damolks.ouxynext.impl

import com.damolks.ouxynext.core.Event
import com.damolks.ouxynext.core.EventBus
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultEventBus @Inject constructor() : EventBus {
    private val _events = MutableSharedFlow<Event>(replay = 0, extraBufferCapacity = 64)
    override val events: SharedFlow<Event> = _events

    override suspend fun emit(event: Event) {
        _events.emit(event)
    }
}