package com.prueba.misindicadores.ui.login.register

import com.prueba.misindicadores.ui.login.LoggedInUserView

data class RegisterResult (
    val success: LoggedInUserView? = null,
    val error: Int? = null
)