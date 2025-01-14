package com.damolks.ouxynext.core

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.damolks.ouxynext.data.ModuleInfo
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModuleManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val splitInstallManager: SplitInstallManager = SplitInstallManagerFactory.create(context)
    
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
                isActive = false
            )
        )
    }

    fun loadModule(moduleId: String) {
        _moduleLoadingState.value = ModuleLoadingState.Loading(moduleId)
        
        when (moduleId) {
            "testmodule" -> {
                if (splitInstallManager.installedModules.contains("testmodule")) {
                    updateModuleState(moduleId, true)
                    launchModuleActivity(moduleId)
                } else {
                    val request = SplitInstallRequest.newBuilder()
                        .addModule(moduleId)
                        .build()

                    splitInstallManager.startInstall(request)
                        .addOnSuccessListener {
                            updateModuleState(moduleId, true)
                            launchModuleActivity(moduleId)
                        }
                        .addOnFailureListener { exception ->
                            _moduleLoadingState.value = ModuleLoadingState.Error(moduleId, exception.message ?: "Erreur inconnue")
                        }
                }
            }
        }
    }

    private fun updateModuleState(moduleId: String, active: Boolean) {
        val currentModules = _installedModules.value?.toMutableList() ?: mutableListOf()
        val moduleIndex = currentModules.indexOfFirst { it.id == moduleId }
        if (moduleIndex != -1) {
            currentModules[moduleIndex] = currentModules[moduleIndex].copy(isActive = active)
            _installedModules.value = currentModules
            if (active) {
                _moduleLoadingState.value = ModuleLoadingState.Success(moduleId)
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
        updateModuleState(moduleId, false)
    }
}

sealed class ModuleLoadingState {
    data class Loading(val moduleId: String) : ModuleLoadingState()
    data class Success(val moduleId: String) : ModuleLoadingState()
    data class Error(val moduleId: String, val error: String) : ModuleLoadingState()
}