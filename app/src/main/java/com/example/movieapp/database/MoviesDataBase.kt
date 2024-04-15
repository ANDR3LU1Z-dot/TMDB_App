package com.example.movieapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapp.MoviePostersResponse
import com.example.movieapp.dao.MovieDetailsDao
import com.example.movieapp.dao.MoviePostersDao
import com.example.movieapp.dao.PostersDao
import com.example.movieapp.dao.ResultDao
import com.example.movieapp.data.MovieDetailsResponse
import com.example.movieapp.data.Posters
import com.example.movieapp.data.Results

@Database(
    entities = [Results::class, MovieDetailsResponse::class, MoviePostersResponse::class, Posters::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesDatabase: RoomDatabase() {

    abstract fun resultDao(moviesDatabase: MoviesDatabase): ResultDao
    abstract fun movieDetailsDao(): MovieDetailsDao
    abstract fun moviePostersDao(moviesDatabase: MoviesDatabase): MoviePostersDao
    abstract fun posterDao(): PostersDao

    companion object{
        @Volatile
        private var instance: MoviesDatabase? = null

        fun getDatabase(context: Context): MoviesDatabase {
            return instance ?: synchronized(this) {
                val database = Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDatabase::class.java,
                    "movie_data_base"
                ).build()
                this.instance = database
                return database
            }
        }
    }
}