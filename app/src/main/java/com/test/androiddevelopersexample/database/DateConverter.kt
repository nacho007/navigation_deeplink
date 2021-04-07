package com.test.androiddevelopersexample.database

import androidx.room.TypeConverter
import java.util.*

/**
 * Created by ignaciodeandreisdenis on 7/20/20.
 */
class DateConverter {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}
