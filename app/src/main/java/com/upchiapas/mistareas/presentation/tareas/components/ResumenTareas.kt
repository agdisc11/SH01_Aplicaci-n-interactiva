package com.upchiapas.mistareas.presentation.tareas.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upchiapas.mistareas.R

// Texto "X de Y tareas completadas" y la barra de progreso.
@Composable
fun ResumenTareas(completadas: Int, total: Int, progreso: Float) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = stringResource(R.string.resumen, completadas, total))

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { progreso },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResumenTareasPreview() {
    ResumenTareas(completadas = 2, total = 5, progreso = 0.4f)
}
