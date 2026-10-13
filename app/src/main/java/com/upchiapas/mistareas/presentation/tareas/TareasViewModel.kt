package com.upchiapas.mistareas.presentation.tareas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.upchiapas.mistareas.R
import com.upchiapas.mistareas.data.Tarea
import com.upchiapas.mistareas.data.TareasLocalDataSource
import com.upchiapas.mistareas.data.TareasRepository
import com.upchiapas.mistareas.data.TareasSemillaDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TareasViewModel(private val repositorio: TareasRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(TareasUiState())

    val uiState: StateFlow<TareasUiState> = _uiState.asStateFlow()

    // La última lista completa (sin filtrar) que mandó el repositorio.
    private var todasLasTareas: List<Tarea> = emptyList()

    init {
        // Cada vez que se guarda algo en DataStore, el repositorio manda la lista nueva.
        viewModelScope.launch {
            repositorio.tareas.collect { tareas ->
                todasLasTareas = tareas
                _uiState.update { estado -> calcularEstado(estado.copy(cargando = false)) }
            }
        }
    }

    fun onTextoChange(texto: String) {
        _uiState.update { it.copy(textoNuevaTarea = texto, error = null) }
    }

    fun onAgregarClick() {
        val titulo = _uiState.value.textoNuevaTarea.trim()

        val error = when {
            titulo.isEmpty() -> R.string.error_vacio
            titulo.length > MAX_CARACTERES -> R.string.error_largo
            todasLasTareas.any { it.titulo.equals(titulo, ignoreCase = true) } -> R.string.error_repetida
            else -> null
        }

        if (error != null) {
            _uiState.update { it.copy(error = error) }
            return
        }

        _uiState.update { it.copy(textoNuevaTarea = "") }
        viewModelScope.launch { repositorio.agregar(titulo) }
    }

    fun onTareaClick(id: Int) {
        viewModelScope.launch { repositorio.cambiarEstado(id) }
    }

    fun onFiltroClick(filtro: Filtro) {
        _uiState.update { calcularEstado(it.copy(filtro = filtro)) }
    }

    // Aplica el filtro y saca los contadores a partir de la lista completa.
    private fun calcularEstado(estado: TareasUiState): TareasUiState {
        val visibles = when (estado.filtro) {
            Filtro.TODAS -> todasLasTareas
            Filtro.PENDIENTES -> todasLasTareas.filter { !it.completada }
            Filtro.COMPLETADAS -> todasLasTareas.filter { it.completada }
        }
        val completadas = todasLasTareas.count { it.completada }
        val total = todasLasTareas.size

        return estado.copy(
            tareasVisibles = visibles,
            totalTareas = total,
            tareasCompletadas = completadas,
            progreso = if (total == 0) 0f else completadas.toFloat() / total
        )
    }

    companion object {
        private const val MAX_CARACTERES = 40

        // Le dice a Compose cómo crear este ViewModel con su repositorio.
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = checkNotNull(this[APPLICATION_KEY])
                val repositorio = TareasRepository(
                    semilla = TareasSemillaDataSource(app),
                    local = TareasLocalDataSource(app)
                )
                TareasViewModel(repositorio)
            }
        }
    }
}
