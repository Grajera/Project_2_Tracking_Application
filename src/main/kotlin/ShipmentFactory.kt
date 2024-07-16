package com.example.shippingsimulator

object ShipmentFactory {
    fun createShipment(id: String): Shipment {
        return Shipment(id)
    }
}
