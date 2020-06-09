package com.prueba.misindicadores.di.subcomponents

import com.prueba.misindicadores.di.scopes.FragmentScope
import com.prueba.misindicadores.ui.indicators.IndicatorDetailsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface IndicatorDetailsComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): IndicatorDetailsComponent
    }

    fun inject(indicatorDetailsFragment: IndicatorDetailsFragment)
}