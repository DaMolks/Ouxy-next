package com.ouxy.core.api.module

import org.junit.Test
import kotlin.test.assertTrue

class ModuleStateTest {
    @Test
    fun `verify ModuleState sealed class contains all required states`() {
        val states = ModuleState::class.sealedSubclasses
        
        assertTrue(states.any { it == ModuleState.NotInitialized::class })
        assertTrue(states.any { it == ModuleState.Initializing::class })
        assertTrue(states.any { it == ModuleState.Running::class })
        assertTrue(states.any { it == ModuleState.Stopped::class })
        assertTrue(states.any { it == ModuleState.Error::class })
    }
}
