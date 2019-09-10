package com.api.jetpack.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.api.jetpack.model.DogBreed
import javax.inject.Inject

class DogListViewModel @Inject constructor() : ViewModel() {
    private val dogListLiveData: MutableLiveData<DogBreed> = MutableLiveData()
    private val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val isError: MutableLiveData<Boolean> = MutableLiveData()


    override fun onCleared() {
        super.onCleared()
    }

    fun refresh() {

    }
}