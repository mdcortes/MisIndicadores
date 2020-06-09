package com.prueba.misindicadores.data

import com.prueba.misindicadores.data.model.Indicator
import java.util.Date

data class MiIndicadorApiResponse (
    val version: String,
    val author: String,
    val fecha: Date,
    val indicadores: List<Indicator>
)