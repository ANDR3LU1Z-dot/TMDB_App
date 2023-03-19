package com.example.movieapp

import com.example.movieapp.data.Backdrops
import com.example.movieapp.data.Posters
import com.squareup.moshi.JsonClass
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@JsonClass(generateAdapter = true)
data class MoviePostersResponse(
    val id: Int?,
    val backdrops: List<Backdrops>?,
    val posters: List<Posters>?
){
    fun getCarouselImages(): List<CarouselItem>? = posters?.map {
        CarouselItem(imageUrl = it.getFullImagePath())
    }
}
