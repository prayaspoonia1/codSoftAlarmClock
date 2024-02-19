package com.yogi.alarmclock.roomDatabase

import androidx.room.*

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarm_table")
    suspend fun getAllAlarms(): List<AlarmEntity>

    @Insert
    suspend fun insertAlarm(alarm: AlarmEntity)

    @Update
    suspend fun updateAlarm(alarm: AlarmEntity)

    @Delete
    suspend fun deleteAlarm(alarm: AlarmEntity)
}