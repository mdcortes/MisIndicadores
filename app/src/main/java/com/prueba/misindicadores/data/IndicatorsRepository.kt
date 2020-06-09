package com.prueba.misindicadores.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.prueba.misindicadores.data.model.Indicator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class IndicatorsRepository @Inject constructor(private val indicatorsDataSource: IndicatorsDataSource) {
    private val _indicators = MutableLiveData<List<Indicator>>()
    val indicators: LiveData<List<Indicator>> get() = _indicators

    init {
        getIndicators()
    }

    private fun getIndicators() {

        indicatorsDataSource.requestIndicators().enqueue(object : Callback<MiIndicadorApiResponse> {
            override fun onResponse(
                call: Call<MiIndicadorApiResponse>,
                response: Response<MiIndicadorApiResponse>
            ) {
                if (response.isSuccessful) {
                    _indicators.value = response.body()?.indicadores
                }
            }

            override fun onFailure(call: Call<MiIndicadorApiResponse>, t: Throwable) {
                // No hacemos nada
            }
        })
    }

    fun getIndicatorByCode(indicatorCode: String): Indicator? {
        return _indicators.value?.find { indicator -> indicator.codigo == indicatorCode }
    }
}