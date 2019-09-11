package com.api.jetpack.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.api.jetpack.di.data.remote.DogListRepo
import com.api.jetpack.model.DogBreed
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DogListViewModel @Inject constructor(val dogListRepo: DogListRepo) : ViewModel() {
    private val dogListLiveData: MutableLiveData<List<DogBreed>> = MutableLiveData()
    private val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val isError: MutableLiveData<Boolean> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()


    override fun onCleared() {
        super.onCleared()
    }

    fun refresh() {
        this.isLoading.value = true
        this.compositeDisposable.add(this.dogListRepo.getDogList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<DogBreed>>(){
                override fun onSuccess(t: List<DogBreed>) {
                    dogListLiveData.value = t
                    isError.value = false
                    isLoading.value = false
                }

                override fun onError(e: Throwable) {
                    isError.value = true
                    isLoading.value = false
                }

            }))
    }
    fun getDogListLiveData(): LiveData<List<DogBreed>> {
        return this.dogListLiveData
    }
    fun getIsLoading(): LiveData<Boolean>{
        return this.isLoading
    }
    fun getIsError(): LiveData<Boolean> {
        return this.isError
    }
}