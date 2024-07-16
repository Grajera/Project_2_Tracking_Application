package com.example.shippingsimulator

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.io.File

class ShipmentTracker private constructor() {
    private val shipments = mutableMapOf<String, Shipment>()

    companion object {
        val instance: ShipmentTracker by lazy { ShipmentTracker() }
    }

    fun trackShipment(id: String) {
        if (!shipments.containsKey(id)) {
            val shipment = ShipmentFactory.createShipment(id)
            shipments[id] = shipment
        }
    }

    fun printShipmentStatus(id: String) {
        val shipment = shipments[id]
        shipment?.let {
            println("Shipment ID: ${it.id}")
            println("Status: ${it.status}")
            println("Location: ${it.location}")
            println("Expected Delivery: ${it.getFormattedExpectedDeliveryDate()}")
            println("Notes: ${it.notes.joinToString(", ")}")
            println("Updates:")
            it.updates.forEach { update ->
                println("  - ${update.updateType} on ${update.timestamp}")
            }
        } ?: println("Shipment with ID $id does not exist.")
    }

    suspend fun processUpdates(fileContent: String) {
        val lines = fileContent.split("\n")
        lines.forEach { line ->
            val parts = line.split(",")
            if (parts.size < 3) return@forEach // Skip invalid lines

            val update = ShipmentUpdate(parts[0], parts[1], parts[2].toLong(), parts.getOrNull(3))
            val shipment = shipments[update.shipmentId]
            shipment?.let {
                updateStrategy.execute(update, it)
            }
        }
    }

    fun getShipments(): List<Shipment> = shipments.values.toList()

    fun stopTracking(id: String) {
        shipments.remove(id)
    }

    private val updateStrategy: UpdateStrategy = ConcreteUpdateStrategy()
}
