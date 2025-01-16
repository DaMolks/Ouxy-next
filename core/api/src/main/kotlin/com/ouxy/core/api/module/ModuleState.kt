package com.ouxy.core.api.module

sealed class ModuleState {
    object NotInitialized : ModuleState()
    object Initializing : ModuleState()
    object Running : ModuleState()
    object Stopped : ModuleState()
    data class Error(val error: Throwable) : ModuleState()
}
