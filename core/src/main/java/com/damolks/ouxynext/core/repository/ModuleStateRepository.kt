package com.damolks.ouxynext.core.repository

import com.damolks.ouxynext.core.data.dao.ModuleStateDao
import com.damolks.ouxynext.core.data.entities.ModuleStateEntity
import com.damolks.ouxynext.core.model.ModuleInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModuleStateRepository @Inject constructor(
    private val moduleStateDao: ModuleStateDao
) {
    fun getAllModuleStates(): Flow<List<ModuleInfo>> =
        moduleStateDao.getAllModuleStates().map { entities ->
            entities.map { it.toModuleInfo() }
        }

    fun getModuleState(moduleId: String): Flow<ModuleInfo?> =
        moduleStateDao.getModuleState(moduleId).map { entity ->
            entity?.toModuleInfo()
        }

    suspend fun updateModuleState(
        moduleId: String,
        name: String,
        version: String,
        isActive: Boolean,
        config: Map<String, Any>? = null
    ) {
        val state = ModuleStateEntity(
            moduleId = moduleId,
            name = name,
            version = version,
            isActive = isActive,
            config = config
        )
        moduleStateDao.insertModuleState(state)
    }

    suspend fun deleteModuleState(moduleId: String, name: String, version: String) {
        val state = ModuleStateEntity(
            moduleId = moduleId,
            name = name,
            version = version,
            isActive = false,
            config = null
        )
        moduleStateDao.deleteModuleState(state)
    }

    suspend fun clearAllModuleStates() {
        moduleStateDao.clearAllModuleStates()
    }

    private fun ModuleStateEntity.toModuleInfo() = ModuleInfo(
        id = moduleId,
        name = name,
        version = version,
        isActive = isActive
    )
}