package com.example.movieapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.movieapp.helper.ApiCredentials
import com.squareup.moshi.JsonClass


@Entity(
    tableName = "movie_details",
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
class MovieDetailsResponse() {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    var overview: String? = null
    var poster_path: String? = null
    var title: String? = null
    var vote_average: Double? = null
    @ColumnInfo(index = true)
    var movieListId: Int? = null

    fun getImageUrl() = ApiCredentials.imageUrl + poster_path

    fun getformattedVotes() = String.format("%.1f", vote_average)
}