package com.example.movieapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.api.MovieService
import com.example.movieapp.data.DataState
import com.example.movieapp.data.DataStateMovieDetails
import com.example.movieapp.data.Event
import com.example.movieapp.data.MovieDetailsResponse
import com.example.movieapp.data.MoviePostersResponse
import com.example.movieapp.data.MoviePostersWithAllProperties
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
    private val movieDetailsDao = moviesDatabase.movieDetailsDao()
    private val moviePostersDao = moviesDatabase.moviePostersDao(moviesDatabase)

    //MovieList LiveData
    val movieListLiveData: LiveData<List<Results>?>
        get() = _movieListLiveData
    private val _movieListLiveData = MutableLiveData<List<Results>?>()

    //MovieDetails LiveData
    val movieDetailsLiveData: LiveData<MovieDetailsResponse>
        get() = _movieDetailsLiveData
    private val _movieDetailsLiveData = MutableLiveData<MovieDetailsResponse>()

    //MoviePosters LiveData
    val moviePostersLiveData: LiveData<MoviePostersResponse?>
        get() = _moviePostersLiveData
    private val _moviePostersLiveData = MutableLiveData<MoviePostersResponse?>()

    //Navigation LiveData
    val navigationToDetailLiveData
        get() = _navigationToDetailLiveData
    private val _navigationToDetailLiveData = MutableLiveData<Event<Unit>>()

    val appState: LiveData<DataState>
        get() = _appState
    private val _appState = MutableLiveData<DataState>()

    val movieDetailsState: LiveData<DataStateMovieDetails>
        get() = _movieDetailsState
    private val _movieDetailsState = MutableLiveData<DataStateMovieDetails>()

    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiCredentials.baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val movieService = retrofit.create(MovieService::class.java)

    init {
        getMoviesListData()
    }

    fun getMoviesListData() {

        _appState.postValue(DataState.Loading)

        viewModelScope.launch {
            try {
                val response = movieService.getMovieList(
                    ApiCredentials.api_key, ApiCredentials.language, PAGE_LIST_NUMBER, ApiCredentials.region
                )
                if (response.isSuccessful) {
                    Log.d(TAG, "movieList Response: ${response.body()?.results}")
                    movieList = response.body()?.results
                    movieList?.let {
                        persistMovieListData(it)
                    }
                    _movieListLiveData.postValue(response.body()?.results)
                    _appState.postValue(DataState.Success)
                } else {
                    Log.d(TAG, "Falha na requisição: ${response.body()?.results}")
                    errorMovieListHandling()
                }
            } catch (e: Exception) {
                Log.d(TAG, "exceção: $e")
                errorMovieListHandling()
            }

        }
    }

    private fun getMovieDetailsData(id: Int) {
        Log.d(TAG, "movie details response id: $id")

        viewModelScope.launch {
            try {
                val response = movieService.getMovieDetails(id, ApiCredentials.api_key, ApiCredentials.language)
                if (response.isSuccessful) {
                    _movieDetailsState.postValue(DataStateMovieDetails.Success)
                    Log.d(TAG, "Movie Details Response: ${response.body()}")
                    response.body()?.let {
                        persistMovieDetailsData(it, id)
                    }
                    _movieDetailsLiveData.postValue(response.body())
                    _appState.postValue(DataState.Success)

                } else {
                    Log.d(TAG, "Falha na requisição: ${response.body()}")
                    errorMovieDetailsHandling(id)
                }
            } catch (e: Exception) {
                Log.d(TAG, "exceção details: $e")
                errorMovieDetailsHandling(id)
            }
        }
    }

    private fun getMoviePostersData(id: Int) {

        viewModelScope.launch {
            try {
                val response = movieService.getMoviePosters(
                    id, ApiCredentials.api_key, ApiCredentials.language,
                    ApiCredentials.include_image_language
                )

                if(response.isSuccessful){
                    Log.d("response", "${response.body()}")
                    response.body()?.let {
                        persistMoviePostersData(it, id)
                    }
                    _moviePostersLiveData.postValue(response.body())
//                    _moviePostersState.postValue(DataStateMoviePosters.Success)

                }
                else{
                    Log.d(TAG, "Falha na requisição: ${response.body()}")
                    errorMoviePostersHandling(id)
                }

            } catch (e: Exception){
                Log.d(TAG, "exceção details: $e")
                errorMoviePostersHandling(id)
            }


        }
    }

    fun onMovieSelected(position: Int) {
        _movieDetailsState.postValue(DataStateMovieDetails.Loading)
        val movieID = movieList?.get(position)?.id
        Log.d(TAG, "movie id: $movieID")
        movieID?.let {
            getMovieDetailsData(it)
            getMoviePostersData(it)
        }
        _navigationToDetailLiveData.postValue(Event(Unit))
    }

    private suspend fun persistMovieListData(movieList: List<Results>) {
        Log.d(TAG, "persistMovieListData $movieList")
        movieListDao.clearMovieListData()
        movieListDao.insertMovieList(movieList)
    }

    private suspend fun persistMovieDetailsData(movieDetails: MovieDetailsResponse, movieId: Int){
        val movieDetailsPersisted = MovieDetailsResponse()
        movieDetailsPersisted.overview = movieDetails.overview
        movieDetailsPersisted.poster_path = movieDetails.poster_path
        movieDetailsPersisted.title = movieDetails.title
        movieDetailsPersisted.vote_average = movieDetails.vote_average
        movieDetailsPersisted.movieListId = movieId
        movieDetailsDao.insertMovieDetails(movieDetailsPersisted)
    }

    private suspend fun persistMoviePostersData(moviePostersResponse: MoviePostersResponse, movieId: Int) {
        moviePostersResponse.movieListId = movieId
        Log.d(TAG, "Movie Poster Object $moviePostersResponse")
        moviePostersDao.insertMoviePosters(moviePostersResponse)
    }

    private suspend fun errorMovieListHandling(){
        movieList = movieListDao.getAllMovies()
        Log.d(TAG, "errorMovieListHandling $movieList")

        if(movieList.isNullOrEmpty()){
            _appState.postValue(DataState.Error)
        } else {
            Log.d(TAG, "errorMovieListHandling $movieList")
            _movieListLiveData.postValue(movieList)
            _appState.postValue(DataState.Success)
        }
    }

    private suspend fun errorMovieDetailsHandling(id: Int){
        val movieDetails = movieDetailsDao.getMovieDetails(id)
        Log.d(TAG, "id details: $movieDetails")

        if(movieDetails == null){
            _movieDetailsState.postValue(DataStateMovieDetails.Error)
        } else {
            _movieDetailsLiveData.postValue(movieDetails)
            _movieDetailsState.postValue(DataStateMovieDetails.Success)
        }
    }

    private suspend fun errorMoviePostersHandling(id: Int){
        val moviePosters = loadPersistedMoviePostersData(id)
        Log.d(TAG, "id movie posters: $moviePosters")

        if (moviePosters == null){
//            _moviePostersState.postValue(DataStateMoviePosters.Error)
            Log.d(TAG, "Movie posters object is null")
        } else {
            _moviePostersLiveData.postValue(moviePosters)
//            _moviePostersState.postValue(DataStateMoviePosters.Success)
        }
    }

    private suspend fun loadPersistedMoviePostersData(movieId: Int): MoviePostersResponse?{
        val moviePostersWithAllProperties = moviePostersDao.getMoviePosters(movieId)
        return moviePostersWithAllProperties?.let { mapMoviePostersWithAllPropertiesToMoviePosters(it) }
    }

    private fun mapMoviePostersWithAllPropertiesToMoviePosters(moviePostersWithAllProperties: MoviePostersWithAllProperties): MoviePostersResponse {
        moviePostersWithAllProperties.moviePostersResponse.posters = moviePostersWithAllProperties.posters
        return moviePostersWithAllProperties.moviePostersResponse
    }

}