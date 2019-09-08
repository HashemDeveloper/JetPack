package com.api.jetpack.di

import com.api.jetpack.DogActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DogActivityModule {
    @ContributesAndroidInjector(modules = [FragmentsBuilderModule::class])
    abstract fun contributeDogActivity(): DogActivity
}