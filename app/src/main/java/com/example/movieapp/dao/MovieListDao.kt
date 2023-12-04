package com.example.movieapp.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.movieapp.data.Results

@Dao
interface ResultDao: BaseDao<Results> {

    @Transaction
    @Query("SELECT * FROM results")
    suspend fun getAllMovies(): List<Results>

    @Transaction
    @Query("SELECT * FROM results WHERE id=:id")
    suspend fun getMovie(id: Int): Results

    @Transaction
    suspend fun insertMovieList(movieList: List<Results>){
        movieList.forEach { insertMovie(it) }
    }

    @Transaction
    suspend fun insertMovie(results: Results){
        insert(results)
    }

    @Transaction
    @Query("DELETE FROM results")
    abstract suspend fun clearMovieListData()
}