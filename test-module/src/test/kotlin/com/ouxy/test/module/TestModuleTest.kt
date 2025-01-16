package com.ouxy.test.module

import com.ouxy.core.api.event.Event
import com.ouxy.core.api.event.EventBus
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TestModuleTest {
    private lateinit var eventBus: EventBus
    private lateinit var module: TestModule

    @Before
    fun setup() {
        eventBus = mockk(relaxed = true)
        module = TestModule(eventBus)
    }

    @Test
    fun `module lifecycle publishes correct events`() = runTest {
        // Test initialization
        module.initialize()
        coVerify { 
            eventBus.publish(match<Event> { 
                it.payload == "Module initialization started" 
            })
            eventBus.publish(match<Event> { 
                it.payload == "Module initialization completed" 
            })
        }

        // Test start
        module.start()
        coVerify { 
            eventBus.publish(match<Event> { 
                it.payload == "Module starting" 
            })
            eventBus.publish(match<Event> { 
                it.payload == "Module started" 
            })
        }

        // Test stop
        module.stop()
        coVerify { 
            eventBus.publish(match<Event> { 
                it.payload == "Module stopping" 
            })
            eventBus.publish(match<Event> { 
                it.payload == "Module stopped" 
            })
        }

        // Test cleanup
        module.cleanup()
        coVerify { 
            eventBus.publish(match<Event> { 
                it.payload == "Module cleanup" 
            })
        }
    }
}
