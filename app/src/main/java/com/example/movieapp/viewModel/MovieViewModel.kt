package com.example.movieapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.DataState
import com.example.movieapp.data.DataStateMovieDetails
import com.example.movieapp.data.Event
import com.example.movieapp.data.MovieDetailsResponse
import com.example.movieapp.data.MoviePostersResponse
import com.example.movieapp.data.Results
import com.example.movieapp.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "MovieViewModel"
    }

    private var movieList: List<Results>? = listOf()

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

    private val movieRepository = MovieRepository(application)

    init {
        getMoviesListData()
    }

    fun onMovieSelected(position: Int) {
        _movieDetailsState.postValue(DataStateMovieDetails.Loading)
        val movieID = movieList?.get(position)?.id
        Log.d(TAG, "movie id: $movieID")
        movieID?.let {
            getMovieDetailsAndPosters(it)
        }
        _navigationToDetailLiveData.postValue(Event(Unit))
    }

    fun getMoviesListData() {
        _appState.postValue(DataState.Loading)
        viewModelScope.launch {
            val movieListResult = movieRepository.getMovieListData()

            if (movieListResult.isNullOrEmpty()) {
                _appState.value = DataState.Error
            } else {
                _movieListLiveData.value = movieListResult
                movieList = movieListResult
                _appState.value = DataState.Success
            }

        }
    }

    private fun getMovieDetailsAndPosters(movieId: Int) {
        viewModelScope.launch {
            val movieDetails = movieRepository.getMovieDetailsData(movieId)
            val moviePosters = movieRepository.getMoviePostersData(movieId)
            if (movieDetails != null && moviePosters != null) {
                Log.d(TAG, "MovieDetails: $movieDetails")
                Log.d(TAG, "moviePosters: $moviePosters")

                movieDetails.let {
                    _movieDetailsLiveData.value = it
                }

                _moviePostersLiveData.postValue(moviePosters)

                _movieDetailsState.value = DataStateMovieDetails.Success
            } else {
                Log.d(TAG, "MovieDetails: $movieDetails")
                Log.d(TAG, "moviePosters: $moviePosters")
                _movieDetailsState.value = DataStateMovieDetails.Error
            }
        }
    }

}