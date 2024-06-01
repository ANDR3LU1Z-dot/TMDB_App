package com.example.movieapp.repository

import android.content.Context
import android.util.Log
import com.example.movieapp.api.MovieService
import com.example.movieapp.data.MovieDetailsResponse
import com.example.movieapp.data.MoviePostersResponse
import com.example.movieapp.data.MoviePostersWithAllProperties
import com.example.movieapp.data.Results
import com.example.movieapp.dataSource.MovieDataSource
import com.example.movieapp.database.MoviesDatabase
import com.example.movieapp.helper.ApiCredentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class MovieRepository(context: Context): MovieDataSource{

    companion object {
        const val TAG = "MovieRepository"
        const val PAGE_LIST_NUMBER = 1
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiCredentials.baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val movieService = retrofit.create(MovieService::class.java)

    private val moviesDatabase = MoviesDatabase.getDatabase(context)
    private val movieListDao = moviesDatabase.resultDao(moviesDatabase)
    private val movieDetailsDao = moviesDatabase.movieDetailsDao()
    private val moviePostersDao = moviesDatabase.moviePostersDao(moviesDatabase)


    override suspend fun getMovieListData(): List<Results>? =

        withContext(Dispatchers.IO) {
            try {
                val response = movieService.getMovieList(
                    ApiCredentials.api_key, ApiCredentials.language,
                    PAGE_LIST_NUMBER, ApiCredentials.region
                )

                if (response.isSuccessful) {
                    response.body()?.results?.let {
                        persistMovieListData(it)
                        response.body()?.results
                    }
                } else {
                    getLocalMovieListData()

                }
            } catch (e: Exception) {
                Log.d(TAG, "Exception get List: $e")
                getLocalMovieListData()
            }

        }

    override suspend fun getMovieDetailsData(id: Int): MovieDetailsResponse? =
        withContext(Dispatchers.IO) {
            try {
                val response = movieService.getMovieDetails(id, ApiCredentials.api_key, ApiCredentials.language)
                if (response.isSuccessful) {
                    Log.d(TAG, "Movie Details Response: ${response.body()}")
                    response.body()?.let { movieDetailsResponse ->
                        Log.d(TAG, "Movie Details Response: $movieDetailsResponse")
                        persistMovieDetailsData(movieDetailsResponse, id)
                        movieDetailsResponse
                    }

                } else {
                    Log.d(TAG, "Falha na requisição: ${response.body()}")
                    getLocalMovieDetailsData(id)
                }
            } catch (e: Exception) {
                Log.d(TAG, "Exception get Details: $e")
                getLocalMovieDetailsData(id)
            }

        }


    override suspend fun getMoviePostersData(id: Int): MoviePostersResponse? =
        withContext(Dispatchers.IO) {
            try {
                val response = movieService.getMoviePosters(
                    id, ApiCredentials.api_key, ApiCredentials.language,
                    ApiCredentials.include_image_language
                )
                if (response.isSuccessful) {
                    Log.d(TAG, "Get Posters Response: ${response.body()}")
                    response.body()?.let { moviePosterResponse ->
                        persistMoviePostersData(moviePosterResponse, id)
                        Log.d(TAG, "Get Posters Response: $moviePosterResponse")
                        moviePosterResponse
                    }

                } else {
                    Log.d(TAG, "Falha na requisição: ${response.body()}")
                    getLocalMoviePostersData(id)
                }

            } catch (e: Exception) {
                Log.d(TAG, "Exception get Posters: $e")
                getLocalMoviePostersData(id)
            }

        }


    override suspend fun persistMovieListData(movieList: List<Results>) {
        Log.d(TAG, "persistMovieListData $movieList")
        movieListDao.clearMovieListData()
        movieListDao.insertMovieList(movieList)
    }

    override suspend fun persistMovieDetailsData(movieDetails: MovieDetailsResponse, movieId: Int) {
        val movieDetailsPersisted = MovieDetailsResponse()
        movieDetailsPersisted.overview = movieDetails.overview
        movieDetailsPersisted.poster_path = movieDetails.poster_path
        movieDetailsPersisted.title = movieDetails.title
        movieDetailsPersisted.vote_average = movieDetails.vote_average
        movieDetailsPersisted.movieListId = movieId
        movieDetailsDao.insertMovieDetails(movieDetailsPersisted)
    }

    override suspend fun persistMoviePostersData(moviePosters: MoviePostersResponse, movieId: Int) {
        moviePosters.movieListId = movieId
        Log.d(TAG, "Movie Poster Object $moviePosters")
        moviePostersDao.insertMoviePosters(moviePosters)
    }

    override suspend fun getLocalMovieListData(): List<Results>? {
        return movieListDao.getAllMovies()
    }

    override suspend fun getLocalMovieDetailsData(movieId: Int): MovieDetailsResponse? {
        return movieDetailsDao.getMovieDetails(movieId)
    }

    override suspend fun getLocalMoviePostersData(movieId: Int): MoviePostersResponse? {
        return loadPersistedMoviePostersData(movieId)
    }

    override fun mapMoviePostersWithAllPropertiesToMoviePosters(moviePostersWithAllProperties: MoviePostersWithAllProperties): MoviePostersResponse {
        moviePostersWithAllProperties.moviePostersResponse.posters = moviePostersWithAllProperties.posters
        return moviePostersWithAllProperties.moviePostersResponse
    }

    override suspend fun clearMovieListData() {
        movieListDao.clearMovieListData()
    }

    private suspend fun loadPersistedMoviePostersData(movieId: Int): MoviePostersResponse? {
        val moviePostersWithAllProperties = moviePostersDao.getMoviePosters(movieId)
        return moviePostersWithAllProperties?.let { mapMoviePostersWithAllPropertiesToMoviePosters(it) }
    }

}