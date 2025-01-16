package com.ouxy.test.module

import com.ouxy.core.api.module.OuxyModule
import com.ouxy.core.api.event.Event
import com.ouxy.core.api.event.EventBus

class TestModule(
    private val eventBus: EventBus
) : OuxyModule {
    
    override val id: String = "test-module"
    override val version: String = "1.0.0"
    override val requiredPermissions: Set<String> = emptySet()

    private var isInitialized = false
    private var isRunning = false

    override suspend fun initialize() {
        if (!isInitialized) {
            eventBus.publish(TestEvent("Module initialization started"))
            // Simulation d'une initialisation
            isInitialized = true
            eventBus.publish(TestEvent("Module initialization completed"))
        }
    }

    override suspend fun start() {
        if (!isRunning && isInitialized) {
            eventBus.publish(TestEvent("Module starting"))
            isRunning = true
            eventBus.publish(TestEvent("Module started"))
        }
    }

    override suspend fun stop() {
        if (isRunning) {
            eventBus.publish(TestEvent("Module stopping"))
            isRunning = false
            eventBus.publish(TestEvent("Module stopped"))
        }
    }

    override suspend fun cleanup() {
        eventBus.publish(TestEvent("Module cleanup"))
        isInitialized = false
        isRunning = false
    }

    private inner class TestEvent(message: String) : Event {
        override val type: String = "test.event"
        override val moduleId: String = id
        override val timestamp: Long = System.currentTimeMillis()
        override val payload: Any? = message
    }
}
