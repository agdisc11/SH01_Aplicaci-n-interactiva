package com.upchiapas.mistareas.data

import android.content.Context
import com.upchiapas.mistareas.R

// Fuente de datos 1: las tareas base (semilla) que vienen dentro de la app,
// en el archivo res/raw/tareas_semilla.json.
class TareasSemillaDataSource(private val context: Context) {

    fun leerTareas(): List<Tarea> {
        val texto = context.resources.openRawResource(R.raw.tareas_semilla)
            .bufferedReader()
            .use { it.readText() }
        return TareasJson.desdeJson(texto)
    }
}
