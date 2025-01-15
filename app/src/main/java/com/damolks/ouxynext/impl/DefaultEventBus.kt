package com.damolks.ouxynext.impl

import com.damolks.ouxynext.core.Event
import com.damolks.ouxynext.core.EventBus
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultEventBus @Inject constructor() : EventBus {
    private val _events = MutableSharedFlow<Event>()
    override val events: SharedFlow<Event> = _events.asSharedFlow()

    override suspend fun emit(event: Event) {
        _events.emit(event)
    }
}