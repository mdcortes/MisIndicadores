package com.prueba.misindicadores.di

import android.content.Context
import com.prueba.misindicadores.data.UserManager
import com.prueba.misindicadores.di.subcomponents.AppSubcomponents
import com.prueba.misindicadores.di.subcomponents.LoginComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppSubcomponents::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun loginComponent(): LoginComponent.Factory
    fun userManager(): UserManager
}