package com.prueba.misindicadores.ui.indicators

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prueba.misindicadores.R
import com.prueba.misindicadores.data.Result
import com.prueba.misindicadores.data.UserManager
import javax.inject.Inject

class IndicatorsViewModel @Inject constructor(private val userManager: UserManager) : ViewModel() {
    private val _logoutResult = MutableLiveData<LogoutResult>()
    val logoutResult: LiveData<LogoutResult> get() = _logoutResult

    fun logout() {
        val result = userManager.logout()

        if (result is Result.Success) {
            _logoutResult.value = LogoutResult(Unit)
        }

        else {
            _logoutResult.value = LogoutResult(error = R.string.logout_failed)
        }
    }
}