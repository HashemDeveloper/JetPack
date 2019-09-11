package com.api.jetpack.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.api.jetpack.di.scopes.ViewModelKey
import com.api.jetpack.viewmodel.DogDetailViewModel
import com.api.jetpack.viewmodel.DogListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun provideViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DogListViewModel::class)
    internal abstract fun provideViewModelForDogList(dogListViewModel: DogListViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DogDetailViewModel::class)
    internal abstract fun provideViewModelForDogDetail(dogDetailViewModel: DogDetailViewModel): ViewModel
}