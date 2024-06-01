package com.example.movieapp.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.movieapp.data.MovieDetailsResponse

@Dao
interface MovieDetailsDao: BaseDao<MovieDetailsResponse> {

    @Transaction
    @Query("SELECT * FROM movie_details WHERE movieListId= :movieListId")
    suspend fun getMovieDetails(movieListId: Int): MovieDetailsResponse?

    @Transaction
    suspend fun insertMovieDetails(movieDetails: MovieDetailsResponse){
        insert(movieDetails)
    }

    @Transaction
    @Query("DELETE FROM movie_details")
    suspend fun clearMovieDetailsData()
}