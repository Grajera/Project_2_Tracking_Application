package com.example.shippingsimulator

interface UpdateStrategy {
    fun execute(update: ShipmentUpdate, shipment: Shipment)
}
