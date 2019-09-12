package com.api.jetpack.data.remote

import com.api.jetpack.model.DogBreed
import io.reactivex.Single
import javax.inject.Inject

class DogListRepo @Inject constructor() {
    @Inject
    lateinit var dogApi: IDogListApi

    fun getDogList(): Single<List<DogBreed>> {
        return this.dogApi.getDogs()
    }
}