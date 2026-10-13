package com.upchiapas.mistareas.presentation.tareas

import androidx.annotation.StringRes
import com.upchiapas.mistareas.R
import com.upchiapas.mistareas.data.Tarea

// Los filtros que se pueden elegir arriba de la lista. Cada uno trae su texto de strings.xml.
enum class Filtro(@StringRes val etiqueta: Int) {
    TODAS(R.string.filtro_todas),
    PENDIENTES(R.string.filtro_pendientes),
    COMPLETADAS(R.string.filtro_completadas)
}

// Todo lo que la pantalla necesita para dibujarse.
// Es inmutable (puros val): para cambiar algo se crea una copia con copy().
data class TareasUiState(
    // true mientras llega la primera lista del repositorio (estado inicial).
    val cargando: Boolean = true,
    // Las tareas que se ven, ya filtradas por el ViewModel.
    val tareasVisibles: List<Tarea> = emptyList(),
    val filtro: Filtro = Filtro.TODAS,
    val textoNuevaTarea: String = "",
    // Id del mensaje de error de strings.xml, o null si no hay error.
    @StringRes val error: Int? = null,
    val totalTareas: Int = 0,
    val tareasCompletadas: Int = 0,
    // De 0.0 a 1.0, para la barra de progreso.
    val progreso: Float = 0f
)
