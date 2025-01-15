package com.damolks.ouxynext.core.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.damolks.ouxynext.core.ModuleManager
import com.damolks.ouxynext.core.model.ModuleInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultModuleManager @Inject constructor() : ModuleManager {
    private val _installedModules = MutableLiveData<List<ModuleInfo>>(emptyList())
    override val installedModules: LiveData<List<ModuleInfo>> = _installedModules

    override fun loadModule(moduleId: String) {
        // TODO: Implémenter le chargement réel du module
        val modules = _installedModules.value?.map { 
            if (it.id == moduleId) it.copy(isActive = true) else it 
        }
        _installedModules.value = modules
    }

    override fun unloadModule(moduleId: String) {
        // TODO: Implémenter le déchargement réel du module
        val modules = _installedModules.value?.map { 
            if (it.id == moduleId) it.copy(isActive = false) else it 
        }
        _installedModules.value = modules
    }
}