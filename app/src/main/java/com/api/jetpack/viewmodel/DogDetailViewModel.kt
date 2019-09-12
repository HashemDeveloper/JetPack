package com.api.jetpack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.api.jetpack.data.local.IDogDao
import com.api.jetpack.model.DogBreed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DogDetailViewModel @Inject constructor(): ViewModel(), CoroutineScope {
    private val job = Job()
    @Inject
    lateinit var iDogDao: IDogDao
    private val dogDetailLiveData = MutableLiveData<DogBreed>()

    fun fetch(dogUUID: Int) {
        launch {
           val dogBreed= iDogDao.getDogBreedsById(dogUUID.toLong())
            dogDetailLiveData.value = dogBreed
        }
    }

    fun getDogDetailLiveData(): LiveData<DogBreed> {
        return this.dogDetailLiveData
    }

    override fun onCleared() {
        super.onCleared()
        this.job.cancel()
    }

    override val coroutineContext: CoroutineContext
        get() = this.job + Dispatchers.Main
}