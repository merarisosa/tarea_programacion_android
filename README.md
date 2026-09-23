# Práctica: Comparativa de Desarrollo Nativo vs. Multiplataforma
**Materia:** Programación de Dispositivos Móviles  
**Nivel:** Maestría  
**Tecnologías:** Kotlin, Android Studio, Jetpack Compose, Compose Multiplatform  

---

## 1. Introducción y Conceptos Fundamentales

### 1.1 ¿Qué es el Desarrollo Nativo?
El **desarrollo nativo** consiste en la creación de aplicaciones diseñadas y programadas exclusivamente para una plataforma o sistema operativo específico (por ejemplo, Android o iOS), utilizando los lenguajes, herramientas, SDKs y entornos de desarrollo oficialmente soportados por el fabricante del sistema (Google en el caso de Android, Apple en iOS).

* **En Android Nativo:** Se utiliza **Kotlin** (o Java) con **Android Studio**, haciendo uso directo de los componentes de arquitectura de Android (`Activity`, `Fragment`, `Context`, Android Lifecycle) y herramientas de renderizado nativas como **Jetpack Compose** (o vistas XML tradicionales).
* **Acceso al hardware:** El código tiene acceso inmediato y sin capas intermedias a todos los sensores, APIs del sistema operativo y capacidades de la máquina.

### 1.2 ¿Qué es el Desarrollo Multiplataforma?
El **desarrollo multiplataforma** es un paradigma de ingeniería de software enfocado en escribir una base de código común (Single Codebase) que puede ejecutarse y desplegarse en múltiples sistemas operativos y plataformas (Android, iOS, Desktop/JVM, Web) reduciendo la duplicación de esfuerzo y costos de mantenimiento.

* **Kotlin Multiplatform (KMP) y Compose Multiplatform:** A diferencia de tecnologías híbridas que renderizan en WebView o frameworks que usan un motor gráfico propio desacoplado del lenguaje del sistema (como Flutter o React Native), KMP compila a código nativo en cada plataforma (bytecode JVM para Android/Desktop, binarios Objective-C/Swift para iOS vía Kotlin/Native, Wasm/JS para Web). **Compose Multiplatform** (desarrollado por JetBrains) extiende la API declarativa de Jetpack Compose permitiendo compartir no solo la lógica de negocio, sino también la interfaz gráfica.

---

## 2. Tecnologías Utilizadas en Esta Práctica

| Aspecto | Proyecto 1: App Nativa | Proyecto 2: App Multiplataforma |
| :--- | :--- | :--- |
| **Lenguaje de Programación** | Kotlin (v2.0) | Kotlin (v2.0) |
| **Entorno de Desarrollo (IDE)** | Android Studio | Android Studio |
| **Framework de UI** | Jetpack Compose (Material 3) | Compose Multiplatform (Material 3) |
| **Gestor de Construcción** | Gradle (Kotlin DSL `.kts`) | Gradle (Kotlin DSL `.kts`) |
| **Targets (Plataformas Destino)** | Android (Teléfonos / Tablets) | Android y Desktop (JVM: Linux, Windows, macOS) |
| **Estructura de Módulos** | Módulo tradicional Android (`:app`) | Módulo Multiplatform (`:composeApp`) con `sourceSets` |

---

## 3. Estructura y Diferencias Arquitectónicas entre Proyectos

La diferencia fundamental entre ambos enfoques se refleja claramente en la organización de los directorios y la división del código fuente.

### Árbol del Proyecto 1: Nativo (`proyecto1_nativo`)
En el proyecto nativo, toda la estructura está acoplada al sistema Android. No existe separación para otras plataformas.
```text
proyecto1_nativo/
├── gradle/
│   ├── libs.versions.toml             # Catálogo de dependencias
│   └── wrapper/                       # Gradle Wrapper (Gradle 8.7)
├── app/                               # Único módulo ejecutable (Android Application)
│   ├── build.gradle.kts               # Configuración del plugin com.android.application
│   └── src/main/
│       ├── AndroidManifest.xml        # Manifiesto obligatorio de Android
│       ├── java/com/practica/nativo/
│       │   ├── MainActivity.kt        # ComponentActivity y anfitrión de Compose
│       │   └── ui/
│       │       ├── CounterScreen.kt   # [LÓGICA PRINCIPAL] UI y estado del contador
│       │       └── theme/             # Paleta de colores y tema Material 3
│       └── res/                       # Recursos XML nativos (strings, drawables, mipmaps)
├── build.gradle.kts                   # Configuración raíz
└── settings.gradle.kts                # Inclusión del módulo :app
```

### Árbol del Proyecto 2: Multiplataforma (`proyecto2_multiplataforma`)
En el proyecto multiplataforma, el código se divide en conjuntos de fuentes (**SourceSets**). La interfaz de usuario y la lógica residen en `commonMain`, mientras que cada target (`androidMain`, `desktopMain`) solo implementa la inicialización y adaptaciones específicas.
```text
proyecto2_multiplataforma/
├── gradle/
│   ├── libs.versions.toml             # Catálogo de dependencias KMP y Compose
│   └── wrapper/                       # Gradle Wrapper (Gradle 8.7)
├── composeApp/                        # Módulo KMP compartido
│   ├── build.gradle.kts               # Configuración de targets: androidTarget() y jvm("desktop")
│   └── src/
│       ├── commonMain/                # [CÓDIGO 100% COMPARTIDO]
│       │   └── kotlin/com/practica/kmp/
│       │       ├── App.kt             # [LÓGICA PRINCIPAL] UI y lógica del contador compartida
│       │       └── Platform.kt        # Declaración 'expect' para puente con plataformas
│       ├── androidMain/               # [ESPECÍFICO DE ANDROID]
│       │   ├── AndroidManifest.xml    # Manifiesto de la app Android
│       │   ├── kotlin/com/practica/kmp/
│       │   │   ├── MainActivity.kt    # Inicia la app invocando App()
│       │   │   └── Platform.android.kt# Implementación 'actual' para Android
│       │   └── res/                   # Recursos de iconos y strings para Android
│       └── desktopMain/               # [ESPECÍFICO DE ESCRITORIO]
│           └── kotlin/com/practica/kmp/
│               ├── main.kt            # Ventana Window { App() } para Desktop
│               └── Platform.desktop.kt# Implementación 'actual' para Desktop (Linux/Win/Mac)
├── build.gradle.kts
└── settings.gradle.kts
```

---

## 4. Archivos que Contienen la Lógica Principal

### En el Proyecto 1 (Nativo):
* **[`proyecto1_nativo/app/src/main/java/com/practica/nativo/ui/CounterScreen.kt`](file:///home/merari/Descargas/hw/proyecto1_nativo/app/src/main/java/com/practica/nativo/ui/CounterScreen.kt)**:
  Contiene la función Composable `CounterScreen()`, que gestiona el estado reactivo `count` mediante `remember { mutableIntStateOf(0) }`, la tarjeta con el valor actual y los tres botones: **Aumentar** (`count++`), **Disminuir** (`count--`) y **Reiniciar** (`count = 0`).
* **[`proyecto1_nativo/app/src/main/java/com/practica/nativo/MainActivity.kt`](file:///home/merari/Descargas/hw/proyecto1_nativo/app/src/main/java/com/practica/nativo/MainActivity.kt)**:
  Gestiona la actividad nativa y renderiza `CounterScreen()` dentro del tema de la aplicación.

### En el Proyecto 2 (Multiplataforma):
* **[`proyecto2_multiplataforma/composeApp/src/commonMain/kotlin/com/practica/kmp/App.kt`](file:///home/merari/Descargas/hw/proyecto2_multiplataforma/composeApp/src/commonMain/kotlin/com/practica/kmp/App.kt)**:
  Contiene la función `App()`. Implementa exactamente la misma interfaz y estado reactivo que la versión nativa. **Este archivo no depende de Android ni de ningún sistema operativo en particular**, permitiendo que se ejecute idénticamente en Android y Desktop.
* **[`proyecto2_multiplataforma/composeApp/src/commonMain/kotlin/com/practica/kmp/Platform.kt`](file:///home/merari/Descargas/hw/proyecto2_multiplataforma/composeApp/src/commonMain/kotlin/com/practica/kmp/Platform.kt)**:
  Declara `expect fun getPlatformName(): String`, demostrando el mecanismo estándar para enlazar código común con capacidades específicas de cada sistema.

---

## 5. Qué Código Puede Compartirse y Cuál es Específico

### Código que Puede Compartirse (`commonMain`):
1. **Lógica de Negocio y Estado:** Clases de modelo, cálculos matemáticos, validaciones, gestión de estado reactivo (`mutableStateOf`, `StateFlow`), lógica del contador.
2. **Interfaz de Usuario (con Compose Multiplatform):** Botones, textos, layouts (`Column`, `Row`, `Box`), modales, navegación y componentes Material 3.
3. **Red y Persistencia:** Librerías multiplataforma como Ktor (cliente HTTP), SQLDelight / Room KMP (bases de datos) o kotlinx.serialization (JSON).

### Código Específico de Plataforma (`androidMain`, `desktopMain`, `iosMain`):
1. **Ciclo de vida del Sistema Operativo:** `Activity`, `Services`, `BroadcastReceivers` en Android; ventanas del gestor gráfico en Desktop (`Window()`, `Tray`).
2. **Acceso a Hardware y APIs Propietarias:** Cámara, Bluetooth, GPS, notificaciones push, permisos de sistema en tiempo de ejecución.
3. **Mecanismo `expect` / `actual`:**
   * En `commonMain/Platform.kt`: `expect fun getPlatformName(): String`
   * En `androidMain/Platform.android.kt`: `actual fun getPlatformName(): String = "Android (API ...)"`
   * En `desktopMain/Platform.desktop.kt`: `actual fun getPlatformName(): String = "Desktop (Linux/Windows/macOS)"`

---

## 6. Ventajas y Desventajas Comparativas

| Criterio | Enfoque Nativo (Jetpack Compose) | Enfoque Multiplataforma (KMP + Compose) |
| :--- | :--- | :--- |
| **Reutilización de Código** | **Baja.** El código solo sirve para Android; para iOS o Desktop debe reprogramarse desde cero. | **Muy Alta.** Entre el 70% y 95% del código (lógica, modelos y UI) se comparte entre plataformas. |
| **Rendimiento** | **Máximo.** Acceso directo sin capas de abstracción; optimizado al 100% para el runtime ART de Android. | **Casi Nativo.** Compila a bytecode o binario nativo según la plataforma (no usa WebViews). |
| **Tiempo de Desarrollo (Time to Market)** | **Mayor.** Requiere mantener equipos y repositorios independientes por cada plataforma. | **Menor.** Un solo desarrollador o equipo puede iterar las funcionalidades para móvil y escritorio simultáneamente. |
| **Acceso a Nuevas Funcionalidades del SO** | **Inmediato.** Soporte día cero para las últimas APIs presentadas en cada versión de Android. | **Ligera espera o requerimiento de expect/actual.** Si una API es muy reciente, debe implementarse manualmente en el sourceSet correspondiente. |
| **Curva de Aprendizaje** | Requiere dominar el ecosistema y APIs específicas de Android. | Requiere comprender Gradle multimodular, sourceSets y las diferencias entre targets de ejecución. |

---

## 7. Instrucciones para Abrir y Ejecutar los Proyectos en Android Studio

### Requisitos Previos:
* **Android Studio** (Hedgehog, Iguana, Jellyfish, Koala, Ladybug o superior).
* **JDK 17 o 21** configurado en Android Studio (`Settings -> Build, Execution, Deployment -> Build Tools -> Gradle -> Gradle JDK`).
* Para la versión de Android: Un emulador de Android configurado o un dispositivo físico con depuración USB habilitada.

---

### Cómo Ejecutar el Proyecto 1: App Nativa
1. Abre Android Studio.
2. Selecciona **Open** (o `File -> Open`).
3. Navega al directorio y selecciona la carpeta:
   `Descargas/hw/proyecto1_nativo`
4. Espera a que Gradle sincronice las dependencias del proyecto (`Gradle Sync`).
5. En la barra superior de herramientas, verifica que esté seleccionada la configuración de ejecución **`app`** y tu emulador o dispositivo móvil.
6. Haz clic en el botón **Run** (ícono verde de ▶) o presiona `Shift + F10`.
7. Observa la pantalla con el contador en el emulador y prueba los botones **Aumentar**, **Disminuir** y **Reiniciar**.

---

### Cómo Ejecutar el Proyecto 2: App Multiplataforma

#### Opción A: Ejecutar en Android
1. Abre Android Studio y selecciona **Open**.
2. Navega y abre la carpeta:
   `Descargas/hw/proyecto2_multiplataforma`
3. Espera a que finalice el `Gradle Sync`.
4. En el selector de configuraciones de ejecución, elige **`composeApp`** y tu dispositivo Android o emulador.
5. Haz clic en **Run** (▶).
6. Verás la aplicación ejecutándose en Android, con la etiqueta indicando:
   `"Ejecutándose en: Android (API XX)"`.

#### Opción B: Ejecutar en Desktop (Demostración Multiplataforma en PC)
Para demostrar que el mismo código corre en una segunda plataforma (Desktop):
1. **Desde Android Studio:**
   * Abre la pestaña lateral de **Gradle** (ubicada a la derecha de la ventana).
   * Despliega: `ContadorMultiplataforma -> composeApp -> Tasks -> compose desktop`.
   * Haz doble clic en la tarea **`run`**.
2. **O desde la terminal integrada en Android Studio:**
   ```bash
   cd /home/merari/Descargas/hw/proyecto2_multiplataforma
   ./gradlew :composeApp:run
   ```
3. Se abrirá una ventana nativa de escritorio en tu computadora con exactamente la misma interfaz y funcionamiento, mostrando la etiqueta:
   `"Ejecutándose en: Desktop (Linux / Windows / macOS)"`.

---

## 8. Conclusiones Académicas
1. **Separación de responsabilidades:** El desarrollo nativo ofrece el control más granular y directo sobre la plataforma objetivo, siendo ideal cuando se requiere exprimir al máximo el hardware o la integración profunda con servicios del sistema operativo.
2. **Eficiencia con KMP:** Kotlin Multiplatform junto con Compose Multiplatform permite mantener una arquitectura limpia donde la lógica de la vista y del dominio se escriben una sola vez (`commonMain`), sin sacrificar la posibilidad de descender a código nativo cuando sea necesario mediante `expect/actual`.
3. **Paridad técnica:** Ambos proyectos logran el mismo resultado visual y funcional con Jetpack Compose y Material 3, pero el proyecto multiplataforma demuestra una clara ventaja de escalabilidad al desplegarse simultáneamente en Android y Desktop.
