package com.prueba.misindicadores.di.subcomponents

import com.prueba.misindicadores.di.scopes.FragmentScope
import com.prueba.misindicadores.ui.indicators.IndicatorsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface IndicatorsComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): IndicatorsComponent
    }

    fun inject(indicatorsFragment: IndicatorsFragment)
}