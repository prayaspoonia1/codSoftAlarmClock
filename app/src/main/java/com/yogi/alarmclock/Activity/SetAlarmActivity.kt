package com.yogi.alarmclock.Activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.yogi.alarmclock.databinding.ActivitySetAlarmBinding
import com.yogi.alarmclock.roomDatabase.AlarmDatabase
import com.yogi.alarmclock.roomDatabase.AlarmEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class SetAlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySetAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.timePicker.setIs24HourView(true)

        binding.saveButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, binding.timePicker.hour)
            calendar.set(Calendar.MINUTE, binding.timePicker.minute)

            val uniqueInt = UUID.randomUUID().hashCode()

            val alarm = AlarmEntity(
                uniqueInt,
                calendar.timeInMillis,
                true
            )

            // Save the alarm entity to Room database
            saveAlarmToDatabase(alarm)

            // Schedule the alarm
            scheduleAlarm(this, alarm)

            finish()
        }
    }


    private fun saveAlarmToDatabase(alarm: AlarmEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            AlarmDatabase.getDatabase(this@SetAlarmActivity).AlarmDao().insertAlarm(alarm)

        }
    }

    private fun scheduleAlarm(context: Context, alarm: AlarmEntity) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarm.id,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
       // Log.d("alarm",alarm.time.toString())
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            alarm.time,
            pendingIntent
        )
    }
}
