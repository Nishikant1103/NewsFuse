package com.example.newsfuse.core.utility

import android.util.Log
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class TimeFormatter {

    fun getFormattedTime(inputFormat: String, outputFormat: String, time: String): String? {
        try {
            // 1. Parse the incoming date
            val inputFormatter: DateTimeFormatter? = DateTimeFormatter.ofPattern(inputFormat)
            val zonedDateTime: ZonedDateTime? = ZonedDateTime.parse(time, inputFormatter)

            // 2. Convert to device local timezone
            val localDateTime: ZonedDateTime? =
                zonedDateTime?.withZoneSameInstant(ZoneId.systemDefault())

            // 3. Format output
            val outputFormatter: DateTimeFormatter? = DateTimeFormatter.ofPattern(outputFormat)
            val formatted = localDateTime?.format(outputFormatter)

            return formatted
        } catch (e: Exception) {
            Log.e("TimeFormatter::class", "Error formatting time: ${e.message}")
            return null
        }
    }
}