package com.example.movieapp.api

import com.example.movieapp.MoviePostersResponse
import com.example.movieapp.data.MovieDetailsResponse
import com.example.movieapp.data.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/now_playing")
    suspend fun getMovieList(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): Response<MovieListResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
    ): Response<MovieDetailsResponse>

    @GET("movie/{movie_id}/images")
    suspend fun getMoviePosters(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("include_image_language") include_image_language: String
    ): Response<MoviePostersResponse>
}