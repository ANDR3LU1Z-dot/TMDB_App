package com.example.movieapp

import com.example.movieapp.data.MovieDetailsResponse
import com.example.movieapp.data.MovieListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/now_playing")
    fun getMovieList(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): Call<MovieListResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
    ): Call<MovieDetailsResponse>

    @GET("movie/{movie_id}/images")
    fun getMoviePosters(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("include_image_language") include_image_language: String
    ): Call<MoviePostersResponse>
}