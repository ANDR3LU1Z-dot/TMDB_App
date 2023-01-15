package com.example.movieapp.data

import com.example.movieapp.ApiCredentials
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Results(
    val poster_path: String?,
    val release_date: String?,
    val id: Int,
    val original_title: String?,
    val title: String?
){
    fun getImageUrl() = ApiCredentials.imageUrl + poster_path
}
