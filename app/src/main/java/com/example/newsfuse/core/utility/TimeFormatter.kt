package com.example.newsfuse.core.utility

import android.util.Log
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class TimeFormatter {

    fun getFormattedTime(inputFormat: List<String>, outputFormat: String, time: String): String? {
        try {
            val zonedLocalDateTime = inputFormat.firstNotNullOf { inputFormat ->
                ZonedDateTime.parse(time, DateTimeFormatter.ofPattern(inputFormat))
            }.withZoneSameInstant(ZoneId.systemDefault())

            val outputFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(outputFormat)
            val formatted = zonedLocalDateTime.format(outputFormatter)

            return formatted
        } catch (e: Exception) {
            Log.e("TimeFormatter::class", "Error formatting time: ${e.message}")
            return null
        }
    }
}