package com.yogi.alarmclock.roomDatabase

import android.net.Uri
import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromUri(uri: Uri): String {
        return uri.toString()
    }

    @TypeConverter
    fun toUri(uriString: String): Uri {
        return Uri.parse(uriString)
    }
}