package com.upchiapas.mistareas.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Un solo DataStore para toda la app. Se guarda en el archivo "tareas.preferences_pb".
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tareas")

// Fuente de datos 2: las tareas guardadas en el dispositivo con DataStore (clave-valor).
// Toda la lista se guarda como un texto JSON dentro de una sola clave.
class TareasLocalDataSource(private val context: Context) {

    private val claveTareas = stringPreferencesKey("lista_tareas")

    // Emite la lista guardada cada vez que cambia, o null si todavía no se ha guardado nada.
    val tareasGuardadas: Flow<List<Tarea>?> = context.dataStore.data.map { preferencias ->
        val texto = preferencias[claveTareas]
        if (texto == null) null else TareasJson.desdeJson(texto)
    }

    suspend fun guardar(tareas: List<Tarea>) {
        context.dataStore.edit { preferencias ->
            preferencias[claveTareas] = TareasJson.aJson(tareas)
        }
    }
}
