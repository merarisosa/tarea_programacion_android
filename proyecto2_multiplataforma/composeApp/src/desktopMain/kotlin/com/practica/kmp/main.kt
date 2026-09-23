package com.practica.kmp

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

/**
 * Punto de entrada exclusivo de la plataforma Desktop.
 *
 * Abre una ventana nativa del sistema operativo de escritorio (Linux, macOS, Windows)
 * y renderiza directamente la misma función 'App()' compartida en commonMain.
 */
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Contador Multiplataforma (Desktop)",
        state = rememberWindowState(width = 450.dp, height = 650.dp)
    ) {
        App()
    }
}
