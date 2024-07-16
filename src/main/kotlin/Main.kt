package com.example.shippingsimulator

import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val shipmentTracker = ShipmentTracker.instance
    val fileContent = loadUpdatesFromFile("src/test.txt")
    shipmentTracker.processUpdates(fileContent)

    while (true) {
        println("Enter a Tracking ID to track (or 'exit' to quit): ")
        val trackingId = readLine() ?: break

        if (trackingId.lowercase() == "exit") break

        shipmentTracker.trackShipment(trackingId)
        shipmentTracker.printShipmentStatus(trackingId)
    }
}

fun loadUpdatesFromFile(filePath: String): String {
    return try {
        java.io.File(filePath).readText()
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}
