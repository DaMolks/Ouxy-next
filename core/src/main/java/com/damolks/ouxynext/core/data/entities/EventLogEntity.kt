package com.damolks.ouxynext.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "event_logs")
data class EventLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "module_id")
    val moduleId: String,

    @ColumnInfo(name = "event_type")
    val eventType: String,

    @ColumnInfo(name = "data")
    val data: Map<String, Any>?,

    @ColumnInfo(name = "timestamp")
    val timestamp: Date = Date()
)