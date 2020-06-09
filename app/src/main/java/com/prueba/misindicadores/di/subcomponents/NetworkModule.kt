package com.prueba.misindicadores.di.subcomponents

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.prueba.misindicadores.data.IndicatorsDataSource
import com.prueba.misindicadores.data.MiIndicadorApiResponse
import com.prueba.misindicadores.data.MiIndicadorApiResponseAdapter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
class NetworkModule {

    @Provides
    fun providesIndicatorsDataSource(): IndicatorsDataSource {
        val gsonBuilder = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .registerTypeAdapter(MiIndicadorApiResponse::class.java, MiIndicadorApiResponseAdapter())


        return Retrofit.Builder().baseUrl("https://www.mindicador.cl")
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .build()
            .create(IndicatorsDataSource::class.java)
    }
}