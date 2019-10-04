package com.syanko.simonis.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syanko.simonis.R
import com.syanko.simonis.domain.entity.User
import com.syanko.simonis.domain.interactor.UserInterActor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userInterActor: UserInterActor) :
    ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<User>()
    var loginResult: LiveData<User> = _loginResult

    private val _isUserAlreadyLoggedIn = MutableLiveData<Boolean>()
    val isUserAlreadyLoggedIn = _isUserAlreadyLoggedIn

    fun login(username: String, password: String) {
        _loginForm.value = LoginFormState(isLoading = true)
        viewModelScope.launch(context = Dispatchers.IO) {
            val result = userInterActor.signIn(username, password)

            withContext(Dispatchers.Main) {
                _loginForm.value = LoginFormState(isLoading = false)
                _loginResult.value = result
            }
        }
    }

    fun checkIsUserAlreadyLoggedIn() {
        viewModelScope.launch(context = Dispatchers.IO) {
            val result = userInterActor.isLoggedIn()

            withContext(Dispatchers.Main) {
                _isUserAlreadyLoggedIn.value = result
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
