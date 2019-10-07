package com.syanko.simonis.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syanko.simonis.domain.entity.FormResultEntity
import com.syanko.simonis.domain.entity.Menu
import com.syanko.simonis.domain.entity.User
import com.syanko.simonis.domain.interactor.FormInterActor
import com.syanko.simonis.domain.interactor.MenuInterActor
import com.syanko.simonis.domain.interactor.UserInterActor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val menuInterActor: MenuInterActor,
    private val userInterActor: UserInterActor,
    private val formInterActor: FormInterActor
) : ViewModel() {

    private var menuSource = MutableLiveData<List<MainViewObject.MenuViewObject>>()
    val menu: LiveData<List<MainViewObject.MenuViewObject>> = menuSource

    private var logoutController = MutableLiveData<Boolean>()
    val logout: LiveData<Boolean> = logoutController

    private var activeUserController = MutableLiveData<User>()
    val activeUser: LiveData<User> = activeUserController

    private var activeFormSessionController = MutableLiveData<Long>()
    val activeFormSession: LiveData<Long> = activeFormSessionController

    private var formSessionEnded = MutableLiveData<Boolean>()
    val isSessionEnded: LiveData<Boolean> = formSessionEnded

    fun initiateActiveUser() {
        viewModelScope.launch(context = Dispatchers.IO) {
            val user = userInterActor.getActiveUser()

            withContext(Dispatchers.Main) {
                activeUserController.value = user
            }
        }
    }

    fun getMenuTree() {
        viewModelScope.launch(context = Dispatchers.IO) {
            val result: List<MainViewObject.MenuViewObject> = menuInterActor.getMenu()
                .mapToMenuViewObject()

            withContext(context = Dispatchers.Main) {
                menuSource.value = result
            }
        }
    }

    fun saveFormSession(userId: Int, form: MainViewObject.FormViewObject) {
        viewModelScope.launch(context = Dispatchers.IO) {
            val activeSession = formInterActor.saveFormSession(
                form.mapToFormEntity(
                    userId
                )
            )

            withContext(context = Dispatchers.Main) {
                activeFormSessionController.value = activeSession
            }
        }
    }

    fun deleteActiveFormSession(formSessionId: Long) {
        viewModelScope.launch(context = Dispatchers.IO) {
            val result = formInterActor.deleteFormSession(formSessionId)

            withContext(context = Dispatchers.Main) {
                formSessionEnded.value = result > 0
            }
        }
    }

    fun saveFormValue() {}

    fun logout() {
        viewModelScope.launch(context = Dispatchers.IO) {
            val result = userInterActor.logout()

            withContext(context = Dispatchers.Main) {
                logoutController.value = result > 0
            }
        }
    }

    private fun List<Menu>.mapToMenuViewObject(): List<MainViewObject.MenuViewObject> {
        return map {
            MainViewObject.MenuViewObject(
                it.id.toInt(),
                it.name,
                it.children.mapToMenuViewObject()
            )
        }
    }

    private fun MainViewObject.FormViewObject.mapToFormEntity(userId: Int): FormResultEntity {
        return FormResultEntity(
            userId,
            inspectionId,
            id,
            substationId,
            voltageId,
            bayId
        )
    }
}

