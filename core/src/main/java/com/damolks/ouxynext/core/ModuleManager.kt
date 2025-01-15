package com.damolks.ouxynext.core

import androidx.lifecycle.LiveData
import com.damolks.ouxynext.core.model.ModuleInfo

abstract class ModuleManager {
    abstract val installedModules: LiveData<List<ModuleInfo>>
    abstract fun loadModule(moduleId: String)
    abstract fun unloadModule(moduleId: String)
}