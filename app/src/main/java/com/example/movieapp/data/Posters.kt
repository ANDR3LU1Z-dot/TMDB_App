package com.example.movieapp.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Posters(
    val file_path: String?
)
