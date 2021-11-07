package com.example.seriesorganizer.utils.converters

import androidx.room.TypeConverter
import java.time.LocalDate
import kotlin.collections.ArrayList


class CConverters {

    @TypeConverter
    fun stringFromLocalDates(localDates: ArrayList<LocalDate>): String {
        return localDates.joinToString(System.lineSeparator())
    }

    @TypeConverter
    fun localDatesFromString(value: String): ArrayList<LocalDate> {
        val list = ArrayList<LocalDate>()

        val stringList = value.trim().split(System.lineSeparator())
        stringList.forEach {
            if (it.isEmpty()) return@forEach
            list.add(LocalDate.parse(it))
        }
        return list
    }
}