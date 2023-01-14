package com.example.movieapp.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailsResponse (
    val original_title: String?,
    val overview: String?,
    val poster_path: String?,
    val title: String?,
    val vote_average: Int?
)