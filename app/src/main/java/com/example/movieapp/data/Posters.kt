package com.example.movieapp.data

import com.example.movieapp.helper.ApiCredentials.Companion.imageUrl
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Posters(
    val file_path: String?
){
    fun getFullImagePath(): String{
    return imageUrl + file_path
    }
}
