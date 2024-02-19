package com.yogi.alarmclock.roomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [AlarmEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun AlarmDao(): AlarmDao

    // Consider making it a Singleton (using a companion object)
    companion object{

        private var INSTANCE:AlarmDatabase? = null

        fun getDatabase(context: Context):AlarmDatabase{
            INSTANCE?.let {
                return it
            }
            return synchronized(AlarmDatabase::class.java){
                val instance=
                    Room.databaseBuilder(context.applicationContext,AlarmDatabase::class.java,"your_database_name").fallbackToDestructiveMigration() .build();

                INSTANCE=instance
                instance
            }

        }

    }
}