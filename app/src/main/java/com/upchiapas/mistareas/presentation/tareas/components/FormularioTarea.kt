package com.upchiapas.mistareas.presentation.tareas.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.upchiapas.mistareas.R

// Campo para escribir una tarea nueva y el botón para agregarla.
// No guarda nada: recibe el texto y avisa los eventos con lambdas.
@Composable
fun FormularioTarea(
    texto: String,
    @StringRes error: Int?,
    onTextoChange: (String) -> Unit,
    onAgregarClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = texto,
            onValueChange = onTextoChange,
            label = { Text(stringResource(R.string.campo_nueva_tarea)) },
            singleLine = true,
            isError = error != null,
            supportingText = {
                if (error != null) {
                    Text(stringResource(error))
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { onAgregarClick() }),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onAgregarClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.boton_agregar))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormularioTareaPreview() {
    FormularioTarea(
        texto = "",
        error = R.string.error_vacio,
        onTextoChange = {},
        onAgregarClick = {}
    )
}
