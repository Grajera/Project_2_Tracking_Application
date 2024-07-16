package com.example.shippingsimulator

class ShipmentObserver {
    private val listeners = mutableListOf<(List<Shipment>) -> Unit>()

    fun addListener(listener: (List<Shipment>) -> Unit) {
        listeners.add(listener)
    }

    fun notifyAllListeners(shipments: List<Shipment>) {
        listeners.forEach { it(shipments) }
    }
}