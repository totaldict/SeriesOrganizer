package com.example.seriesorganizer.utils.rest

import android.graphics.Movie
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class CRetrofitBuilder {
    companion object {
        const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/" // сюда отправляются запросы.

        @Volatile
        private var INSTANCE: Retrofit? = null

        fun getRetrofit(): Retrofit {
            val tempInstance = CRetrofitBuilder.INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            // проверка чтобы была одна БД создана
            synchronized(this) {

                val moshi = Moshi.Builder()
//                    .add(CConverterLocalDateTime())
//                    .add(CConverterUUID())
//                    .add(KotlinJsonAdapterFactory())
//                    .add(CApiResponseSearchJsonAdapter())
                    .build()

                val instance = Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                    .build()

                CRetrofitBuilder.INSTANCE = instance
                return instance
            }
        }
    }
}