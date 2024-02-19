package com.yogi.alarmclock.roomDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm_table")
data class AlarmEntity(
    @PrimaryKey
    val id: Int,
    val time: Long,
    val isEnabled: Boolean
)