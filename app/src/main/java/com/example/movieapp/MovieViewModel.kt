package com.example.movieapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.*
import com.example.movieapp.movieDetails.MovieDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieViewModel: ViewModel() {

    //MovieDetails LiveData
    val movieDetailsLiveData: LiveData<MovieDetails>
        get() = _movieDetailsLiveData
    private val _movieDetailsLiveData = MutableLiveData<MovieDetails>()

    //MovieList LiveData
    val movieListLiveData: LiveData<List<Results>?>
        get() = _movieListLiveData
    private val _movieListLiveData = MutableLiveData<List<Results>?>()

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
        getMoviesData()
    }

    private fun getMoviesData(){
        movieService.getMovieList(ApiCredentials.api_key, ApiCredentials.language, 1, ApiCredentials.region).enqueue(object: Callback<MovieListResponse>{
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
//                Log.d("response", "${response.body()}")
                if(response.isSuccessful){
                    _movieListLiveData.postValue(response.body()?.results)
                    _appState.postValue(DataState.Success)
                } else {
                    _appState.postValue(DataState.Error)
                }
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
//                Log.d("response", "$t")
                _appState.postValue(DataState.Error)
            }

        } )
    }

    fun getMovieDetails(){
        TODO("Fazer a requisição dos detalhes do filme")
    }

    fun onMovieSelected(position: Int){
        val movieDetails = MovieDetails("Filme 1", "Texto do conteúdo da sinopse", "10/10")
        _movieDetailsLiveData.postValue(movieDetails)
        _navigationToDetailLiveData.postValue(Unit)
    }

}