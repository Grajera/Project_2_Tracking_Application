package com.example.shippingsimulator

import java.io.File

class ShipmentTracker private constructor() {
    private val shipments = mutableMapOf<String, Shipment>()
    private val trackedShipments = mutableSetOf<String>()
    private val updateIndices = mutableMapOf<String, Int>()
    private val shipmentObserver = ShipmentObserver()
    private val validShipmentIds = mutableSetOf<String>()

    companion object {
        val instance: ShipmentTracker by lazy { ShipmentTracker() }
    }

    fun trackShipment(id: String) {
        if (!shipments.containsKey(id)) {
            val shipment = ShipmentFactory.createShipment(id)
            shipments[id] = shipment
            updateIndices[id] = 0
            shipmentObserver.notifyAllListeners(getShipments())
        }
        trackedShipments.add(id)
    }

    fun stopTracking(id: String) {
        val shipment = shipments.remove(id)
        //validShipmentIds.remove(id)
        trackedShipments.remove(id)
        updateIndices.remove(id)
        shipment?.let {
            shipmentObserver.notifyAllListeners(getShipments()) // Notify observers
        }
    }

    suspend fun processUpdates(fileContent: String) {
        val lines = fileContent.split("\n")
        val updatesMap = mutableMapOf<String, MutableList<ShipmentUpdate>>()

        lines.forEach { line ->
            val parts = line.split(",")
            if (parts.size < 3) return@forEach
            val update = ShipmentUpdate(parts[0], parts[1], parts[2].toLong(), parts.getOrNull(3))
            validShipmentIds.add(parts[1])
            updatesMap.computeIfAbsent(update.shipmentId) { mutableListOf() }.add(update)
        }

        updatesMap.forEach { (shipmentId, updates) ->
            if (trackedShipments.contains(shipmentId)) {
                val shipment = shipments[shipmentId]
                shipment?.let {
                    val currentIndex = updateIndices[shipmentId] ?: 0
                    if (currentIndex < updates.size) {
                        val update = updates[currentIndex]
                        updateStrategy.execute(update, it)
                        updateIndices[shipmentId] = currentIndex + 1
                        // Notify observers of the update
                        shipmentObserver.notifyAllListeners(getShipments())
                    }
                }
            }
        }
    }

    fun doesShipmentExist(trackingNumber: String): Boolean {
        return validShipmentIds.contains(trackingNumber)
    }

    fun addShipmentUpdateListener(listener: (List<Shipment>) -> Unit) {
        shipmentObserver.addListener(listener)
    }

    fun getShipments(): List<Shipment> = shipments.values.toList()

    private val updateStrategy: UpdateStrategy = UpdateStrategy()
}
