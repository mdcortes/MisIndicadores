package com.prueba.misindicadores.di.subcomponents

import com.prueba.misindicadores.di.scopes.FragmentScope
import com.prueba.misindicadores.ui.login.register.RegisterFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface RegisterComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): RegisterComponent
    }

    fun inject(registerFragment: RegisterFragment)
}