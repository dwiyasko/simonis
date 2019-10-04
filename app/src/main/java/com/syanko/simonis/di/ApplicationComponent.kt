package com.syanko.simonis.di

import com.syanko.simonis.SimonisApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AndroidInjectionBuilder::class,
        ApplicationModule::class, NetworkModule::class,
        ApiModule::class, RepositoryModule::class,
        ViewModelModule::class, InteractorModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<SimonisApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: SimonisApplication): Builder

        fun build(): ApplicationComponent
    }
}