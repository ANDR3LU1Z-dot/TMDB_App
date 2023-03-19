package com.example.movieapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.*
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieViewModel: ViewModel() {
    private lateinit var movieList: List<Results>

    //MovieList LiveData
    val movieListLiveData: LiveData<List<Results>?>
        get() = _movieListLiveData
    private val _movieListLiveData = MutableLiveData<List<Results>?>()

    //MovieDetails LiveData
    val movieDetailsLiveData: LiveData<MovieDetailsResponse>
        get() = _movieDetailsLiveData
    private val _movieDetailsLiveData = MutableLiveData<MovieDetailsResponse>()

    //MoviePosters LiveData
    val moviePostersLiveData: LiveData<List<Posters>?>
        get() = _moviePostersLiveData
    val _moviePostersLiveData = MutableLiveData<List<Posters>?>()

    //Navigation LiveData
    val navigationToDetailLiveData
        get() = _navigationToDetailLiveData
    private val _navigationToDetailLiveData = MutableLiveData<Unit>()

    val appState: LiveData<DataState>
        get() = _appState
    private val _appState = MutableLiveData<DataState>()

    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiCredentials.baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val movieService = retrofit.create(MovieService::class.java)

    init {
        _appState.postValue(DataState.Loading)
        GlobalScope.launch { getMoviesListData() }
    }

    private fun getMoviesListData() {

        viewModelScope.launch {
            val response =
                movieService.getMovieList(ApiCredentials.api_key, ApiCredentials.language, 1, ApiCredentials.region)
            if (response.isSuccessful) {
                Log.d("movieList", "${response.body()?.results}")
                movieList = response.body()?.results!!
                _movieListLiveData.postValue(response.body()?.results)
                _appState.postValue(DataState.Success)
            } else {
                _appState.postValue(DataState.Error)
            }
        }
    }

    private fun getMovieDetailsData(id: Int) {

        viewModelScope.launch {
            val response = movieService.getMovieDetails(id, ApiCredentials.api_key, ApiCredentials.language)
            Log.d("response", "${response.body()}")
            _movieDetailsLiveData.postValue(response.body())
        }
    }

    private fun getMoviePostersData(id: Int){

        viewModelScope.launch{
            val response = movieService.getMoviePosters(
                id, ApiCredentials.api_key, ApiCredentials.language,
                ApiCredentials.include_image_language
            )
            Log.d("response", "${response.body()?.posters}")
            _moviePostersLiveData.postValue(response.body()?.posters)
            _appState.postValue(DataState.Success)

        }
    }

    fun onMovieSelected(position: Int){
        _appState.postValue(DataState.Loading)
        val movieDetails = movieList[position].id
        movieDetails.let {
            getMovieDetailsData(it)
            getMoviePostersData(it)
        }
        _navigationToDetailLiveData.postValue(Unit)
    }

}