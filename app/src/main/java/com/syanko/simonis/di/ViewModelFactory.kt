package com.syanko.simonis.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.syanko.simonis.ui.form.FormViewModel
import com.syanko.simonis.ui.form.content.FormContentViewModel
import com.syanko.simonis.ui.login.LoginViewModel
import com.syanko.simonis.ui.main.MainViewModel
import com.syanko.simonis.ui.main.equipment.EquipmentViewModel
import com.syanko.simonis.ui.main.inspection.InspectionViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModels[modelClass]?.get() as T
    }
}

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun loginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EquipmentViewModel::class)
    internal abstract fun equipmentViewModel(viewModel: EquipmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InspectionViewModel::class)
    internal abstract fun inspectionViewModel(viewModel: InspectionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FormContentViewModel::class)
    internal abstract fun formContentViewModel(viewModel: FormContentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FormViewModel::class)
    internal abstract fun formViewModel(viewModel: FormViewModel): ViewModel
}