package io.cerve.development.canvas

class WasmPlatform {
    val name: String = "Web with Kotlin/Wasm"
}

fun getPlatform() = WasmPlatform()