package com.api.jetpack.di

import com.api.jetpack.JetPack
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ApplicationModule::class, DogActivityModule::class])
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun buildApplication(jetPack: JetPack): Builder
        fun build(): ApplicationComponent
    }
    fun inject(jetPack: JetPack)
}