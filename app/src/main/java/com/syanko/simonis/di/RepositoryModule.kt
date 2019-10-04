package com.syanko.simonis.di

import com.syanko.simonis.domain.repository.*
import com.syanko.simonis.platform.api.*
import com.syanko.simonis.platform.db.DaoProvider
import com.syanko.simonis.platform.repository.*
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideUserRepository(loginApi: LoginApi, authManager: AuthManager): UserRepository {
        return UserRepositoryImpl(loginApi, authManager)
    }

    @Provides
    fun provideGroupRepository(groupApi: GroupApi): GroupRepository {
        return GroupRepositoryImpl(groupApi)
    }

    @Provides
    fun provideFormRepository(formApi: FormApi, daoProvider: DaoProvider): FormRepository {
        return FormRepositoryImpl(formApi, daoProvider)
    }

    @Provides
    fun provideEquipmentRepository(equipmentApi: EquipmentApi): EquipmentRepository {
        return EquipmentRepositoryImpl(equipmentApi)
    }

    @Provides
    fun provideInspectionRepository(inspectionApi: InspectionApi): InspectionRepository {
        return InspectionRepositoryImpl(inspectionApi)
    }
}