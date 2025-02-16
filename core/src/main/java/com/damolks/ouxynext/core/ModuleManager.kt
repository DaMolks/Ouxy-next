package com.damolks.ouxynext.core

import androidx.lifecycle.LiveData
import com.damolks.ouxynext.core.model.ModuleInfo

interface ModuleManager {
    val events: LiveData<List<ModuleInfo>>
    fun loadModule(moduleId: String)
    fun unloadModule(moduleId: String)
}