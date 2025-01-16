package com.ouxy.core.events

import com.ouxy.core.api.event.Event
import com.ouxy.core.api.event.EventBus
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.CoroutineScope

class OuxyEventBus(
    private val scope: CoroutineScope
) : EventBus {

    private val eventFlow = MutableSharedFlow<Event>(
        replay = 0,
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override suspend fun publish(event: Event) {
        eventFlow.emit(event)
    }

    override suspend fun subscribe(eventType: String, handler: suspend (Event) -> Unit) {
        eventFlow
            .filter { it.type == eventType }
            .onEach { handler(it) }
            .launchIn(scope)
    }

    override suspend fun unsubscribe(eventType: String, handler: suspend (Event) -> Unit) {
        // Dans cette implémentation basique, nous ne gérons pas encore le unsubscribe
        // Cela sera géré dans une version future avec un système de tracking des subscriptions
    }
}
