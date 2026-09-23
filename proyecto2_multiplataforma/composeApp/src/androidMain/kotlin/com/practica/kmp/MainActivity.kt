package com.practica.kmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

/**
 * Actividad puente exclusiva de Android en Compose Multiplatform.
 *
 * Su única responsabilidad es hospedar la interfaz Composable compartida 'App()'
 * dentro del ciclo de vida nativo de Android.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Invoca la interfaz común declarada en commonMain/App.kt
            App()
        }
    }
}
