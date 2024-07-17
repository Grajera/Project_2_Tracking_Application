package com.example.shippingsimulator

interface ConcreteUpdateStrategy {
    fun execute(update: ShipmentUpdate, shipment: Shipment)
}
