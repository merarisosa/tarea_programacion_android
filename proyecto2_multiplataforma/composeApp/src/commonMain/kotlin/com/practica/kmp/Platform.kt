package com.practica.kmp

/**
 * Mecanismo 'expect' de Kotlin Multiplatform.
 *
 * Define un contrato común que cada target de plataforma (Android, Desktop)
 * implementará mediante su respectivo 'actual'.
 *
 * Esto permite que la UI compartida en commonMain pueda mostrar en qué plataforma
 * se está ejecutando sin acoplarse al sistema operativo.
 */
expect fun getPlatformName(): String
