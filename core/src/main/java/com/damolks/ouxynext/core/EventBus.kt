package com.damolks.ouxynext.core

import kotlinx.coroutines.flow.SharedFlow

interface EventBus {
    val events: SharedFlow<Event>
    suspend fun emit(event: Event)
}