package com.prueba.misindicadores.data

import androidx.lifecycle.MutableLiveData
import com.prueba.misindicadores.data.model.Indicator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class IndicatorsRepository @Inject constructor(private val indicatorsDataSource: IndicatorsDataSource) {
    fun getIndicators() : MutableLiveData<List<Indicator>> {
        val data = MutableLiveData<List<Indicator>>()

        indicatorsDataSource.requestIndicators().enqueue(object : Callback<MiIndicadorApiResponse> {
            override fun onResponse(
                call: Call<MiIndicadorApiResponse>,
                response: Response<MiIndicadorApiResponse>
            ) {
                if (response.isSuccessful) {
                    data.value = response.body()?.indicadores
                }
            }

            override fun onFailure(call: Call<MiIndicadorApiResponse>, t: Throwable) {
                // No hacemos nada
            }
        })

        return data
    }
}