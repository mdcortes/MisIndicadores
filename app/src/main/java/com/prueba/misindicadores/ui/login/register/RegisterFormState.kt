package com.prueba.misindicadores.ui.login.register

/**
 * Data validation state of the login form.
 */
data class RegisterFormState(
    val usernameError: Int? = null,
    val displayNameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)