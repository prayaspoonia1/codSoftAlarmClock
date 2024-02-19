package com.yogi.alarmclock.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yogi.alarmclock.Adapter.AlarmAdapter
import com.yogi.alarmclock.databinding.ActivityMainBinding
import com.yogi.alarmclock.roomDatabase.AlarmDatabase
import com.yogi.alarmclock.roomDatabase.AlarmEntity
import kotlinx.coroutines.launch
import android.os.Handler
import android.os.Looper
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val alarmList = mutableListOf<AlarmEntity>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var alarmAdapter: AlarmAdapter
    private lateinit var timeHandler: Handler

    override fun onResume() {
        super.onResume()
        loadAlarms()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.alarmRecyclerView.layoutManager = LinearLayoutManager(this)
        alarmAdapter = AlarmAdapter(alarmList)
        binding.alarmRecyclerView.adapter = alarmAdapter

        binding.addAlarmButton.setOnClickListener {
            startActivity(Intent(this, SetAlarmActivity::class.java))
        }

        // Start updating time and date
        timeHandler = Handler(Looper.getMainLooper())
        updateTimeAndDate()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadAlarms() {
        lifecycleScope.launch {
            val alarmDatabase = AlarmDatabase.getDatabase(this@MainActivity)
            val alarmDao = alarmDatabase.AlarmDao()

            alarmList.clear()
            alarmList.addAll(alarmDao.getAllAlarms())
            alarmAdapter.notifyDataSetChanged()
        }
    }

    private fun updateTimeAndDate() {
        val timeRunnable = object : Runnable {
            override fun run() {
                val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                binding.currentTimeTextView.text = currentTime
                binding.currentDateTextView.text = currentDate

                // Repeat every second
                timeHandler.postDelayed(this, 1000)
            }
        }
        timeHandler.post(timeRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove callbacks to prevent memory leaks
        timeHandler.removeCallbacksAndMessages(null)
    }
}
