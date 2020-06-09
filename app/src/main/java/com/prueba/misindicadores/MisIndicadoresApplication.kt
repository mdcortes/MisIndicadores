package com.prueba.misindicadores

import android.app.Application
import com.prueba.misindicadores.di.AppComponent
import com.prueba.misindicadores.di.DaggerAppComponent

class MisIndicadoresApplication: Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }
}