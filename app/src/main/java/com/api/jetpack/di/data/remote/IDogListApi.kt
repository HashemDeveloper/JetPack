package com.api.jetpack.di.data.remote

import com.api.jetpack.model.DogBreed
import io.reactivex.Single
import retrofit2.http.GET

interface IDogListApi {
    @GET("DevTides/DogsApi/master/dogs.json")
    fun getDogs(): Single<List<DogBreed>>
}