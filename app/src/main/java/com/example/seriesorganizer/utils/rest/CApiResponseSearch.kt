package com.example.seriesorganizer.utils.rest

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CApiResponseSearch (
    @field:Json(name = "keyword")
    var keyword: String,
    @field:Json(name = "pagesCount")
    var pagesCount: Int?,
    @field:Json(name = "films")
    var films: List <CFilmInfo>,
    @field:Json(name = "searchFilmsCountResult")
    var searchFilmsCountResult: Int?,
)

@JsonClass(generateAdapter = true)
data class CFilmInfo (
    @field:Json(name = "filmId")
    val filmId: Int,
    @field:Json(name = "nameRu")
    val nameRu: String?,
    @field:Json(name = "nameEn")
    val nameEn: String?,
    @field:Json(name = "type")
    val type: String?,
    @field:Json(name = "year")
    val year: String?,
    @field:Json(name = "description")
    val description: String?,
    @field:Json(name = "filmLength")
    val filmLength: String?,
    @field:Json(name = "countries")
    val countries: List<CCountries>?,
    @field:Json(name = "genres")
    val genres: List<CGenres>?,
    @field:Json(name = "rating")
    val rating: String?,
    @field:Json(name = "ratingVoteCount")
    val ratingVoteCount: Int?,
    @field:Json(name = "posterUrl")
    val posterUrl: String?,
    @field:Json(name = "posterUrlPreview")
    val posterUrlPreview: String?,
)

@JsonClass(generateAdapter = true)
data class CCountries (
    @field:Json(name = "country")
    val country: String?,
)

@JsonClass(generateAdapter = true)
data class CGenres (
    @field:Json(name = "genre")
    val genre: String?,
)
