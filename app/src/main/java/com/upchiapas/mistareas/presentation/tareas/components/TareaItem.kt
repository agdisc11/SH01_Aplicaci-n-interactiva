package com.upchiapas.mistareas.presentation.tareas.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upchiapas.mistareas.data.Tarea

// Una tarea de la lista. Al tocarla se marca o desmarca como completada.
// Si está completada, el texto se tacha y se ve más claro.
@Composable
fun TareaItem(tarea: Tarea, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            // onCheckedChange = null porque el clic lo maneja toda la tarjeta.
            Checkbox(checked = tarea.completada, onCheckedChange = null)

            Text(
                text = tarea.titulo,
                textDecoration = if (tarea.completada) TextDecoration.LineThrough else TextDecoration.None,
                color = if (tarea.completada) {
                    MaterialTheme.colorScheme.outline
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
                modifier = Modifier.padding(start = 12.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TareaItemPreview() {
    TareaItem(tarea = Tarea(id = 1, titulo = "Estudiar para el examen", completada = true), onClick = {})
}
