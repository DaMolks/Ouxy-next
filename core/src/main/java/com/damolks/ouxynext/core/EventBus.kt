package com.damolks.ouxynext.core

import kotlinx.coroutines.flow.SharedFlow

abstract class EventBus {
    abstract val events: SharedFlow<Event>
    abstract suspend fun emit(event: Event)
}