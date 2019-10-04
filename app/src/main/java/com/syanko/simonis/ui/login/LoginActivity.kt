package com.syanko.simonis.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.syanko.simonis.R
import com.syanko.simonis.ui.main.MainActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)

        loginViewModel = ViewModelProviders.of(this, viewModelFactory)[LoginViewModel::class.java]

        password.apply {
            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }
        }

        login.setOnClickListener {
            loginViewModel.login(username.text.toString(), password.text.toString())
        }

        loginViewModel.isUserAlreadyLoggedIn.observe(this, Observer { isTrue ->
            if (isTrue) {
                navigateToMainPage()
            }
        })

        loginViewModel.loginResult.observe(this, Observer {
            Toast.makeText(
                this@LoginActivity,
                it.firstName,
                Toast.LENGTH_SHORT
            ).show()
            navigateToMainPage()
        })

        loginViewModel.loginFormState.observe(this, Observer {
            when (it.isLoading) {
                true -> {
                    loading.visibility = View.VISIBLE
                    login.isEnabled = false
                }
                false -> {
                    loading.visibility = View.GONE
                    login.isEnabled = true
                }
            }
        })

        loginViewModel.checkIsUserAlreadyLoggedIn()
    }

    private fun navigateToMainPage() {
        val intent = MainActivity.createIntent(this)
        startActivity(intent)
        finish()
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}
