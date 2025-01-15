package com.damolks.ouxynext.core

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.damolks.ouxynext.core.model.ModuleInfo
import com.damolks.ouxynext.core.repository.EventLogRepository
import com.damolks.ouxynext.core.repository.ModuleStateRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModuleManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val moduleStateRepository: ModuleStateRepository,
    private val eventLogRepository: EventLogRepository
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    val installedModules: LiveData<List<ModuleInfo>> = moduleStateRepository.getAllModuleStates()
        .asLiveData(scope.coroutineContext)

    private val _moduleLoadingState = MutableLiveData<ModuleLoadingState>()
    val moduleLoadingState: LiveData<ModuleLoadingState> = _moduleLoadingState

    init {
        initializeDefaultModules()
    }

    private fun initializeDefaultModules() {
        scope.launch {
            moduleStateRepository.updateModuleState(
                moduleId = "testmodule",
                name = "Module de Test",
                version = "1.0.0",
                isActive = false
            )
        }
    }

    fun loadModule(moduleId: String) {
        _moduleLoadingState.value = ModuleLoadingState.Loading(moduleId)
        
        scope.launch {
            try {
                when (moduleId) {
                    "testmodule" -> {
                        moduleStateRepository.updateModuleState(
                            moduleId = moduleId,
                            name = "Module de Test",
                            version = "1.0.0",
                            isActive = true
                        )
                        eventLogRepository.logEvent(
                            moduleId = moduleId,
                            eventType = "MODULE_LOADED"
                        )
                        launchModuleActivity(moduleId)
                        _moduleLoadingState.value = ModuleLoadingState.Success(moduleId)
                    }
                    else -> {
                        _moduleLoadingState.value = ModuleLoadingState.Error(moduleId, "Module non reconnu")
                    }
                }
            } catch (e: Exception) {
                _moduleLoadingState.value = ModuleLoadingState.Error(moduleId, e.message ?: "Erreur inconnue")
                eventLogRepository.logEvent(
                    moduleId = moduleId,
                    eventType = "MODULE_LOAD_ERROR",
                    data = mapOf("error" to (e.message ?: "Erreur inconnue"))
                )
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
        scope.launch {
            moduleStateRepository.updateModuleState(
                moduleId = moduleId,
                name = "Module de Test",
                version = "1.0.0",
                isActive = false
            )
            eventLogRepository.logEvent(
                moduleId = moduleId,
                eventType = "MODULE_UNLOADED"
            )
        }
    }
}

sealed class ModuleLoadingState {
    data class Loading(val moduleId: String) : ModuleLoadingState()
    data class Success(val moduleId: String) : ModuleLoadingState()
    data class Error(val moduleId: String, val error: String) : ModuleLoadingState()
}