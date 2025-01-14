package com.damolks.ouxynext.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "module_states")
data class ModuleStateEntity(
    @PrimaryKey
    @ColumnInfo(name = "module_id")
    val moduleId: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "version")
    val version: String,

    @ColumnInfo(name = "is_active")
    val isActive: Boolean,

    @ColumnInfo(name = "config")
    val config: Map<String, Any>?,

    @ColumnInfo(name = "last_updated")
    val lastUpdated: Date = Date()
)