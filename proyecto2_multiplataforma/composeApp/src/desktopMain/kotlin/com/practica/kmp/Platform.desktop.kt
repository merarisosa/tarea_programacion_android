package com.practica.kmp

/**
 * Implementación 'actual' para Desktop (JVM).
 * Detecta y devuelve el sistema operativo de escritorio (Linux, Windows, macOS).
 */
actual fun getPlatformName(): String = "Desktop (${System.getProperty("os.name")})"
