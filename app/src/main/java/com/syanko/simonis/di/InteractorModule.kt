package com.syanko.simonis.di

import com.syanko.simonis.domain.interactor.*
import com.syanko.simonis.domain.repository.*
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {
    @Provides
    fun provideSignInInterActor(userRepository: UserRepository): UserInterActor {
        return UserInterActorImpl(userRepository)
    }

    @Provides
    fun provideMenuInterActor(
        groupRepository: GroupRepository,
        equipmentRepository: EquipmentRepository,
        inspectionRepository: InspectionRepository
    ): MenuInterActor {
        return MenuInterActorImpl(
            groupRepository,
            equipmentRepository,
            inspectionRepository
        )
    }

    @Provides
    fun provideFormInterActor(formRepository: FormRepository): FormInterActor {
        return FormInterActorImpl(formRepository)
    }
}