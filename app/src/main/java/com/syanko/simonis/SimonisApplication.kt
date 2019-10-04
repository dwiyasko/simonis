package com.syanko.simonis

import com.syanko.simonis.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class SimonisApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<SimonisApplication> {
        return DaggerApplicationComponent.builder().application(this).build()
    }
}