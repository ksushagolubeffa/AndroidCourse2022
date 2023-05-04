package com.example.homework10.database

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun toDate(date: Long?): Date? =
        if (date == null) null
        else Date(date)

    @TypeConverter
    fun fromDate(date: Date?): Long? =
        date?.time
}