package com.example.movieapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    private suspend fun getMoviesListData() = coroutineScope{
        launch(Dispatchers.IO){
            movieService.getMovieList(ApiCredentials.api_key, ApiCredentials.language,
                1, ApiCredentials.region).enqueue(object : Callback<MovieListResponse> {
                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
                ) {
                    if(response.isSuccessful){
//                        Log.d("movieList", "${response.body()?.results}")
                        movieList = response.body()?.results!!
                        _movieListLiveData.postValue(response.body()?.results)
                        _appState.postValue(DataState.Success)
                    } else {
                        _appState.postValue(DataState.Error)
                    }
                }

                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                    _appState.postValue(DataState.Error)
                }
            } )
        }
    }

    private suspend fun getMovieDetailsData(id: Int) = coroutineScope{
        launch(Dispatchers.IO){
            movieService.getMovieDetails(id, ApiCredentials.api_key, ApiCredentials.language)
                .enqueue(object : Callback<MovieDetailsResponse> {
                override fun onResponse(
                    call: Call<MovieDetailsResponse>,
                    response: Response<MovieDetailsResponse>
                ) {
//                    Log.d("response", "${response.body()}")
                    _movieDetailsLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<MovieDetailsResponse>, t: Throwable) {
//                    Log.d("response", "$t")
                }

            })
        }

    }

    private suspend fun getMoviePostersData(id: Int) = coroutineScope {
        launch ( Dispatchers.IO ){
            movieService.getMoviePosters(id, ApiCredentials.api_key, ApiCredentials.language,
                ApiCredentials.include_image_language).enqueue(object : Callback<MoviePostersResponse>{
                override fun onResponse(call: Call<MoviePostersResponse>, response: Response<MoviePostersResponse>) {
//                    Log.d("response", "$response")
                    Log.d("response", "${response.body()?.posters}")
                    _moviePostersLiveData.postValue(response.body()?.posters)
                }

                override fun onFailure(call: Call<MoviePostersResponse>, t: Throwable) {
                    Log.d("response", "$t")
                }

            })
        }
    }

    fun onMovieSelected(position: Int){
        GlobalScope.launch {
            getMovieDetailsData(movieList[position].id)
            getMoviePostersData(movieList[position].id)
        }
        _navigationToDetailLiveData.postValue(Unit)
    }

}