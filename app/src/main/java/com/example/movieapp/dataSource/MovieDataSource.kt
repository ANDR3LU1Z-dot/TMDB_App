package com.example.movieapp.dataSource

import com.example.movieapp.data.MovieDetailsResponse
import com.example.movieapp.data.MoviePostersResponse
import com.example.movieapp.data.MoviePostersWithAllProperties
import com.example.movieapp.data.Results

interface MovieDataSource {

    suspend fun getMovieListData(): List<Results>?

    suspend fun getMovieDetailsData(id: Int): MovieDetailsResponse?

    suspend fun getMoviePostersData(id: Int): MoviePostersResponse?

    suspend fun persistMovieListData(movieList: List<Results>)

    suspend fun persistMovieDetailsData(movieDetails: MovieDetailsResponse, movieId: Int)

    suspend fun persistMoviePostersData(moviePosters: MoviePostersResponse, movieId: Int)

    suspend fun getLocalMovieListData(): List<Results>?

    suspend fun getLocalMovieDetailsData(movieId: Int): MovieDetailsResponse?

    suspend fun getLocalMoviePostersData(movieId: Int): MoviePostersResponse?

    fun mapMoviePostersWithAllPropertiesToMoviePosters(moviePostersWithAllProperties: MoviePostersWithAllProperties): MoviePostersResponse

    suspend fun clearMovieListData()

}