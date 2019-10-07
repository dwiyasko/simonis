package com.syanko.simonis.ui.webform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syanko.simonis.domain.interactor.UserInterActor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WebFormViewModel @Inject constructor(
    private val userInterActor: UserInterActor
) : ViewModel() {

    private val tokenSource: MutableLiveData<String> = MutableLiveData()
    val token: LiveData<String> = tokenSource

    fun getToken() {
        viewModelScope.launch(context = Dispatchers.IO) {
            val result = userInterActor.getToken()

            withContext(context = Dispatchers.Main) {
                tokenSource.value = result
            }
        }
    }
}