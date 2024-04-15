package com.example.movieapp.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.movieapp.MoviePostersResponse
import com.example.movieapp.data.MoviePostersWithAllProperties
import com.example.movieapp.database.MoviesDatabase

@Dao
abstract class MoviePostersDao(moviesDatabase: MoviesDatabase): BaseDao<MoviePostersResponse> {

    private val postersDao = moviesDatabase.posterDao()

    @Transaction
    @Query("SELECT * FROM movie_posters WHERE movieListId = :movieListId")
    abstract suspend fun getMoviePosters(movieListId: Int): MoviePostersWithAllProperties?

    @Transaction
    open suspend fun insertMoviePosters(moviePosters: MoviePostersResponse){
        moviePosters.posters?.forEach {
            it.moviePosterId = moviePosters.id
            Log.d("InsertMoviePosters", "moviePosterId = ${it.moviePosterId}, movie-postersResponse: ${moviePosters.id}")
        }

        insert(moviePosters)
        moviePosters.posters?.let { postersDao.insertList(it) }
    }

    @Transaction
    @Query("DELETE FROM movie_posters")
    abstract suspend fun clearMoviePostersData()
}