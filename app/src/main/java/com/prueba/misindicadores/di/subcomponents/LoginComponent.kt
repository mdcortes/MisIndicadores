package com.prueba.misindicadores.di.subcomponents

import com.prueba.misindicadores.di.scopes.FragmentScope
import com.prueba.misindicadores.ui.login.LoginFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface LoginComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

    fun inject(loginFragment: LoginFragment)
}