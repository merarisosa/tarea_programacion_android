package com.practica.nativo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.practica.nativo.ui.CounterScreen
import com.practica.nativo.ui.theme.ContadorNativoTheme

/**
 * Punto de entrada exclusivo de la plataforma Android.
 * En el desarrollo nativo, la Activity gestiona el ciclo de vida del SO móvil
 * y sirve como anfitrión para la jerarquía de vistas de Jetpack Compose.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContadorNativoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CounterScreen()
                }
            }
        }
    }
}
