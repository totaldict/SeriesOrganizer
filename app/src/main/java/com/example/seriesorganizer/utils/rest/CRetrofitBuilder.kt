package com.example.seriesorganizer.utils.rest

import android.graphics.Movie
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import okhttp3.Interceptor
import okhttp3.Request
import kotlin.reflect.KParameter


class CRetrofitBuilder {
    companion object {
        private const val BASE_URL = "https://kinopoiskapiunofficial.tech/" // сюда отправляются запросы.
        private const val TOKEN = "74b74cc5-bec7-4dbc-b2a4-2f4e5889a0ba"    // токен подключения к API

        @Volatile
        private var INSTANCE: Retrofit? = null

        fun getRetrofit(): Retrofit {
            val tempInstance = CRetrofitBuilder.INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            // проверка чтобы была одна БД создана
            synchronized(this) {

                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addInterceptor(Interceptor { chain ->
                        val original: Request = chain.request()
                        val request: Request = original.newBuilder()
                            .addHeader("x-api-key", TOKEN)
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Accept", "application/json")
                            .method(original.method, original.body)
                            .build()
                        chain.proceed(request)
                    })
                    .retryOnConnectionFailure(true)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .build()

                val moshi = Moshi.Builder()
                    .build()

                val instance = Retrofit
                    .Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                    .build()


                CRetrofitBuilder.INSTANCE = instance
                return instance
            }
        }
    }
}