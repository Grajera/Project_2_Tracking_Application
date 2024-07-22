package ShippingStrategies

import com.example.shippingsimulator.Shipment
import com.example.shippingsimulator.ShipmentUpdate

class LostUpdateStrategy : ShipmentUpdateStrategy {
    override fun applyUpdate(shipment: Shipment, update: ShipmentUpdate) {
        shipment.setStatus("lost")
    }
}
