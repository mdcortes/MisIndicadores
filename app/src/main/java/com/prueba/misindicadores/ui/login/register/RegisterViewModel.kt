package com.prueba.misindicadores.ui.login.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prueba.misindicadores.R
import com.prueba.misindicadores.data.Result
import com.prueba.misindicadores.data.UserManager
import com.prueba.misindicadores.ui.login.LoggedInUserView
import com.prueba.misindicadores.ui.login.LoginChecks
import javax.inject.Inject

class RegisterViewModel @Inject constructor(private val userManager: UserManager) : ViewModel() {
    private var loginChecks = LoginChecks()

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    fun register(email: String, name: String, password: String) {
        val result = userManager.register(username = email, displayName = name, password = password)

        if (result is Result.Success) {
            _registerResult.value =
                RegisterResult(success = LoggedInUserView(displayName = result.data.displayName))
        } else {
            _registerResult.value = RegisterResult(error = R.string.login_failed)
        }
    }

    fun registerDataChanged(username: String, displayName: String, password: String) {
        if (!loginChecks.isUserNameValid(username)) {
            _registerForm.value = RegisterFormState(usernameError = R.string.invalid_username)
        } else if (!isDisplayNameValid(displayName)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_display_name)
        } else if (!loginChecks.isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        } else {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    // A placeholder password validation check
    private fun isDisplayNameValid(displayName: String): Boolean {
        return displayName.length > 5
    }
}