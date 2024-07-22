package ShippingStrategies

import com.example.shippingsimulator.Shipment
import com.example.shippingsimulator.ShipmentUpdate

class LocationUpdateStrategy : ShipmentUpdateStrategy {
    override fun applyUpdate(shipment: Shipment, update: ShipmentUpdate) {
        shipment.setLocation(update.otherInfo ?: shipment.getLocation())
    }
}