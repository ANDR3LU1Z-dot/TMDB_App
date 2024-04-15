package com.example.movieapp.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.movieapp.data.Posters

@Dao
interface PostersDao: BaseDao<Posters> {

    @Query("SELECT * FROM posters")
    suspend fun getAllPosters(): List<Posters>?

    @Query("SELECT * FROM posters WHERE id = :id")
    suspend fun getPoster(id: Int): Posters?

}