package com.prueba.misindicadores.data

import retrofit2.Call
import retrofit2.http.GET

interface IndicatorsDataSource {
    @GET("api")
    fun requestIndicators(): Call<MiIndicadorApiResponse>
}