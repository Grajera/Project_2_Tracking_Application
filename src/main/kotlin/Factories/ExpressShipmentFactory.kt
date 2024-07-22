package Factories

import ShipmentTypes.ExpressShipmentType
import com.example.shippingsimulator.Shipment

class ExpressShipmentFactory : ShipmentFactory {
    override fun createShipment(id: String): Shipment {
        return Shipment(id, shipmentType = ExpressShipmentType())
    }
}