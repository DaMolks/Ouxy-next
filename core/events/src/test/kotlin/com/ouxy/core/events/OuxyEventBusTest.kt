package com.ouxy.core.events

import com.ouxy.core.api.event.Event
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.TestScope
import org.junit.Test
import kotlin.test.assertEquals

class OuxyEventBusTest {
    
    @Test
    fun `when event is published, subscribers receive it`() = runTest {
        // Given
        val eventBus = OuxyEventBus(TestScope())
        var receivedPayload: String? = null
        
        // When
        eventBus.subscribe("test.event") { event ->
            receivedPayload = event.payload as String
        }
        
        val testEvent = object : Event {
            override val type: String = "test.event"
            override val moduleId: String = "test"
            override val timestamp: Long = System.currentTimeMillis()
            override val payload: Any = "test payload"
        }
        
        eventBus.publish(testEvent)
        
        // Then
        assertEquals("test payload", receivedPayload)
    }
    
    @Test
    fun `subscribers only receive events they subscribed to`() = runTest {
        // Given
        val eventBus = OuxyEventBus(TestScope())
        var receivedCount = 0
        
        // When
        eventBus.subscribe("type.a") { receivedCount++ }
        
        val eventA = object : Event {
            override val type: String = "type.a"
            override val moduleId: String = "test"
            override val timestamp: Long = System.currentTimeMillis()
            override val payload: Any? = null
        }
        
        val eventB = object : Event {
            override val type: String = "type.b"
            override val moduleId: String = "test"
            override val timestamp: Long = System.currentTimeMillis()
            override val payload: Any? = null
        }
        
        eventBus.publish(eventA)
        eventBus.publish(eventB)
        
        // Then
        assertEquals(1, receivedCount)
    }
}
