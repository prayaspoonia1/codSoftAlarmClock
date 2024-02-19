package com.yogi.alarmclock.Activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val ringtoneUri: Uri = intent.getParcelableExtra("ringtoneUri") ?: RingtoneManager.getDefaultUri(
            RingtoneManager.TYPE_ALARM)

        // Play the alarm sound
        val mediaPlayer = MediaPlayer.create(context, ringtoneUri)
        mediaPlayer.start()

        // You can also set looping and other properties as needed
        // mediaPlayer.isLooping = true

        // You can release the media player when it's done playing if needed
        // mediaPlayer.setOnCompletionListener { mediaPlayer.release() }
    }
}
