package com.example.shippingsimulator

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Shipment Tracker Simulator"
    ) {
        MaterialTheme {
            shippingSimulatorApp()
        }
    }
}

