package com.example.shippingsimulator

import Client
import Server
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.runtime.Composable


fun main() = application {
    val shipmentTracker = ShipmentTracker.instance
    Window(
        onCloseRequest = ::exitApplication,
        title = "Shipment Tracker Simulator"
    ) {
        MaterialTheme {
            shippingSimulatorApp()
            Client().run()
            Server(shipmentTracker).start()
        }
    }

}

