package com.api.jetpack.di.data.remote

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object DogListRepoModule {
    @Singleton
    @Provides
    @JvmStatic
    internal fun provideDogListRep(retrofit: Retrofit): IDogListApi {
        return retrofit.create(IDogListApi::class.java)
    }
}