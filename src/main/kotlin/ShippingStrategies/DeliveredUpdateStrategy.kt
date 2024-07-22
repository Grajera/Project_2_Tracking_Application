package ShippingStrategies

import com.example.shippingsimulator.Shipment
import com.example.shippingsimulator.ShipmentUpdate

class DeliveredUpdateStrategy : ShipmentUpdateStrategy {
    override fun applyUpdate(shipment: Shipment, update: ShipmentUpdate) {
        shipment.setStatus("delivered")
    }
}
