package com.damolks.ouxynext.impl

import com.damolks.ouxynext.core.Event
import com.damolks.ouxynext.core.EventBus
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@Singleton
class DefaultEventBus @Inject constructor() : EventBus {
    private val _events = MutableSharedFlow<Event>(replay = 0, extraBufferCapacity = 64)

    override val events: SharedFlow<Event>
        get() = _events.asSharedFlow()

    override suspend fun emit(event: Event) {
        _events.emit(event)
    }
}