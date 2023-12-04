package com.example.movieapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface BaseDao<T> {

    @Insert
    suspend fun insert(entity: T)

    @Insert
    suspend fun insertList(entities: List<T>)

    @Update
    suspend fun update(entity: T)

    @Update
    suspend fun updateList(entities: List<T>)

    @Delete
    suspend fun deleteEntity(entity: T)

    @Delete
    suspend fun deleteList(entities: List<T>)
}