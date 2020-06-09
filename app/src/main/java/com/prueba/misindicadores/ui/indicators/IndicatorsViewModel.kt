package com.prueba.misindicadores.ui.indicators

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prueba.misindicadores.R
import com.prueba.misindicadores.data.IndicatorsRepository
import com.prueba.misindicadores.data.Result
import com.prueba.misindicadores.data.UserManager
import com.prueba.misindicadores.data.model.Indicator
import javax.inject.Inject

class IndicatorsViewModel @Inject constructor(private val userManager: UserManager,
                                              indicatorsRepository: IndicatorsRepository) : ViewModel() {

    private val _currentUser = MutableLiveData<String>()
    val currentUser: LiveData<String> get() = _currentUser

    private val _logoutResult = MutableLiveData<LogoutResult>()
    val logoutResult: LiveData<LogoutResult> get() = _logoutResult

    private val _indicatorsList: MutableLiveData<List<Indicator>> = indicatorsRepository.getIndicators()
    val indicatorsList: LiveData<List<Indicator>> get() = _indicatorsList

    init {
        if (userManager.isLoggedIn) {
            _currentUser.value = userManager.user?.displayName
        }
    }

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