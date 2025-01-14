package com.damolks.ouxynext.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.damolks.ouxynext.data.ModuleInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModuleManager @Inject constructor() {
    private val _installedModules = MutableLiveData<List<ModuleInfo>>()
    val installedModules: LiveData<List<ModuleInfo>> = _installedModules

    private val _moduleLoadingState = MutableLiveData<ModuleLoadingState>()
    val moduleLoadingState: LiveData<ModuleLoadingState> = _moduleLoadingState

    init {
        refreshInstalledModules()
    }

    private fun refreshInstalledModules() {
        // Pour le PoC, on simule des modules installés
        _installedModules.value = listOf(
            ModuleInfo(
                id = "test_module",
                name = "Module de Test",
                version = "1.0.0",
                isActive = true
            )
        )
    }

    fun loadModule(moduleId: String) {
        _moduleLoadingState.value = ModuleLoadingState.Loading(moduleId)
        val currentModules = _installedModules.value?.toMutableList() ?: mutableListOf()
        val moduleIndex = currentModules.indexOfFirst { it.id == moduleId }
        if (moduleIndex != -1) {
            currentModules[moduleIndex] = currentModules[moduleIndex].copy(isActive = true)
            _installedModules.value = currentModules
        }
        _moduleLoadingState.value = ModuleLoadingState.Success(moduleId)
    }

    fun unloadModule(moduleId: String) {
        val currentModules = _installedModules.value?.toMutableList() ?: mutableListOf()
        val moduleIndex = currentModules.indexOfFirst { it.id == moduleId }
        if (moduleIndex != -1) {
            currentModules[moduleIndex] = currentModules[moduleIndex].copy(isActive = false)
            _installedModules.value = currentModules
        }
    }
}

sealed class ModuleLoadingState {
    data class Loading(val moduleId: String) : ModuleLoadingState()
    data class Success(val moduleId: String) : ModuleLoadingState()
    data class Error(val moduleId: String, val error: String) : ModuleLoadingState()
}