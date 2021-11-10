package com.example.seriesorganizer.utils.rest

import retrofit2.http.*
import java.util.*

interface IServerAPITemplate {

//    https://kinopoiskapiunofficial.tech/
//    Для доступа, запрос должен содержать header
//    name: X-API-KEY
//    value: 74b74cc5-bec7-4dbc-b2a4-2f4e5889a0ba
//    Поиск по ключевым словам:
//    https://kinopoiskapiunofficial.tech/api/v2.1/films/search-by-keyword?keyword=Hello&page=1
//    Инфо по ID:
//    https://kinopoiskapiunofficial.tech/api/v2.2/films/464963
//    Серии сериала:
//    https://kinopoiskapiunofficial.tech/api/v2.2/films/464963/seasons

    @GET("/api/v2.1/films/search-by-keyword")
    suspend fun getSearchByKeyword(@Query(value="keyword") keyword: String, @Query(value="page") page: Int): CApiResponseSearch

    @GET("/api/v2.2/films/{id}/seasons")
    suspend fun getSeasons(@Path("id") id: Int): CApiResponseEpisodes

}