package com.example.movieapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.movieapp.data.Backdrops
import com.example.movieapp.data.Posters
import com.example.movieapp.data.Results
import com.squareup.moshi.JsonClass
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@Entity(
    tableName = "movie_posters",
    foreignKeys = [
        ForeignKey(
            entity = Results::class,
            parentColumns = ["id"],
            childColumns = ["movieListId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@JsonClass(generateAdapter = true)
class MoviePostersResponse(){
    @PrimaryKey
    var id: Int? = null
    @Ignore
    var backdrops: List<Backdrops>? = null
    @Ignore
    var posters: List<Posters>? = null
    @ColumnInfo(index = true)
    var movieListId: Int? = null

    @Ignore
    constructor(
        id: Int? = null,
        backdrops: List<Backdrops>? = null,
        posters: List<Posters>? = null,
        movieListId: Int? = null
    ) : this() {
        this.id = id
        this.backdrops = backdrops
        this.posters = posters
        this.movieListId = movieListId
    }

    fun getCarouselImages(): List<CarouselItem>? = posters?.map {
        CarouselItem(imageUrl = it.getFullImagePath())
    }
}
