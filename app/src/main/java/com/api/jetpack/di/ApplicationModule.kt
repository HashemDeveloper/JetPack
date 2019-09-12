package com.api.jetpack.di

import android.content.Context
import com.api.jetpack.JetPack
import com.api.jetpack.data.local.IDogDao
import com.api.jetpack.data.local.RoomDbService
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
}