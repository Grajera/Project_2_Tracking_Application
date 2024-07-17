package com.example.shippingsimulator

class UpdateStrategy : ConcreteUpdateStrategy {
    override fun execute(update: ShipmentUpdate, shipment: Shipment) {
        shipment.addUpdate(update)
    }
}
