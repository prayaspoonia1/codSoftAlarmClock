package com.yogi.alarmclock.Adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.yogi.alarmclock.R
import com.yogi.alarmclock.roomDatabase.AlarmEntity
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class AlarmAdapter(private var alarmList: MutableList<AlarmEntity>) :
    RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    @RequiresApi(Build.VERSION_CODES.O)
    private val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a") // For formatting time

    // Provide a reference to the views for each alarm item
    class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeTextView: TextView = itemView.findViewById(R.id.alarmTimeTextView) // Assuming in alarm_item.xml
        val alarmSwitch: Switch = itemView.findViewById(R.id.alarmEnabledSwitch)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val alarmItemView = layoutInflater.inflate(R.layout.alarm_item, parent, false)
        return AlarmViewHolder(alarmItemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val currentAlarm = alarmList[position]

        val localDateTime = Instant.ofEpochMilli(currentAlarm.time)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
        holder.timeTextView.text = localDateTime.format(timeFormatter)
        holder.alarmSwitch.isChecked = currentAlarm.isEnabled

        // Add OnCheckedChangeListener to the switch to update in the database
        holder.alarmSwitch.setOnCheckedChangeListener { _, isChecked ->
            //  Update isEnabled status of the currentAlarm entity in the database
        }
    }

    override fun getItemCount() = alarmList.size

    // Function to update the data
    fun updateAlarmList(updatedList: List<AlarmEntity>) {
        alarmList = updatedList as MutableList<AlarmEntity>
        notifyDataSetChanged()
    }
}
