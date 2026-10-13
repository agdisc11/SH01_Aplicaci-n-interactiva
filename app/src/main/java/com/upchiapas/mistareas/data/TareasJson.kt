package com.upchiapas.mistareas.data

import org.json.JSONArray
import org.json.JSONObject

// Convierte la lista de tareas a texto JSON y de regreso.
// Se usa para leer el archivo semilla y para guardar la lista en DataStore.
object TareasJson {

    fun desdeJson(texto: String): List<Tarea> {
        val arreglo = JSONArray(texto)
        return List(arreglo.length()) { i ->
            val objeto = arreglo.getJSONObject(i)
            Tarea(
                id = objeto.getInt("id"),
                titulo = objeto.getString("titulo"),
                completada = objeto.getBoolean("completada")
            )
        }
    }

    fun aJson(tareas: List<Tarea>): String {
        val arreglo = JSONArray()
        for (tarea in tareas) {
            val objeto = JSONObject()
            objeto.put("id", tarea.id)
            objeto.put("titulo", tarea.titulo)
            objeto.put("completada", tarea.completada)
            arreglo.put(objeto)
        }
        return arreglo.toString()
    }
}
