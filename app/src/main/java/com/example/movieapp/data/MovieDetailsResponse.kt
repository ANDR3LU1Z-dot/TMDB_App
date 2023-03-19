package com.example.movieapp.data

import com.example.movieapp.helper.ApiCredentials
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailsResponse (
    val overview: String?,
    val poster_path: String?,
    val title: String?,
    val vote_average: Double?
){
    fun getImageUrl() = ApiCredentials.imageUrl + poster_path

    val getformattedVotes = String.format("%.1f", vote_average)
}