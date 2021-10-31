package com.example.seriesorganizer.dao

import androidx.room.*
import com.example.seriesorganizer.model.CTvSeries
import java.util.*

@Dao
interface IDAOTvSeries {
    @Query("SELECT * FROM tvseries")
    suspend fun getAll(): List<CTvSeries>

    @Query("SELECT * FROM tvseries WHERE id = :ident")
    suspend fun findById(ident: Int): CTvSeries?

    @Insert
    suspend fun insert(lesson: CTvSeries)

    @Update
    suspend fun update(lesson: CTvSeries)

    @Delete
    suspend fun delete(lesson: CTvSeries)
}