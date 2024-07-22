package com.example.shippingsimulator

class ShipmentObserver {
    private val listeners = mutableListOf<(List<Shipment>, String?) -> Unit>()

    fun addListener(listener: (List<Shipment>, String?) -> Unit) {
        listeners.add(listener)
    }

    fun notifyAllListeners(shipments: List<Shipment>, message: String? = null) {
        for (listener in listeners) {
            listener(shipments, message)
        }
    }
}