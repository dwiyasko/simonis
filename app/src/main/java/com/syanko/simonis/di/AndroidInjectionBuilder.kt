package com.syanko.simonis.di

import com.syanko.simonis.ui.form.FormFragment
import com.syanko.simonis.ui.form.content.FormContentFragment
import com.syanko.simonis.ui.login.LoginActivity
import com.syanko.simonis.ui.main.MainActivity
import com.syanko.simonis.ui.main.MenuFragment
import com.syanko.simonis.ui.main.equipment.EquipmentFragment
import com.syanko.simonis.ui.main.inspection.InspectionFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidInjectionBuilder {

    @ContributesAndroidInjector
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindMenuFragment(): MenuFragment

    @ContributesAndroidInjector
    abstract fun bindEquipmentFragment(): EquipmentFragment

    @ContributesAndroidInjector
    abstract fun bindInspectionFragment(): InspectionFragment

    @ContributesAndroidInjector
    abstract fun bindFormFragment(): FormFragment

    @ContributesAndroidInjector
    abstract fun bindFormContentFragment(): FormContentFragment
}