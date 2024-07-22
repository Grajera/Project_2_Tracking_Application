package Factories

import ShipmentTypes.StandardShipmentType
import com.example.shippingsimulator.Shipment

class StandardShipmentFactory : ShipmentFactory {
    override fun createShipment(id: String): Shipment {
        return Shipment(id, shipmentType = StandardShipmentType())
    }
}