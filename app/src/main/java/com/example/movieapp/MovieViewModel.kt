package com.example.movieapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.api.MovieService
import com.example.movieapp.data.DataState
import com.example.movieapp.data.Event
import com.example.movieapp.data.MovieDetailsResponse
import com.example.movieapp.data.Results
import com.example.movieapp.database.MoviesDatabase
import com.example.movieapp.helper.ApiCredentials
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "MovieViewModel"
        const val PAGE_LIST_NUMBER = 1
    }

    private var movieList: List<Results>? = listOf()
    private val moviesDatabase = MoviesDatabase.getDatabase(application)
    private val movieListDao = moviesDatabase.resultDao(moviesDatabase)

    //MovieList LiveData
    val movieListLiveData: LiveData<List<Results>?>
        get() = _movieListLiveData
    private val _movieListLiveData = MutableLiveData<List<Results>?>()

    //MovieDetails LiveData
    val movieDetailsLiveData: LiveData<MovieDetailsResponse>
        get() = _movieDetailsLiveData
    private val _movieDetailsLiveData = MutableLiveData<MovieDetailsResponse>()

    //MoviePosters LiveData
    val moviePostersLiveData: LiveData<MoviePostersResponse>
        get() = _moviePostersLiveData
    private val _moviePostersLiveData = MutableLiveData<MoviePostersResponse>()

    //Navigation LiveData
    val navigationToDetailLiveData
        get() = _navigationToDetailLiveData
    private val _navigationToDetailLiveData = MutableLiveData<Event<Unit>>()

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
        getMoviesListData()
    }

    private fun getMoviesListData() {

        viewModelScope.launch {
            try {
                val response = movieService.getMovieList(
                    ApiCredentials.api_key, ApiCredentials.language, PAGE_LIST_NUMBER, ApiCredentials.region
                )
                if (response.isSuccessful) {
                    Log.d(TAG, "${response.body()?.results}")
                    movieList = response.body()?.results
                    movieList?.let {
                        persistMovieListData(it)
                    }
                    _movieListLiveData.postValue(response.body()?.results)
                    _appState.postValue(DataState.Success)
                } else {
                    Log.d(TAG, "Não sucesso :( ${response.body()?.results}")
                    errorMovieListHandling()
                }
            } catch (e: Exception) {
                Log.d(TAG, "exceção: $e")
                errorMovieListHandling()
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

    private fun getMoviePostersData(id: Int) {

        viewModelScope.launch {
            val response = movieService.getMoviePosters(
                id, ApiCredentials.api_key, ApiCredentials.language,
                ApiCredentials.include_image_language
            )
            Log.d("response", "${response.body()}")
            _moviePostersLiveData.postValue(response.body())
            _appState.postValue(DataState.Success)

        }
    }

    fun onMovieSelected(position: Int) {
        _appState.postValue(DataState.Loading)
        val movieID = movieList?.get(position)?.id
        movieID?.let {
            getMovieDetailsData(it)
            getMoviePostersData(it)
        }
        _navigationToDetailLiveData.postValue(Event(Unit))
    }

    private suspend fun persistMovieListData(movieList: List<Results>) {
        movieListDao.clearMovieListData()
        movieListDao.insertMovieList(movieList)
    }

    private suspend fun errorMovieListHandling(){
        val movieList = movieListDao.getAllMovies()

        if(movieList.isNullOrEmpty()){
            _appState.postValue(DataState.Error)
        } else {
            _movieListLiveData.postValue(movieList)
            _appState.postValue(DataState.Success)
        }
    }

}