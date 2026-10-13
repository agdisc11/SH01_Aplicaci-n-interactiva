package com.upchiapas.mistareas.presentation.tareas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upchiapas.mistareas.R
import com.upchiapas.mistareas.data.Tarea
import com.upchiapas.mistareas.presentation.tareas.components.FiltrosTareas
import com.upchiapas.mistareas.presentation.tareas.components.FormularioTarea
import com.upchiapas.mistareas.presentation.tareas.components.ResumenTareas
import com.upchiapas.mistareas.presentation.tareas.components.TareaItem

// Pantalla con estado: es la única que conoce al ViewModel.
@Composable
fun TareasPage(viewModel: TareasViewModel = viewModel(factory = TareasViewModel.Factory)) {

    // Cada vez que el ViewModel emite un estado nuevo, esta pantalla se vuelve a dibujar
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TareasContenido(
        uiState = uiState,
        onTextoChange = { texto -> viewModel.onTextoChange(texto) },
        onAgregarClick = { viewModel.onAgregarClick() },
        onFiltroClick = { filtro -> viewModel.onFiltroClick(filtro) },
        onTareaClick = { id -> viewModel.onTareaClick(id) }
    )
}

// Pantalla sin estado: solo dibuja lo que trae uiState y avisa los eventos hacia arriba.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareasContenido(
    uiState: TareasUiState,
    onTextoChange: (String) -> Unit,
    onAgregarClick: () -> Unit,
    onFiltroClick: (Filtro) -> Unit,
    onTareaClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.titulo)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        // Toda la pantalla es una sola lista con scroll, así también cabe con el celular acostado.
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                ResumenTareas(
                    completadas = uiState.tareasCompletadas,
                    total = uiState.totalTareas,
                    progreso = uiState.progreso
                )
            }

            item {
                FormularioTarea(
                    texto = uiState.textoNuevaTarea,
                    error = uiState.error,
                    onTextoChange = onTextoChange,
                    onAgregarClick = onAgregarClick
                )
            }

            item {
                FiltrosTareas(filtroActual = uiState.filtro, onFiltroClick = onFiltroClick)
            }

            when {
                uiState.cargando -> item {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                uiState.tareasVisibles.isEmpty() -> item {
                    Text(text = stringResource(R.string.lista_vacia))
                }

                else -> items(uiState.tareasVisibles, key = { it.id }) { tarea ->
                    TareaItem(tarea = tarea, onClick = { onTareaClick(tarea.id) })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TareasContenidoPreview() {
    TareasContenido(
        uiState = TareasUiState(
            cargando = false,
            tareasVisibles = listOf(
                Tarea(id = 1, titulo = "Entregar requisitos", completada = true),
                Tarea(id = 2, titulo = "Presentar avance")
            ),
            totalTareas = 2,
            tareasCompletadas = 1,
            progreso = 0.5f
        ),
        onTextoChange = {},
        onAgregarClick = {},
        onFiltroClick = {},
        onTareaClick = {}
    )
}
