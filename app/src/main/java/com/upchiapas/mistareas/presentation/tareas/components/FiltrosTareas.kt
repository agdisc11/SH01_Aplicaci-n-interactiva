package com.upchiapas.mistareas.presentation.tareas.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upchiapas.mistareas.presentation.tareas.Filtro

// Fila de chips: Todas / Pendientes / Completadas. Se marca el que está activo.
@Composable
fun FiltrosTareas(filtroActual: Filtro, onFiltroClick: (Filtro) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        for (filtro in Filtro.entries) {
            FilterChip(
                selected = filtro == filtroActual,
                onClick = { onFiltroClick(filtro) },
                label = { Text(stringResource(filtro.etiqueta)) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FiltrosTareasPreview() {
    FiltrosTareas(filtroActual = Filtro.PENDIENTES, onFiltroClick = {})
}
