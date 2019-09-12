package com.api.jetpack.di

import android.content.Context
import com.api.jetpack.JetPack
import com.api.jetpack.data.local.IDogDao
import com.api.jetpack.data.local.RoomDbService
import com.api.jetpack.data.local.sharedpreference.ISharedPrefService
import com.api.jetpack.data.local.sharedpreference.SharedPreferenceService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {
    @Singleton
    @Provides
    fun provideContext(jetPack: JetPack): Context {
        return jetPack;
    }
    @Singleton
    @Provides
    fun provideDogDao(roomDbService: RoomDbService): IDogDao {
        return roomDbService.getDogDao()
    }
    @Singleton
    @Provides
    fun provideSharedPrefService(context: Context): SharedPreferenceService {
        return SharedPreferenceService.invoke(context)
    }
    @Singleton
    @Provides
    fun provideSharedPref(service: SharedPreferenceService): ISharedPrefService {
        return service
    }
}