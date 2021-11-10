package com.example.seriesorganizer.utils.rest

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CApiResponseEpisodes (
    @field:Json(name = "total")
    var total: Int?,
    @field:Json(name = "items")
    var items : List <CSeason>?
)

@JsonClass(generateAdapter = true)
data class CSeason (
    @field:Json(name = "number")
    var number: Int?,
    @field:Json(name = "episodes")
    var episodes: List<CEpisode>?
)

@JsonClass(generateAdapter = true)
data class CEpisode (
    @field:Json(name = "seasonNumber")
    var seasonNumber: Int?,
    @field:Json(name = "episodeNumber")
    var episodeNumber: Int?,
    @field:Json(name = "nameRu")
    var nameRu: String?,
    @field:Json(name = "nameEn")
    var nameEn: String?,
    @field:Json(name = "synopsis")
    var synopsis: String?,
    @field:Json(name = "releaseDate")
    var releaseDate: String?,
)
