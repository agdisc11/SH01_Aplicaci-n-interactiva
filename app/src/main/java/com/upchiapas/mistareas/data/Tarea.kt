package com.upchiapas.mistareas.data

// Modelo de datos: una tarea de la lista.
data class Tarea(
    val id: Int,
    val titulo: String,
    val completada: Boolean = false
)
