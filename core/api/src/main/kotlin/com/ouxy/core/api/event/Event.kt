package com.ouxy.core.api.event

interface Event {
    val type: String
    val moduleId: String
    val timestamp: Long
    val payload: Any?
}
