package com.example.shippingsimulator

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import Server


fun main() = application {
    val shipmentTracker = ShipmentTracker.instance
    Window(
        onCloseRequest = ::exitApplication,
        title = "Shipment Tracker Simulator"
    ) {
        MaterialTheme {
            shippingSimulatorApp()
            Server.startServer()
        }
    }

}

