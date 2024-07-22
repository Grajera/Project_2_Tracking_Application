package Factories

import ShipmentTypes.OvernightShipmentType
import com.example.shippingsimulator.Shipment

class OvernightShipmentFactory : ShipmentFactory {
    override fun createShipment(id: String): Shipment {
        return Shipment(id, shipmentType = OvernightShipmentType())
    }
}