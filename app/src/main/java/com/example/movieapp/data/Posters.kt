package com.example.movieapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.movieapp.MoviePostersResponse
import com.example.movieapp.helper.ApiCredentials.Companion.imageUrl
import com.squareup.moshi.JsonClass

@Entity(
    foreignKeys = [
            ForeignKey(
                entity = MoviePostersResponse::class,
                parentColumns = ["id"],
                childColumns = ["moviePosterId"],
                onDelete = ForeignKey.CASCADE
            )
    ]
)
@JsonClass(generateAdapter = true)
data class Posters(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val file_path: String?,
    @ColumnInfo(index = true)
    var moviePosterId: Int?
){
    fun getFullImagePath(): String{
    return imageUrl + file_path
    }
}
