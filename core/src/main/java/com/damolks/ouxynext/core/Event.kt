package com.damolks.ouxynext.core

sealed class Event {
    data class ModuleEvent(
        val moduleId: String,
        val type: String
    ) : Event()

    data class SystemEvent(
        val type: String,
        val data: Map<String, Any>
    ) : Event()
}