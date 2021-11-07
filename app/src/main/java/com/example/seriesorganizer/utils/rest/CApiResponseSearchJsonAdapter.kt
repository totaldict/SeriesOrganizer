package com.example.seriesorganizer.utils.rest

import com.squareup.moshi.*

import java.io.IOException


class CApiResponseSearchJsonAdapter {
    @FromJson
    fun fromJson(reader: JsonReader): CApiResponseSearch? {
        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<CApiResponseSearch> = moshi.adapter<CApiResponseSearch>(
            CApiResponseSearch::class.java
        )
        return jsonAdapter.fromJson(reader.nextString())
    }

    @ToJson
    fun toJson(writer: JsonWriter, value: CApiResponseSearch?) {
        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<CApiResponseSearch> = moshi.adapter<CApiResponseSearch>(
            CApiResponseSearch::class.java
        )
        writer.value(jsonAdapter.toJson(value))
    }
}