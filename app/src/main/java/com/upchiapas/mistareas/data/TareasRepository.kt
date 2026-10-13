package com.upchiapas.mistareas.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

// Repositorio: el único punto de acceso a los datos para el ViewModel.
// Junta las dos fuentes: la semilla (JSON) y lo guardado (DataStore).
class TareasRepository(
    private val semilla: TareasSemillaDataSource,
    private val local: TareasLocalDataSource
) {

    // Evita que dos cambios al mismo tiempo (dos clics rápidos) se pisen al guardar.
    private val mutex = Mutex()

    // Si el usuario todavía no ha guardado nada, se usan las tareas semilla.
    val tareas: Flow<List<Tarea>> = local.tareasGuardadas
        .map { guardadas -> guardadas ?: semilla.leerTareas() }
        .flowOn(Dispatchers.IO)

    suspend fun agregar(titulo: String) {
        mutex.withLock {
            val actuales = tareas.first()
            val nuevoId = (actuales.maxOfOrNull { it.id } ?: 0) + 1
            local.guardar(listOf(Tarea(id = nuevoId, titulo = titulo)) + actuales)
        }
    }

    suspend fun cambiarEstado(id: Int) {
        mutex.withLock {
            val actuales = tareas.first()
            val nuevas = actuales.map { tarea ->
                if (tarea.id == id) tarea.copy(completada = !tarea.completada) else tarea
            }
            local.guardar(nuevas)
        }
    }
}
