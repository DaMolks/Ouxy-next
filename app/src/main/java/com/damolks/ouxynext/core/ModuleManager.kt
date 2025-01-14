package com.damolks.ouxynext.core

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.damolks.ouxynext.data.ModuleInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModuleManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val _installedModules = MutableLiveData<List<ModuleInfo>>()
    val installedModules: LiveData<List<ModuleInfo>> = _installedModules

    private val _moduleLoadingState = MutableLiveData<ModuleLoadingState>()
    val moduleLoadingState: LiveData<ModuleLoadingState> = _moduleLoadingState

    init {
        refreshInstalledModules()
    }

    private fun refreshInstalledModules() {
        _installedModules.value = listOf(
            ModuleInfo(
                id = "testmodule",
                name = "Module de Test",
                version = "1.0.0",
                isActive = true
            )
        )
    }

    fun loadModule(moduleId: String) {
        _moduleLoadingState.value = ModuleLoadingState.Loading(moduleId)
        
        when (moduleId) {
            "testmodule" -> {
                // Mise à jour de l'état du module
                val currentModules = _installedModules.value?.toMutableList() ?: mutableListOf()
                val moduleIndex = currentModules.indexOfFirst { it.id == moduleId }
                if (moduleIndex != -1) {
                    currentModules[moduleIndex] = currentModules[moduleIndex].copy(isActive = true)
                    _installedModules.value = currentModules
                }
                
                // Lancement de l'activité du module
                _moduleLoadingState.value = ModuleLoadingState.Success(moduleId)
                launchModuleActivity(moduleId)
            }
            else -> {
                _moduleLoadingState.value = ModuleLoadingState.Error(moduleId, "Module non reconnu")
            }
        }
    }

    private fun launchModuleActivity(moduleId: String) {
        when (moduleId) {
            "testmodule" -> {
                val intent = Intent().setClassName(
                    context.packageName,
                    "com.damolks.ouxynext.testmodule.TestModuleActivity"
                )
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        }
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