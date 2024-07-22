package Factories

import com.example.shippingsimulator.Shipment

interface ShipmentFactory {
    fun createShipment(id: String): Shipment
}
