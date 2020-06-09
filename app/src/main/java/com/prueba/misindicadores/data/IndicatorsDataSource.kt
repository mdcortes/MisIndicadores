package com.prueba.misindicadores.data

import retrofit2.Call
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface IndicatorsDataSource {
    @GET("api")
    fun requestIndicators(): Call<MiIndicadorApiResponse>
}