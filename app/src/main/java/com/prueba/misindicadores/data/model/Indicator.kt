package com.prueba.misindicadores.data.model

import java.util.Date

data class Indicator(
    val codigo: String,
    val nombre: String,
    val unidad_medida: String,
    val fecha: Date,
    val valor: Double
)