package com.example.seriesorganizer.model

class CTvSeries (
    var id: Int,
    var name: String,
    var info: String,
    /** Список серий сериала, пока строкой */
    var episodes: String,
)