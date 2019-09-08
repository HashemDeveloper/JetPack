package com.api.jetpack.di

import com.api.jetpack.view.DetailFragment
import com.api.jetpack.view.ListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailFragment
    @ContributesAndroidInjector
    abstract fun contributeListFragment(): ListFragment
}