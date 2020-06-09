package com.prueba.misindicadores.ui.indicators

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prueba.misindicadores.data.IndicatorsRepository
import com.prueba.misindicadores.data.model.Indicator
import javax.inject.Inject

class IndicatorDetailsViewModel @Inject constructor(private val indicatorsRepository: IndicatorsRepository)
    : ViewModel() {

    private val _indicator = MutableLiveData<Indicator>()
    val indicator: LiveData<Indicator> get() = _indicator

    fun getIndicator(indicatorCode: String) {
        _indicator.value = indicatorsRepository.getIndicatorByCode(indicatorCode)
    }
}