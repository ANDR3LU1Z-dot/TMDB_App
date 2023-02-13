package com.example.movieapp.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Backdrops(
    val file_path: String?
)
