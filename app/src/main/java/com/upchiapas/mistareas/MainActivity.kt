package com.upchiapas.mistareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.upchiapas.mistareas.presentation.tareas.TareasPage
import com.upchiapas.mistareas.presentation.theme.MisTareasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MisTareasTheme {
                TareasPage()
            }
        }
    }
}
