package com.damolks.ouxynext.core.data.dao

import androidx.room.*
import com.damolks.ouxynext.core.data.entities.ModuleStateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ModuleStateDao {
    @Query("SELECT * FROM module_states")
    fun getAllModuleStates(): Flow<List<ModuleStateEntity>>

    @Query("SELECT * FROM module_states WHERE module_id = :moduleId")
    fun getModuleState(moduleId: String): Flow<ModuleStateEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModuleState(state: ModuleStateEntity)

    @Delete
    suspend fun deleteModuleState(state: ModuleStateEntity)

    @Query("DELETE FROM module_states")
    suspend fun clearAllModuleStates()
}