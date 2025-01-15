package com.damolks.ouxynext.core.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_logs")
data class EventLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long,
    val type: String,
    val data: String
)