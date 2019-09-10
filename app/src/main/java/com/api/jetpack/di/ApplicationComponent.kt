package com.api.jetpack.di

import com.api.jetpack.JetPack
import com.api.jetpack.di.networking.ServiceModule
import com.api.jetpack.di.viewmodel.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ApplicationModule::class, DogActivityModule::class, ViewModelModule::class, ServiceModule::class])
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun buildApplication(jetPack: JetPack): Builder
        fun build(): ApplicationComponent
    }
    fun inject(jetPack: JetPack)
}