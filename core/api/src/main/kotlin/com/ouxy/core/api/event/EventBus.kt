package com.ouxy.core.api.event

interface EventBus {
    suspend fun publish(event: Event)
    suspend fun subscribe(eventType: String, handler: suspend (Event) -> Unit)
    suspend fun unsubscribe(eventType: String, handler: suspend (Event) -> Unit)
}
