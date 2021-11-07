package com.example.seriesorganizer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "tvseries")
class CTvSeries (
    @PrimaryKey
    var id: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "description")
    var description: String,
    /** Даты выхода сериала */
    @ColumnInfo(name = "episodes")
    var episodes: ArrayList<LocalDate>,
)