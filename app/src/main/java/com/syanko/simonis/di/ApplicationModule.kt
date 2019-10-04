package com.syanko.simonis.di

import com.syanko.simonis.SimonisApplication
import com.syanko.simonis.domain.repository.AuthManager
import com.syanko.simonis.platform.db.DaoProvider
import com.syanko.simonis.platform.db.DaoProviderImpl
import com.syanko.simonis.platform.db.SimonisDatabase
import com.syanko.simonis.platform.repository.AuthManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideDatabase(application: SimonisApplication): SimonisDatabase {
        return SimonisDatabase.getInstance(application)!!
    }

    @Provides
    @Singleton
    fun provideDaoProvider(simonisDatabase: SimonisDatabase): DaoProvider {
        return DaoProviderImpl(simonisDatabase)
    }

    @Provides
    @Singleton
    fun provideAuthManager(daoProvider: DaoProvider): AuthManager {
        return AuthManagerImpl(daoProvider)
    }
}