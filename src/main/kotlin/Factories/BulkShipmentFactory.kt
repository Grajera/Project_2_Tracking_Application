package Factories

import ShipmentTypes.BulkShipmentType
import com.example.shippingsimulator.Shipment

class BulkShipmentFactory : ShipmentFactory {
    override fun createShipment(id: String): Shipment {
        return Shipment(id, shipmentType = BulkShipmentType())
    }
}