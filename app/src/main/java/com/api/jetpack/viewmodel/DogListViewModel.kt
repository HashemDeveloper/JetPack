package com.api.jetpack.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.api.jetpack.data.local.IDogDao
import com.api.jetpack.data.local.sharedpreference.ISharedPrefService
import com.api.jetpack.data.remote.DogListRepo
import com.api.jetpack.model.DogBreed
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DogListViewModel @Inject constructor(val dogListRepo: DogListRepo) : ViewModel(), CoroutineScope {

    // refresh time, 5 minutes in nano seconds
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L
    private val dogListLiveData: MutableLiveData<List<DogBreed>> = MutableLiveData()
    private val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val isError: MutableLiveData<Boolean> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()
    private val job = Job()

    @Inject
    lateinit var context: Context
    @Inject
    lateinit var sharedPreference: ISharedPrefService

    @Inject
    lateinit var iDogDao: IDogDao

    override val coroutineContext: CoroutineContext
        get() = this.job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        this.job.cancel()
        this.compositeDisposable.clear()
    }

    fun refresh() {
        val updateTime = this.sharedPreference.getUpdateTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < this.refreshTime) {
            fetchFromRoomDatabase();
        } else {
            fetchDogDataFromRemote()
        }
    }
    fun refreshBypassCache() {
        fetchDogDataFromRemote()
    }
    private fun fetchFromRoomDatabase() {
        this.isLoading.value = true
        launch {
            val dogsList = iDogDao.getAllDogBreeds()
            dogBreedListRetrieved(dogsList)
            Toast.makeText(context, "Dogs retrieved from db", Toast.LENGTH_SHORT).show()
        }
    }
    private fun fetchDogDataFromRemote() {
        this.isLoading.value = true
        this.compositeDisposable.add(this.dogListRepo.getDogList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<DogBreed>>(){
                override fun onSuccess(t: List<DogBreed>) {
                    storeDogLocally(t) // storing data locally first
                }

                override fun onError(e: Throwable) {
                    isError.value = true
                    isLoading.value = false
                }

            }))
    }
    private fun dogBreedListRetrieved(dogList: List<DogBreed>) {
        dogListLiveData.value = dogList
        isError.value = false
        isLoading.value = false
    }
    private fun storeDogLocally(dogList: List<DogBreed>) {
        launch {
            iDogDao.deleteAllDogData()
            val result = iDogDao.insertDogData(*dogList.toTypedArray())
            var i = 0
            while (i < result.size) {
                dogList[i].setUuid(result[i])
                i++
            }
            dogBreedListRetrieved(dogList)
        }
        this.sharedPreference.saveUpdateTime(System.nanoTime())
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