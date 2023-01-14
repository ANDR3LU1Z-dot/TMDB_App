package com.example.movieapp.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieListResponse(
    val page: Int?,
    val results: List<Results>?
)