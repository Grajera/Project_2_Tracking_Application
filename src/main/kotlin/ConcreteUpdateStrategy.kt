package com.example.shippingsimulator

class ConcreteUpdateStrategy : UpdateStrategy {
    override fun execute(update: ShipmentUpdate, shipment: Shipment) {
        shipment.addUpdate(update)
    }
}
