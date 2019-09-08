package com.api.jetpack.di

import android.content.Context
import com.api.jetpack.JetPack
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
}