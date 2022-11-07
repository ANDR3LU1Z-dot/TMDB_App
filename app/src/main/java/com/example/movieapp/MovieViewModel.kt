package com.example.movieapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieViewModel: ViewModel() {

    //MovieDetails LiveData
    val movieDetailsLiveData: LiveData<MovieDetails>
        get() = _movieDetailsLiveData
    private val _movieDetailsLiveData = MutableLiveData<MovieDetails>()

    //MovieList LiveData
    val movieListLiveData: LiveData<MutableList<BodyCardMovies>>
        get() = _movieListLiveData
    private val _movieListLiveData = MutableLiveData<MutableList<BodyCardMovies>>()

    //Navigation LiveData
    val navigationToDetailLiveData
        get() = _navigationToDetailLiveData
    private val _navigationToDetailLiveData = MutableLiveData<Unit>()

    init {
        _movieListLiveData.postValue(MockupMovies.cardMoviesList)
    }

    fun onMovieSelected(position: Int){
        val movieDetails = MovieDetails("Filme 1", "Texto do conteúdo da sinopse", "10/10")
        _movieDetailsLiveData.postValue(movieDetails)
        _navigationToDetailLiveData.postValue(Unit)
    }

}