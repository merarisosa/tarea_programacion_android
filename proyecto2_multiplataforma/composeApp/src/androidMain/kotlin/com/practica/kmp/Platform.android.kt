package com.practica.kmp

/**
 * Implementación 'actual' para Android.
 * Proporciona el nombre de la plataforma Android y la versión del SDK.
 */
actual fun getPlatformName(): String = "Android (API ${android.os.Build.VERSION.SDK_INT})"
