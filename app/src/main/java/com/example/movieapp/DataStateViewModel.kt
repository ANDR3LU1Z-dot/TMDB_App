package com.example.movieapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataStateViewModel: ViewModel() {

    val dataStateLiveData: LiveData<DataState>
        get() = _dataStateLiveData

    private val _dataStateLiveData = MutableLiveData<DataState>()

    fun addState(state: DataState){
        _dataStateLiveData.postValue(state)
    }
}