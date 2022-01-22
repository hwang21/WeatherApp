package com.goat.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

     fun convertDate(Timestamp: Int): String {
        val timeD = Date(Timestamp.toLong() * 1000)
        val sdf = SimpleDateFormat("yyyy-MMM-dd HH:mm", Locale.US)
        return sdf.format(timeD)
    }
}