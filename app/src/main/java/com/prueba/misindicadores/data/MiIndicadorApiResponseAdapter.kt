package com.prueba.misindicadores.data

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.prueba.misindicadores.data.model.Indicator
import java.lang.reflect.Type
import java.util.Date

class MiIndicadorApiResponseAdapter : JsonDeserializer<MiIndicadorApiResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): MiIndicadorApiResponse {
        lateinit var version: String
        lateinit var autor: String
        lateinit var fecha: Date

        val indicatorList = mutableListOf<Indicator>()
        val gson = Gson()
        val jsonObject = json!!.asJsonObject

        for (elementJson in jsonObject.entrySet()) {
            when(elementJson.key) {
                "version" -> version = elementJson.value.asString
                "autor" -> autor = elementJson.value.asString
                "fecha" -> fecha = gson.fromJson(elementJson.value, Date::class.java)
                else -> {
                    indicatorList.add(gson.fromJson(elementJson.value, Indicator::class.java))
                }
            }
        }

        return MiIndicadorApiResponse(version, autor, fecha, indicatorList)
    }
}