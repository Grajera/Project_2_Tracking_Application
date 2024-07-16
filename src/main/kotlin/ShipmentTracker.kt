package com.example.shippingsimulator

class ShipmentTracker private constructor() {
    private val shipments = mutableMapOf<String, Shipment>()
    private val trackedShipments = mutableSetOf<String>()
    private val updateIndices = mutableMapOf<String, Int>() // Track current update index for each shipment
    private val shipmentUpdateListeners = mutableListOf<(List<Shipment>) -> Unit>()

    companion object {
        val instance: ShipmentTracker by lazy { ShipmentTracker() }
    }

    fun trackShipment(id: String) {
        if (!shipments.containsKey(id)) {
            val shipment = ShipmentFactory.createShipment(id)
            shipments[id] = shipment
            updateIndices[id] = 0 // Initialize update index
        }
        trackedShipments.add(id)
    }

    fun stopTracking(id: String) {
        shipments.remove(id)
        trackedShipments.remove(id)
        updateIndices.remove(id) // Remove update index when stopping tracking
        notifyListeners()
    }

    suspend fun processUpdates(fileContent: String) {
        val lines = fileContent.split("\n")
        val updatesMap = mutableMapOf<String, MutableList<ShipmentUpdate>>() // Map to store updates by shipment ID

        // Gather all updates from the file
        lines.forEach { line ->
            val parts = line.split(",")
            if (parts.size < 3) return@forEach // Skip invalid lines

            val update = ShipmentUpdate(parts[0], parts[1], parts[2].toLong(), parts.getOrNull(3))
            updatesMap.computeIfAbsent(update.shipmentId) { mutableListOf() }.add(update)
        }

        // Process updates only for tracked shipments
        updatesMap.forEach { (shipmentId, updates) ->
            if (trackedShipments.contains(shipmentId)) {
                val shipment = shipments[shipmentId]
                shipment?.let {
                    // Get the current index for updates
                    val currentIndex = updateIndices[shipmentId] ?: 0

                    // Process the next update if available
                    if (currentIndex < updates.size) {
                        val update = updates[currentIndex]
                        updateStrategy.execute(update, it)

                        // Update the index for the next update
                        updateIndices[shipmentId] = currentIndex + 1

                        // Notify listeners of the update
                        notifyListeners()
                    }
                }
            }
        }
    }

    private fun getShipments(): List<Shipment> = shipments.values.toList()

    fun addShipmentUpdateListener(listener: (List<Shipment>) -> Unit) {
        shipmentUpdateListeners.add(listener)
    }

    private fun notifyListeners() {
        shipmentUpdateListeners.forEach { it(getShipments())
        }
    }

    private val updateStrategy: UpdateStrategy = ConcreteUpdateStrategy()
}
